package com.example.sluzba.dto;


import java.time.LocalDateTime;

public class ObavestenjeDTO {

    public Long id;
    public String poruka;
    public boolean procitano;
    public LocalDateTime datum;

    public ObavestenjeDTO(Long id, String poruka, boolean procitano, LocalDateTime datum) {
        this.id = id;
        this.poruka = poruka;
        this.procitano = procitano;
        this.datum = datum;
    }
}
