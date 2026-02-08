package com.example.fakultet.controller;

import com.example.fakultet.dto.IspitCreateDto;
import com.example.fakultet.model.Ispit;
import com.example.fakultet.service.IspitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ispiti-admin")
public class IspitController {

    private final IspitService ispitService;

    public IspitController(IspitService ispitService) {
        this.ispitService = ispitService;
    }


    @PostMapping
    @PreAuthorize("hasRole('PROFESOR') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ispit kreiraj(@Valid @RequestBody IspitCreateDto dto) {
        return ispitService.kreiraj(dto);
    }


    @GetMapping("/aktivni")
    public List<Ispit> aktivni() {
        return ispitService.sviAktivniZaPrijavu();
    }
}
