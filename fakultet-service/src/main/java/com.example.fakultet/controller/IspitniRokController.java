package com.example.fakultet.controller;

import com.example.fakultet.dto.IspitniRokCreateDto;
import com.example.fakultet.model.IspitniRok;
import com.example.fakultet.service.IspitniRokService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rokovi")
public class IspitniRokController {

    private final IspitniRokService rokService;

    public IspitniRokController(IspitniRokService rokService) {
        this.rokService = rokService;
    }

    // ✅ profesor/admin kreira rok
    @PostMapping
    @PreAuthorize("hasRole('PROFESOR') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public IspitniRok kreiraj(@Valid @RequestBody IspitniRokCreateDto dto) {
        return rokService.kreiraj(dto);
    }

    // ✅ svi mogu da vide rokove (da student zna na šta prijavljuje)
    @GetMapping
    public List<IspitniRok> svi() {
        return rokService.svi();
    }
}
