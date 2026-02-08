package com.example.fakultet.controller;

import com.example.fakultet.dto.UverenjeDto;
import com.example.fakultet.model.TipUverenja;
import com.example.fakultet.service.UverenjeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fakultet")
public class UverenjeController {

    private final UverenjeService uverenjeService;

    public UverenjeController(UverenjeService uverenjeService) {
        this.uverenjeService = uverenjeService;
    }

    @PostMapping("/uverenja/me")
    public UverenjeDto izdaj(
            @RequestParam(defaultValue = "STUDIRANJE") TipUverenja tip,
            JwtAuthenticationToken auth
    ) {
        Long uid = extractUid(auth);
        return uverenjeService.izdajUverenje(uid, tip);
    }

    @GetMapping("/uverenja/me")
    public List<UverenjeDto> moja(JwtAuthenticationToken auth) {
        Long uid = extractUid(auth);
        return uverenjeService.mojaUverenja(uid);
    }


    @GetMapping("/uverenja/{id}/pdf")
    public ResponseEntity<byte[]> pdf(@PathVariable Long id, JwtAuthenticationToken auth) {
        Long uid = extractUid(auth);

        byte[] pdf = uverenjeService.generisiPdfZaUverenje(uid, id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=uverenje-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    private Long extractUid(JwtAuthenticationToken auth) {
        Jwt jwt = auth.getToken();
        Object raw = jwt.getClaims().get("uid");
        return (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());
    }
}
