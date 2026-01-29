package com.example.fakultet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "predmeti")
public class Predmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String sifra;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String naziv;

    private Integer espb;

    public Predmet() {}

    public Long getId() { return id; }

    public String getSifra() { return sifra; }
    public void setSifra(String sifra) { this.sifra = sifra; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public Integer getEspb() { return espb; }
    public void setEspb(Integer espb) { this.espb = espb; }
}
