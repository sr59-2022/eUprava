package com.example.sluzba.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Poslodavac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String nazivKompanije;
    private String brTelefona;

    @OneToMany(mappedBy = "poslodavac", cascade = CascadeType.ALL)
    private List<Oglas> oglasi;

    public Poslodavac() {}
    public Poslodavac(Long id, String ime, String prezime, String nazivKompanije, String brTelefona) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.nazivKompanije = nazivKompanije;
        this.brTelefona = brTelefona;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public String getNazivKompanije() { return nazivKompanije; }
    public void setNazivKompanije(String nazivKompanije) { this.nazivKompanije = nazivKompanije; }
    public String getBrTelefona() { return brTelefona; }
    public void setBrTelefona(String brTelefona) { this.brTelefona = brTelefona; }
    public List<Oglas> getOglasi() { return oglasi; }
    public void setOglasi(List<Oglas> oglasi) { this.oglasi = oglasi; }
}
