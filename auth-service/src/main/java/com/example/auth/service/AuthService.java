package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.model.Korisnik;
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

        // direktno poređenje (plain text)
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
}
