package com.example.sluzba.controller;

import com.example.sluzba.dto.OglasRequest;
import com.example.sluzba.model.Oglas;
import com.example.sluzba.security.JwtUtil;
import com.example.sluzba.service.OglasService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oglasi")
public class OglasController {

    private final OglasService oglasService;
    private final JwtUtil jwtUtil;

    public OglasController(OglasService oglasService, JwtUtil jwtUtil) {
        this.oglasService = oglasService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/dodaj")
    public Oglas dodajOglas(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OglasRequest req) {

        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtil.isPoslodavac(token)) {
            throw new RuntimeException("Nemate dozvolu da postavite oglas.");
        }

        Long uid = jwtUtil.getUid(token);
        return oglasService.dodajOglas(req, uid);
    }
}
