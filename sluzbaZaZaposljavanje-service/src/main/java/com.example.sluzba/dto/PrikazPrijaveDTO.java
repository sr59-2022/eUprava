package com.example.sluzba.dto;

import com.example.sluzba.model.StatusPrijave;
import java.time.LocalDate;

public class PrikazPrijaveDTO {
    private Long oglasId;
    private Long prijavaId;
    private String nazivPozicije;
    private String nazivKompanije;
    private LocalDate datumPrijave;
    private StatusPrijave status;
    private String razlogOdbijanja;

    public PrikazPrijaveDTO(Long oglasId, Long prijavaId,
                            String nazivPozicije,
                            String nazivKompanije,
                            LocalDate datumPrijave,
                            StatusPrijave status,
                            String razlogOdbijanja) {
        this.oglasId = oglasId;
        this.prijavaId = prijavaId;
        this.nazivPozicije = nazivPozicije;
        this.nazivKompanije = nazivKompanije;
        this.datumPrijave = datumPrijave;
        this.status = status;
        this.razlogOdbijanja = razlogOdbijanja;
    }


    public Long getPrijavaId() {
        return prijavaId;
    }

    public String getNazivPozicije() {
        return nazivPozicije;
    }

    public String getNazivKompanije() {
        return nazivKompanije;
    }

    public LocalDate getDatumPrijave() {
        return datumPrijave;
    }

    public StatusPrijave getStatus() {
        return status;
    }

    public String getRazlogOdbijanja() {
        return razlogOdbijanja;
    }

    public Long getOglasId() {
        return oglasId;
    }
}
