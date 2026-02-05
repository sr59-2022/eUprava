package com.example.fakultet.controller;

import com.example.fakultet.dto.PredmetIndeksDto;
import com.example.fakultet.model.Predmet;
import com.example.fakultet.repository.PredmetRepository;
import com.example.fakultet.service.PredmetIndeksService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predmeti")
public class PredmetController {

    private final PredmetRepository predmetRepository;
    private final PredmetIndeksService predmetIndeksService;

    public PredmetController(PredmetRepository predmetRepository, PredmetIndeksService predmetIndeksService) {
        this.predmetRepository = predmetRepository;
        this.predmetIndeksService = predmetIndeksService;
    }


    @GetMapping
    @PreAuthorize("hasRole('PROFESOR') or hasRole('ADMIN')")
    public List<Predmet> sviPredmeti() {
        return predmetRepository.findAll();
    }


    @GetMapping("/indeks/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<PredmetIndeksDto> indeks(
            JwtAuthenticationToken auth,
            @RequestParam(required = false) String predmet,
            @RequestParam(required = false) Boolean polozio
    ) {
        Jwt jwt = auth.getToken();
        Object raw = jwt.getClaims().get("uid");
        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());

        return predmetIndeksService.indeks(uid, predmet, polozio);
    }
}