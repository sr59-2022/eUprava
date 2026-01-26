package com.example.sluzba.controller;

import com.example.sluzba.dto.GradjaninDTO;
import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.repository.GradjaninRepository;
import com.example.sluzba.service.GradjaninService;
import com.example.sluzba.service.PotvrdaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PotvrdaController {

    private final PotvrdaService potvrdaService;

    public PotvrdaController(PotvrdaService potvrdaService) {
        this.potvrdaService = potvrdaService;
    }

    // GRAĐANIN
    @PostMapping("/gradjanin/zatrazi-potvrdu/{id}")
    public ResponseEntity<Map<String, String>> zatrazi(@PathVariable Long id) {
        potvrdaService.zatraziPotvrdu(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Zahtev poslat");
        return ResponseEntity.ok(response);
    }


    // ADMIN
    @PostMapping("/admin/izdaj-potvrdu/{id}")
    public ResponseEntity<?> izdaj(@PathVariable Long id) {
        return ResponseEntity.ok(
                potvrdaService.izdajPotvrdu(id)
        );
    }

    @GetMapping("/admin/gradjani")
    public ResponseEntity<List<GradjaninDTO>> getGradjani() {
        return ResponseEntity.ok(potvrdaService.getGradjaniSaPotvrdama());
    }


}

