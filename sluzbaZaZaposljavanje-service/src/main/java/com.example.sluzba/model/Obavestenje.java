package com.example.sluzba.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Obavestenje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String poruka;

    @Column(nullable = false)
    private boolean procitano = false;

    @Column(nullable = false)
    private LocalDateTime datum;

    @ManyToOne
    @JoinColumn(name = "poslodavac_id")
    private Poslodavac poslodavac;


    public Obavestenje() {
    }

    public Obavestenje(String poruka, LocalDateTime datum, Poslodavac poslodavac) {
        this.poruka = poruka;
        this.datum = datum;
        this.poslodavac = poslodavac;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public boolean isProcitano() {
        return procitano;
    }

    public void setProcitano(boolean procitano) {
        this.procitano = procitano;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public Poslodavac getPoslodavac() {
        return poslodavac;
    }

    public void setPoslodavac(Poslodavac poslodavac) {
        this.poslodavac = poslodavac;
    }
}

