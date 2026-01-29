package com.example.sluzba.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "gradjani")
public class Gradjanin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(unique = true, nullable = true, length = 13)
    private String jmbg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RadniStatus radniStatus;

    @Column(nullable = true)
    private String oblastZainteresovanosti;

    @Column(nullable = false)
    private boolean zahtevZatrazen = false;

    @Column(name = "auth_gradjanin_id",nullable = false, unique = true)
    private Long authGradjaninId;


    @OneToMany(mappedBy = "gradjanin", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Prijava> prijave;

    @OneToOne(mappedBy = "gradjanin", cascade = CascadeType.ALL)
    private PotvrdaNezaposlenosti potvrda;

    public Gradjanin() {}

    public Gradjanin(Long id, String ime, String prezime, String jmbg, RadniStatus radniStatus, String oblastZainteresovanosti) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.radniStatus = radniStatus;
        this.oblastZainteresovanosti = oblastZainteresovanosti;
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
    public List<Prijava> getPrijave() { return prijave; }
    public void setPrijave(List<Prijava> prijave) { this.prijave = prijave; }
    public PotvrdaNezaposlenosti getPotvrda() { return potvrda; }
    public void setPotvrda(PotvrdaNezaposlenosti potvrda) { this.potvrda = potvrda; }
    public RadniStatus getRadniStatus() {
        return radniStatus;
    }
    public void setRadniStatus(RadniStatus radniStatus) {
        this.radniStatus = radniStatus;
    }
    public Long getAuthGradjaninId() {
        return authGradjaninId;
    }
    public void setAuthGradjaninId(Long authGradjaninId) {
        this.authGradjaninId = authGradjaninId;
    }

    public boolean isZahtevZatrazen() {
        return zahtevZatrazen;
    }

    public void setZahtevZatrazen(boolean zahtevZatrazen) {
        this.zahtevZatrazen = zahtevZatrazen;
    }

    public String getOblastZainteresovanosti() {
        return oblastZainteresovanosti;
    }

    public void setOblastZainteresovanosti(String oblastZainteresovanosti) {
        this.oblastZainteresovanosti = oblastZainteresovanosti;
    }
}
