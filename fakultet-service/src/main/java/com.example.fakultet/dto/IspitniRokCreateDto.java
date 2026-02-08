package com.example.fakultet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class IspitniRokCreateDto {

    @NotBlank
    private String naziv;

    @NotNull
    private LocalDate pocetak;

    @NotNull
    private LocalDate kraj;

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public LocalDate getPocetak() { return pocetak; }
    public void setPocetak(LocalDate pocetak) { this.pocetak = pocetak; }

    public LocalDate getKraj() { return kraj; }
    public void setKraj(LocalDate kraj) { this.kraj = kraj; }
}
