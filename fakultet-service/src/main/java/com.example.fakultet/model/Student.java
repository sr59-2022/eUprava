package com.example.fakultet.model;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brojIndeksa;
    private String ime;
    private String prezime;

    @Enumerated(EnumType.STRING)
    private StatusStudenta statusStudenta;

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public String getBrojIndeksa() {
        return brojIndeksa;
    }

    public void setBrojIndeksa(String brojIndeksa) {
        this.brojIndeksa = brojIndeksa;
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

    public StatusStudenta getStatusStudenta() {
        return statusStudenta;
    }

    public void setStatusStudenta(StatusStudenta statusStudenta) {
        this.statusStudenta = statusStudenta;
    }
}
