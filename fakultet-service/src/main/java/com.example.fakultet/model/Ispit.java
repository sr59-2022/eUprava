package com.example.fakultet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ispiti")
public class Ispit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String predmet;

    @Column(nullable = false)
    private LocalDate datum;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String sala;

    @OneToMany(mappedBy = "ispit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ocena> ocene = new ArrayList<>();

    public Ispit() {}

    public Long getId() { return id; }

    public String getPredmet() { return predmet; }
    public void setPredmet(String predmet) { this.predmet = predmet; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public List<Ocena> getOcene() { return ocene; }
    public void setOcene(List<Ocena> ocene) { this.ocene = ocene; }
}
