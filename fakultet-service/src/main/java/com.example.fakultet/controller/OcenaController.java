package com.example.fakultet.controller;

import com.example.fakultet.dto.OcenaPregledDto;
import com.example.fakultet.service.OcenaService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fakultet")
public class OcenaController {

    private final OcenaService ocenaService;

    public OcenaController(OcenaService ocenaService) {
        this.ocenaService = ocenaService;
    }


    @GetMapping("/ocene/me")
    public List<OcenaPregledDto> mojeOcene(
            JwtAuthenticationToken auth,
            @RequestParam(required = false) Integer ocena,
            @RequestParam(required = false) Integer ocenaMin,
            @RequestParam(required = false) Integer ocenaMax,
            @RequestParam(required = false) Boolean polozio,
            @RequestParam(required = false) String predmet
    ) {
        Jwt jwt = auth.getToken();
        Object raw = jwt.getClaims().get("uid");
        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());

        return ocenaService.mojeOceneFilter(uid, ocena, ocenaMin, ocenaMax, polozio, predmet);
    }
}
