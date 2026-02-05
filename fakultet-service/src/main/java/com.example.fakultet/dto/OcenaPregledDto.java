package com.example.fakultet.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OcenaPregledDto(
        Long ocenaId,
        Integer vrednost,
        LocalDate datumUpisa,

        Long ispitId,
        LocalDateTime datumOdrzavanja,

        Long predmetId,
        String predmetSifra,
        String predmetNaziv,
        Integer predmetEspb,

        String rokNaziv
) {}
