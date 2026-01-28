package com.example.sluzba.controller;

import com.example.sluzba.dto.OglasRequest;
import com.example.sluzba.model.Oglas;
import com.example.sluzba.model.TipOglasa;
import com.example.sluzba.service.OglasService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@RestController
@RequestMapping("/api/oglasi")
public class OglasController {

    private final OglasService oglasService;

    public OglasController(OglasService oglasService) {
        this.oglasService = oglasService;
    }

    @PostMapping("/dodaj")
    @PreAuthorize("hasRole('POSLODAVAC')")
    public Oglas dodajOglas(
            @RequestBody OglasRequest req,
            @AuthenticationPrincipal Jwt jwt) {

        Long uid = ((Number) jwt.getClaims().get("uid")).longValue();

        return oglasService.dodajOglas(req, uid);
    }

    @GetMapping
    public List<Oglas> getAllOglasi() {
        return oglasService.getAllOglasi();
    }

    @GetMapping("/pretraga")
    public List<Oglas> pretraga(
            @RequestParam(required = false) String naziv,
            @RequestParam(required = false) TipOglasa tip) {

        return oglasService.pretragaOglasa(naziv, tip);
    }

    @GetMapping("/preporuke")
    public List<Oglas> getPreporuke(@AuthenticationPrincipal Jwt jwt) {
        Long authGradjaninId = ((Number) jwt.getClaims().get("uid")).longValue();
        return oglasService.generisiPreporuke(authGradjaninId);
    }


}
