package com.example.fakultet.dto;

public record DiplomiranjeStatusDto(
        String status,
        int ukupnoEspb,
        int potrebnoEspb,
        boolean zavrsniRadOdbranjen
) {}
