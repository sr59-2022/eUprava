package com.example.fakultet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OglasDTO {

    private Long idOglasa;
    private String nazivPozicije;
    private String nazivKompanije;
    private String oblast;
    private String opis;
    private LocalDate datumObjave;
    private LocalDate rokPrijave;
    private String tipOglasa;

    public OglasDTO() {
    }

    public OglasDTO(Long idOglasa,
                    String nazivPozicije,
                    String nazivKompanije,
                    String oblast,
                    String opis,
                    LocalDate datumObjave,
                    LocalDate rokPrijave,
                    String tipOglasa) {
        this.idOglasa = idOglasa;
        this.nazivPozicije = nazivPozicije;
        this.nazivKompanije = nazivKompanije;
        this.oblast = oblast;
        this.opis = opis;
        this.datumObjave = datumObjave;
        this.rokPrijave = rokPrijave;
        this.tipOglasa = tipOglasa;
    }

    public Long getIdOglasa() {
        return idOglasa;
    }

    public void setIdOglasa(Long idOglasa) {
        this.idOglasa = idOglasa;
    }

    public String getNazivPozicije() {
        return nazivPozicije;
    }

    public void setNazivPozicije(String nazivPozicije) {
        this.nazivPozicije = nazivPozicije;
    }

    public String getNazivKompanije() {
        return nazivKompanije;
    }

    public void setNazivKompanije(String nazivKompanije) {
        this.nazivKompanije = nazivKompanije;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDate getDatumObjave() {
        return datumObjave;
    }

    public void setDatumObjave(LocalDate datumObjave) {
        this.datumObjave = datumObjave;
    }

    public LocalDate getRokPrijave() {
        return rokPrijave;
    }

    public void setRokPrijave(LocalDate rokPrijave) {
        this.rokPrijave = rokPrijave;
    }

    public String getTipOglasa() {
        return tipOglasa;
    }

    public void setTipOglasa(String tipOglasa) {
        this.tipOglasa = tipOglasa;
    }
}