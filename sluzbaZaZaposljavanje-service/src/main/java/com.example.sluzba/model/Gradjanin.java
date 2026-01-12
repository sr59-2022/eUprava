package com.example.sluzba.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Gradjanin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String jmbg;
    private String statusNezaposlenosti;

    @OneToMany(mappedBy = "gradjanin", cascade = CascadeType.ALL)
    private List<Prijava> prijave;

    @OneToOne(mappedBy = "gradjanin", cascade = CascadeType.ALL)
    private PotvrdaNezaposlenosti potvrda;

    public Gradjanin() {}

    public Gradjanin(Long id, String ime, String prezime, String jmbg, String statusNezaposlenosti) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.statusNezaposlenosti = statusNezaposlenosti;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public String getJmbg() { return jmbg; }
    public void setJmbg(String jmbg) { this.jmbg = jmbg; }
    public String getStatusNezaposlenosti() { return statusNezaposlenosti; }
    public void setStatusNezaposlenosti(String statusNezaposlenosti) { this.statusNezaposlenosti = statusNezaposlenosti; }
    public List<Prijava> getPrijave() { return prijave; }
    public void setPrijave(List<Prijava> prijave) { this.prijave = prijave; }
    public PotvrdaNezaposlenosti getPotvrda() { return potvrda; }
    public void setPotvrda(PotvrdaNezaposlenosti potvrda) { this.potvrda = potvrda; }
}
