package com.example.sluzba.dto;


import java.time.LocalDate;

public class DiplomiraniStudentDto {

    private Long authUid;
    private String ime;
    private String prezime;
    private String brojIndeksa;


    public DiplomiraniStudentDto() {}

    public DiplomiraniStudentDto(Long authUid, String ime, String prezime, String brojIndeksa) {
        this.authUid = authUid;
        this.ime = ime;
        this.prezime = prezime;
        this.brojIndeksa = brojIndeksa;
    }

    public Long getAuthUid() { return authUid; }
    public void setAuthUid(Long authUid) { this.authUid = authUid; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getBrojIndeksa() { return brojIndeksa; }
    public void setBrojIndeksa(String brojIndeksa) { this.brojIndeksa = brojIndeksa; }

}
