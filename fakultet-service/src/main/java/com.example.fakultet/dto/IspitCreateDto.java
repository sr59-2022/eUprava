package com.example.fakultet.dto;

import java.time.LocalDateTime;

public class IspitCreateDto {

    private Long predmetId;
    private Long rokId;
    private LocalDateTime datumOdrzavanja;
    private LocalDateTime prijavaDo;
    private String sala;

    public Long getPredmetId() { return predmetId; }
    public void setPredmetId(Long predmetId) { this.predmetId = predmetId; }

    public Long getRokId() { return rokId; }
    public void setRokId(Long rokId) { this.rokId = rokId; }

    public LocalDateTime getDatumOdrzavanja() { return datumOdrzavanja; }
    public void setDatumOdrzavanja(LocalDateTime datumOdrzavanja) { this.datumOdrzavanja = datumOdrzavanja; }

    public LocalDateTime getPrijavaDo() { return prijavaDo; }
    public void setPrijavaDo(LocalDateTime prijavaDo) { this.prijavaDo = prijavaDo; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }
}
