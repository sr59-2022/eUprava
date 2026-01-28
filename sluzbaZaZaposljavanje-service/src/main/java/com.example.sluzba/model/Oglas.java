package com.example.sluzba.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "oglasi")
public class Oglas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOglasa;

    @Column(nullable = false)
    private String nazivPozicije;

    @Column(nullable = false, length = 1000)
    private String opis;

    @Column(nullable = false)
    private LocalDate datumObjave;

    @Column(nullable = false)
    private LocalDate rokPrijave;

    @Column(nullable = false)
    private String nazivKompanije;

    @Column(nullable = true)
    private String oblast;

    @ManyToOne
    @JoinColumn(name = "poslodavac_id")
    private Poslodavac poslodavac;

    @OneToMany(mappedBy = "oglas", cascade = CascadeType.ALL)
    private List<Prijava> prijave;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipOglasa tipOglasa;


    public Oglas() {}

    public Oglas(String nazivPozicije, String opis, LocalDate datumObjave,
                 LocalDate rokPrijave, String nazivKompanije,TipOglasa tipOglasa, String oblast, Poslodavac poslodavac) {
        this.nazivPozicije = nazivPozicije;
        this.opis = opis;
        this.datumObjave = datumObjave;
        this.rokPrijave = rokPrijave;
        this.nazivKompanije = nazivKompanije;
        this.tipOglasa = tipOglasa;
        this.oblast = oblast;
        this.poslodavac = poslodavac;
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

    public TipOglasa getTipOglasa() {
        return tipOglasa;
    }

    public void setTipOglasa(TipOglasa tipOglasa) {
        this.tipOglasa = tipOglasa;
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
}
