package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.dto.RegistracijaRequest;
import com.example.auth.model.Korisnik;
import com.example.auth.model.Uloga;
import com.example.auth.repository.KorisnikRepository;
import com.example.auth.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final KorisnikRepository korisnikRepository;
    private final JwtService jwtService;

    @Value("${fakultet.service.base-url}")
    private String fakultetBaseUrl;

    @Value("${service.token}")
    private String serviceToken;

    public AuthService(KorisnikRepository korisnikRepository, JwtService jwtService) {
        this.korisnikRepository = korisnikRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        Korisnik k = korisnikRepository.findByKorisnickoIme(request.getKorisnickoIme())
                .orElseThrow(() -> new RuntimeException("Pogrešno korisničko ime ili lozinka"));

        if (!k.getLozinka().equals(request.getLozinka())) {
            throw new RuntimeException("Pogrešno korisničko ime ili lozinka");
        }

        String token = jwtService.generateToken(k);

        Set<String> roles = k.getUloge()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        return new LoginResponse(token, roles);
    }

    public void registracija(RegistracijaRequest request) {

        if (request.getUloga() == Uloga.ROLE_ADMIN) {
            throw new RuntimeException("ADMIN se ne može registrovati");
        }

        if (korisnikRepository.existsByKorisnickoIme(request.getKorisnickoIme())) {
            throw new RuntimeException("Korisničko ime već postoji");
        }

        if (korisnikRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email već postoji");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(request.getIme());
        korisnik.setPrezime(request.getPrezime());
        korisnik.setKorisnickoIme(request.getKorisnickoIme());
        korisnik.setEmail(request.getEmail());
        korisnik.setLozinka(request.getLozinka());
        korisnik.setUloge(Set.of(request.getUloga()));

        Korisnik saved = korisnikRepository.save(korisnik);


        if (request.getUloga() != Uloga.ROLE_STUDENT) {
            return;
        }

        RestTemplate rt = new RestTemplate();
        String url = fakultetBaseUrl + "/api/fakultet/internal/studenti";

        Map<String, Object> body = new HashMap<>();
        body.put("authUid", saved.getId());
        body.put("ime", saved.getIme());
        body.put("prezime", saved.getPrezime());
        body.put("brojIndeksa", "SR" + saved.getId() + "/2026");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-SERVICE-TOKEN", serviceToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            rt.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (Exception e) {
            korisnikRepository.deleteById(saved.getId());
            throw new RuntimeException("Registracija nije kompletna: upis u fakultet nije uspeo (auth korisnik obrisan)", e);
        }
    }
}
