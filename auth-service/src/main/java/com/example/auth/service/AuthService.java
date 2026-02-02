package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.dto.RegistracijaRequest;
import com.example.auth.model.Korisnik;
import com.example.auth.model.Uloga;
import com.example.auth.repository.KorisnikRepository;
import com.example.auth.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final KorisnikRepository korisnikRepository;
    private final JwtService jwtService;

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

        // plain text
        korisnik.setLozinka(request.getLozinka());

        korisnik.setUloge(Set.of(request.getUloga()));

        korisnikRepository.save(korisnik);
    }

}
