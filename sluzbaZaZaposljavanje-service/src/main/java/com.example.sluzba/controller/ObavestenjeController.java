package com.example.sluzba.controller;

import com.example.sluzba.dto.ObavestenjeDTO;
import com.example.sluzba.service.ObavestenjeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obavestenja")
public class ObavestenjeController {

    private final ObavestenjeService obavestenjeService;

    public ObavestenjeController(ObavestenjeService obavestenjeService) {
        this.obavestenjeService = obavestenjeService;
    }

    @GetMapping("/poslodavac")
    public List<ObavestenjeDTO> mojaObavestenja(@AuthenticationPrincipal Jwt jwt) {
        Long authPoslodavacId = ((Number) jwt.getClaims().get("uid")).longValue();
        return obavestenjeService.getObavestenjaZaPoslodavca(authPoslodavacId);
    }

    @PostMapping("/{id}/procitano")
    public void oznaciProcitano(@PathVariable Long id) {
        obavestenjeService.oznaciKaoProcitano(id);
    }

    @GetMapping("/neprocitana")
    public long brojNeprocitanihObavestenja(@AuthenticationPrincipal Jwt jwt) {
        Long authPoslodavacId = ((Number) jwt.getClaims().get("uid")).longValue();
        return obavestenjeService.brojNeprocitanihObavestenja(authPoslodavacId);
    }

}
