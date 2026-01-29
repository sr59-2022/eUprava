package com.example.sluzba.controller;

import com.example.sluzba.dto.GradjaninDTO;
import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.service.GradjaninService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;


import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gradjanin")
public class GradjaninController {

    private final GradjaninService gradjaninService;

    public GradjaninController(GradjaninService gradjaninService) {
        this.gradjaninService = gradjaninService;
    }

    @GetMapping("/me")
    public GradjaninDTO getOrCreateGradjanin(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();

        Long authId = Long.valueOf(claims.get("uid").toString());
        String ime = (String) claims.get("ime");
        String prezime = (String) claims.get("prezime");
        Collection<String> roles = (Collection<String>) claims.get("roles");

        Gradjanin g = gradjaninService.createGradjanin(authId, ime, prezime, roles);
        return gradjaninService.getProfilGradjanin(g.getId());
    }

    @PutMapping("/me")
    public GradjaninDTO updateMe(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody Gradjanin updated
    ) {
        Map<String, Object> claims = jwt.getClaims();
        Long authId = Long.valueOf(claims.get("uid").toString());

        Gradjanin g = gradjaninService.updateGradjanin(authId, updated);
        return gradjaninService.getProfilGradjanin(g.getId());
    }



    @GetMapping("/me/dto")
    public GradjaninDTO getProfilSaPotvrdom(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();

        Long authId = Long.valueOf(claims.get("uid").toString());
        String ime = (String) claims.get("ime");
        String prezime = (String) claims.get("prezime");
        Collection<String> roles = (Collection<String>) claims.get("roles");

        Gradjanin g = gradjaninService.createGradjanin(authId, ime, prezime, roles);

        // Vraća DTO sa potvrdom
        return gradjaninService.getProfilGradjanin(g.getId());
    }
}