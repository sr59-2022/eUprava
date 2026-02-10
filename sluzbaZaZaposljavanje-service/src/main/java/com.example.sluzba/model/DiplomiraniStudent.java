package com.example.sluzba.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "diplomirani_student")
public class DiplomiraniStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(nullable = false, unique = true)
    private String brojIndeksa;

    @Column(name = "datum_diplomiranja")
    private LocalDate datumDiplomiranja;

    @Column(name = "dostupan_za_zaposljavanje", nullable = false)
    private boolean dostupanZaZaposljavanje;

    public DiplomiraniStudent() {
    }

    public DiplomiraniStudent(String ime, String prezime, String brojIndeksa,
                              LocalDate datumDiplomiranja, boolean dostupanZaZaposljavanje) {
        this.ime = ime;
        this.prezime = prezime;
        this.brojIndeksa = brojIndeksa;
        this.datumDiplomiranja = datumDiplomiranja;
        this.dostupanZaZaposljavanje = dostupanZaZaposljavanje;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojIndeksa() {
        return brojIndeksa;
    }

    public void setBrojIndeksa(String brojIndeksa) {
        this.brojIndeksa = brojIndeksa;
    }

    public LocalDate getDatumDiplomiranja() {
        return datumDiplomiranja;
    }

    public void setDatumDiplomiranja(LocalDate datumDiplomiranja) {
        this.datumDiplomiranja = datumDiplomiranja;
    }

    public boolean isDostupanZaZaposljavanje() {
        return dostupanZaZaposljavanje;
    }

    public void setDostupanZaZaposljavanje(boolean dostupanZaZaposljavanje) {
        this.dostupanZaZaposljavanje = dostupanZaZaposljavanje;
    }

}

