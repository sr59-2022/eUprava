package com.example.fakultet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ispiti")
public class Ispit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "predmet_id", nullable = false)
    private Predmet predmet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rok_id", nullable = false)
    private IspitniRok rok;

    @Column(nullable = false)
    private LocalDateTime datumOdrzavanja;

    @Column(name = "prijava_do", nullable = false)
    private LocalDateTime prijavaDo;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String sala;

    @OneToMany(mappedBy = "ispit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ocena> ocene = new ArrayList<>();

    public Ispit() {}

    public Long getId() { return id; }

    public Predmet getPredmet() { return predmet; }
    public void setPredmet(Predmet predmet) { this.predmet = predmet; }

    public IspitniRok getRok() { return rok; }
    public void setRok(IspitniRok rok) { this.rok = rok; }

    public LocalDateTime getDatumOdrzavanja() { return datumOdrzavanja; }
    public void setDatumOdrzavanja(LocalDateTime datumOdrzavanja) { this.datumOdrzavanja = datumOdrzavanja; }

    public LocalDateTime getPrijavaDo() { return prijavaDo; }
    public void setPrijavaDo(LocalDateTime prijavaDo) { this.prijavaDo = prijavaDo; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public List<Ocena> getOcene() { return ocene; }
    public void setOcene(List<Ocena> ocene) { this.ocene = ocene; }
}
