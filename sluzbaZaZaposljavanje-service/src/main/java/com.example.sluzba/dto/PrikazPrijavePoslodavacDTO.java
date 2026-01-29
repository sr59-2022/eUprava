package com.example.sluzba.dto;

import com.example.sluzba.model.StatusPrijave;

import java.time.LocalDate;

public class PrikazPrijavePoslodavacDTO {

    public Long idPrijave;
    public Long idOglasa;
    public String nazivPozicije;
    public String nazivKompanije;
    public LocalDate datumPrijave;
    public StatusPrijave status;
    public String razlogOdbijanja;

    public String imeGradjanina;
    public String prezimeGradjanina;
    public String oblastGradjanina;
    public String radniStatusGradjanina;

    public PrikazPrijavePoslodavacDTO() {}

    public PrikazPrijavePoslodavacDTO(Long idPrijave, Long idOglasa, String nazivPozicije, String nazivKompanije,
                                      LocalDate datumPrijave, StatusPrijave status, String razlogOdbijanja,
                                      String imeGradjanina, String prezimeGradjanina,
                                      String oblastGradjanina, String radniStatusGradjanina) {
        this.idPrijave = idPrijave;
        this.idOglasa = idOglasa;
        this.nazivPozicije = nazivPozicije;
        this.nazivKompanije = nazivKompanije;
        this.datumPrijave = datumPrijave;
        this.status = status;
        this.razlogOdbijanja = razlogOdbijanja;
        this.imeGradjanina = imeGradjanina;
        this.prezimeGradjanina = prezimeGradjanina;
        this.oblastGradjanina = oblastGradjanina;
        this.radniStatusGradjanina = radniStatusGradjanina;
    }

}
