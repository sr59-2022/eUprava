package com.example.fakultet.dto;

public record PredmetIndeksDto(
        Long predmetId,
        String sifra,
        String naziv,
        Integer espb,
        Integer ocena,
        boolean polozio
) {}