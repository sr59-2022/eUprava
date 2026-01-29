package com.example.sluzba.dto;

import java.time.LocalDate;

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

    public OglasDTO(Long idOglasa, String nazivPozicije, String nazivKompanije, String oblast,
                    String opis, LocalDate datumObjave, LocalDate rokPrijave, String tipOglasa) {
        this.idOglasa = idOglasa;
        this.nazivPozicije = nazivPozicije;
        this.nazivKompanije = nazivKompanije;
        this.oblast = oblast;
        this.opis = opis;
        this.datumObjave = datumObjave;
        this.rokPrijave = rokPrijave;
        this.tipOglasa = tipOglasa;
    }

    public Long getIdOglasa() { return idOglasa; }
    public String getNazivPozicije() { return nazivPozicije; }
    public String getNazivKompanije() { return nazivKompanije; }
    public String getOblast() { return oblast; }
    public String getOpis() { return opis; }
    public LocalDate getDatumObjave() { return datumObjave; }
    public LocalDate getRokPrijave() { return rokPrijave; }
    public String getTipOglasa() { return tipOglasa; }
}
