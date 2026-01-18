package com.example.sluzba.dto;

import com.example.sluzba.model.TipOglasa;

import java.time.LocalDate;

public class OglasRequest {
    public String nazivPozicije;
    public String opis;
    public LocalDate rokPrijave;
    public String nazivKompanije;
    public TipOglasa tipOglasa;
}

