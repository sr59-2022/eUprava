package com.example.sluzba.dto;

import com.example.sluzba.model.RadniStatus;

public class GradjaninDTO {
    private Long id;
    private String ime;
    private String prezime;
    private RadniStatus radniStatus;
    private boolean imaPotvrdu;

    public GradjaninDTO(Long id, String ime, String prezime, RadniStatus radniStatus, boolean imaPotvrdu) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.radniStatus = radniStatus;
        this.imaPotvrdu = imaPotvrdu;
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

    public RadniStatus getRadniStatus() {
        return radniStatus;
    }

    public void setRadniStatus(RadniStatus radniStatus) {
        this.radniStatus = radniStatus;
    }

    public boolean isImaPotvrdu() {
        return imaPotvrdu;
    }

    public void setImaPotvrdu(boolean imaPotvrdu) {
        this.imaPotvrdu = imaPotvrdu;
    }
}

