package com.example.fakultet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "ispitni_rokovi")
public class IspitniRok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String naziv; // npr. "Januar 2026"

    private LocalDate pocetak;
    private LocalDate kraj;

    public IspitniRok() {}

    public Long getId() { return id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public LocalDate getPocetak() { return pocetak; }
    public void setPocetak(LocalDate pocetak) { this.pocetak = pocetak; }

    public LocalDate getKraj() { return kraj; }
    public void setKraj(LocalDate kraj) { this.kraj = kraj; }
}
