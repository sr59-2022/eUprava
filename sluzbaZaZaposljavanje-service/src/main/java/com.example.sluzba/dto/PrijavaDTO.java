package com.example.sluzba.dto;

import com.example.sluzba.model.StatusPrijave;
import java.time.LocalDate;

public class PrijavaDTO {

    private Long idPrijave;
    private Long oglasId;
    private String nazivPozicije;
    private String nazivKompanije;
    private LocalDate datumPrijave;
    private StatusPrijave status;
    private String razlogOdbijanja;


    public PrijavaDTO() {
    }


    public PrijavaDTO(Long idPrijave, Long oglasId, String nazivPozicije,
                      String nazivKompanije, LocalDate datumPrijave,
                      StatusPrijave status, String razlogOdbijanja) {
        this.idPrijave = idPrijave;
        this.oglasId = oglasId;
        this.nazivPozicije = nazivPozicije;
        this.nazivKompanije = nazivKompanije;
        this.datumPrijave = datumPrijave;
        this.status = status;
        this.razlogOdbijanja = razlogOdbijanja;
    }


    public Long getIdPrijave() {
        return idPrijave;
    }

    public void setIdPrijave(Long idPrijave) {
        this.idPrijave = idPrijave;
    }

    public Long getOglasId() {
        return oglasId;
    }

    public void setOglasId(Long oglasId) {
        this.oglasId = oglasId;
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

    public LocalDate getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(LocalDate datumPrijave) {
        this.datumPrijave = datumPrijave;
    }

    public StatusPrijave getStatus() {
        return status;
    }

    public void setStatus(StatusPrijave status) {
        this.status = status;
    }

    public String getRazlogOdbijanja() {
        return razlogOdbijanja;
    }

    public void setRazlogOdbijanja(String razlogOdbijanja) {
        this.razlogOdbijanja = razlogOdbijanja;
    }

    @Override
    public String toString() {
        return "PrijavaDTO{" +
                "idPrijave=" + idPrijave +
                ", oglasId=" + oglasId +
                ", nazivPozicije='" + nazivPozicije + '\'' +
                ", nazivKompanije='" + nazivKompanije + '\'' +
                ", datumPrijave=" + datumPrijave +
                ", status=" + status +
                ", razlogOdbijanja='" + razlogOdbijanja + '\'' +
                '}';
    }
}
