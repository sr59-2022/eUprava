package com.example.sluzba.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Oglas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOglasa;

    private String nazivPozicije;
    private String opis;
    private LocalDate datumObjave;
    private LocalDate rokPrijave;

    @ManyToOne
    @JoinColumn(name = "poslodavac_id")
    private Poslodavac poslodavac;

    @OneToMany(mappedBy = "oglas", cascade = CascadeType.ALL)
    private List<Prijava> prijave;

    public Oglas() {}
    public Oglas(Long idOglasa, String nazivPozicije, String opis) {
        this.idOglasa = idOglasa;
        this.nazivPozicije = nazivPozicije;
        this.opis = opis;
    }


    public Long getIdOglasa() { return idOglasa; }
    public void setIdOglasa(Long idOglasa) { this.idOglasa = idOglasa; }
    public String getNazivPozicije() { return nazivPozicije; }
    public void setNazivPozicije(String nazivPozicije) { this.nazivPozicije = nazivPozicije; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    public LocalDate getDatumObjave() { return datumObjave; }
    public void setDatumObjave(LocalDate datumObjave) { this.datumObjave = datumObjave; }
    public LocalDate getRokPrijave() { return rokPrijave; }
    public void setRokPrijave(LocalDate rokPrijave) { this.rokPrijave = rokPrijave; }
    public Poslodavac getPoslodavac() { return poslodavac; }
    public void setPoslodavac(Poslodavac poslodavac) { this.poslodavac = poslodavac; }
    public List<Prijava> getPrijave() { return prijave; }
    public void setPrijave(List<Prijava> prijave) { this.prijave = prijave; }
}
