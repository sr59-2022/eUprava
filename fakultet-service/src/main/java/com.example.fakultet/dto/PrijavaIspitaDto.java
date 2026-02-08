package com.example.fakultet.dto;

import com.example.fakultet.model.StatusPrijave;

import java.time.LocalDateTime;

public record PrijavaIspitaDto(
        Long id,
        Long ispitId,
        String predmetNaziv,
        String rokNaziv,
        LocalDateTime datumOdrzavanja,
        LocalDateTime prijavaDo,
        StatusPrijave status,
        LocalDateTime datumPrijave
) {}
