package com.example.sluzba.controller;

import com.example.sluzba.dto.PrijavaDTO;
import com.example.sluzba.dto.PrikazPrijaveDTO;
import com.example.sluzba.model.Prijava;
import com.example.sluzba.model.StatusPrijave;
import com.example.sluzba.service.PrijavaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@RestController
@RequestMapping("/api/prijave")
public class PrijavaController {

    private final PrijavaService prijavaService;

    public PrijavaController(PrijavaService prijavaService) {
        this.prijavaService = prijavaService;
    }

    @PostMapping("/prijavi")
    public PrikazPrijaveDTO prijaviSe(@AuthenticationPrincipal Jwt jwt,
                                      @RequestParam Long oglasId) {

        Long authGradjaninId = ((Number) jwt.getClaims().get("uid")).longValue();
        Prijava p = prijavaService.prijaviSe(authGradjaninId, oglasId);

        return prijavaService.prijavaDTO(p);
    }


    @GetMapping("/moje")
    public List<PrikazPrijaveDTO> mojePrijave(@AuthenticationPrincipal Jwt jwt) {
        Long authGradjaninId = ((Number) jwt.getClaims().get("uid")).longValue();
        return prijavaService.prijaveZaGradjaninaDTO(authGradjaninId);
    }

}



