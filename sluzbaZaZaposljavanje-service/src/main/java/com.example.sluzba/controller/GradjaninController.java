package com.example.sluzba.controller;

import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.service.GradjaninService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;


import java.util.Map;

@RestController
@RequestMapping("/api/gradjanin")
public class GradjaninController {

    private final GradjaninService gradjaninService;

    public GradjaninController(GradjaninService gradjaninService) {
        this.gradjaninService = gradjaninService;
    }

    @GetMapping("/me")
    public Gradjanin getOrCreateGradjanin(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();

        Long authId = Long.valueOf(claims.get("uid").toString());
        String ime = (String) claims.get("ime");
        String prezime = (String) claims.get("prezime");

        return gradjaninService.createGradjanin(authId, ime, prezime);
    }

}