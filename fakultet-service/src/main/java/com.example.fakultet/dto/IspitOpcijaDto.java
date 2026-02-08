package com.example.fakultet.dto;

import java.time.LocalDateTime;

public record IspitOpcijaDto(
        Long id,
        String predmetNaziv,
        String rokNaziv,
        LocalDateTime datumOdrzavanja,
        LocalDateTime prijavaDo
) {}
