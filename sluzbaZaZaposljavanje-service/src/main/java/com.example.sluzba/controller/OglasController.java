package com.example.sluzba.controller;

import com.example.sluzba.dto.OglasDTO;
import com.example.sluzba.dto.OglasRequest;
import com.example.sluzba.model.Oglas;
import com.example.sluzba.model.TipOglasa;
import com.example.sluzba.service.OglasService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/oglasi")
public class OglasController {

    private final OglasService oglasService;

    public OglasController(OglasService oglasService) {
        this.oglasService = oglasService;
    }

    @PostMapping("/dodaj")
    @PreAuthorize("hasRole('POSLODAVAC')")
    public OglasDTO dodajOglas(
            @RequestBody OglasRequest req,
            @AuthenticationPrincipal Jwt jwt) {

        Long authPoslodavacId = ((Number) jwt.getClaims().get("uid")).longValue();
        Oglas o = oglasService.dodajOglas(req, authPoslodavacId);
        return oglasService.oglasDTO(o);
    }

    @GetMapping
    public List<OglasDTO> getAllOglasi() {
        return oglasService.getAllOglasi().stream()
                .map(oglasService::oglasDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pretraga")
    public List<OglasDTO> pretraga(
            @RequestParam(required = false) String naziv,
            @RequestParam(required = false) TipOglasa tip) {

        return oglasService.pretragaOglasa(naziv, tip).stream()
                .map(oglasService::oglasDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/preporuke")
    public List<OglasDTO> getPreporuke(@AuthenticationPrincipal Jwt jwt) {
        Long authGradjaninId = ((Number) jwt.getClaims().get("uid")).longValue();
        return oglasService.generisiPreporuke(authGradjaninId).stream()
                .map(oglasService::oglasDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/moji")
    public List<OglasDTO> getMojiOglasi(@AuthenticationPrincipal Jwt jwt) {
        Long authPoslodavacId = ((Number) jwt.getClaims().get("uid")).longValue();
        return oglasService.getOglasiPoslodavca(authPoslodavacId).stream()
                .map(oglasService::oglasDTO)
                .collect(Collectors.toList());
    }

}

