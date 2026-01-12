package com.example.sluzba.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Prijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrijave;

    private LocalDate datumPrijave;
    private String status;

    @ManyToOne
    @JoinColumn(name = "gradjanin_id")
    private Gradjanin gradjanin;

    @ManyToOne
    @JoinColumn(name = "oglas_id")
    private Oglas oglas;


    public Prijava() {}
    public Prijava(Long idPrijave, LocalDate datumPrijave, String status) {
        this.idPrijave = idPrijave;
        this.datumPrijave = datumPrijave;
        this.status = status;
    }


    public Long getIdPrijave() { return idPrijave; }
    public void setIdPrijave(Long idPrijave) { this.idPrijave = idPrijave; }
    public LocalDate getDatumPrijave() { return datumPrijave; }
    public void setDatumPrijave(LocalDate datumPrijave) { this.datumPrijave = datumPrijave; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Gradjanin getGradjanin() { return gradjanin; }
    public void setGradjanin(Gradjanin gradjanin) { this.gradjanin = gradjanin; }
    public Oglas getOglas() { return oglas; }
    public void setOglas(Oglas oglas) { this.oglas = oglas; }
}
