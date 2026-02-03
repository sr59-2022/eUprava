package com.example.fakultet.controller;

import com.example.fakultet.model.Predmet;
import com.example.fakultet.repository.PredmetRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predmeti")
public class PredmetController {

    private final PredmetRepository predmetRepository;

    public PredmetController(PredmetRepository predmetRepository) {
        this.predmetRepository = predmetRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('PROFESOR') or hasRole('ADMIN')")
    public List<Predmet> sviPredmeti() {
        return predmetRepository.findAll();
    }
}
