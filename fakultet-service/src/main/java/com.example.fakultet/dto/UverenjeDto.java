package com.example.fakultet.dto;

import java.time.LocalDate;

public record UverenjeDto(
        Long id,
        String brojDokumenta,
        LocalDate datumIzdavanja,
        String tip
) {}
