package com.example.sluzba.dto;


import java.time.LocalDate;

public class DiplomiraniStudentDto {

    private String ime;
    private String prezime;
    private String brojIndeksa;
    private boolean dostupanZaZaposljavanje;


    public DiplomiraniStudentDto() {}

    public DiplomiraniStudentDto(String ime, String prezime, String brojIndeksa, boolean dostupanZaZaposljavanje) {
        this.ime = ime;
        this.prezime = prezime;
        this.brojIndeksa = brojIndeksa;
        this.dostupanZaZaposljavanje= dostupanZaZaposljavanje;

    }


    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getBrojIndeksa() { return brojIndeksa; }
    public void setBrojIndeksa(String brojIndeksa) { this.brojIndeksa = brojIndeksa; }

    public boolean isDostupanZaZaposljavanje() {
        return dostupanZaZaposljavanje;
    }

    public void setDostupanZaZaposljavanje(boolean dostupanZaZaposljavanje) {
        this.dostupanZaZaposljavanje = dostupanZaZaposljavanje;
    }
}
