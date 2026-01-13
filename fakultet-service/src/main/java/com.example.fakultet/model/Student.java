package com.example.fakultet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "studenti",
        indexes = {
                @Index(name = "idx_studenti_broj_indeksa", columnList = "broj_indeksa")
        }
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "broj_indeksa", nullable = false, unique = true, length = 30)
    private String brojIndeksa;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String ime;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String prezime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_studenta", nullable = false, length = 20)
    private StatusStudenta statusStudenta;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ocena> ocene = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Uverenje> uverenja = new ArrayList<>();

    public Student() {}

    public Long getId() { return id; }

    public String getBrojIndeksa() { return brojIndeksa; }
    public void setBrojIndeksa(String brojIndeksa) { this.brojIndeksa = brojIndeksa; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public StatusStudenta getStatusStudenta() { return statusStudenta; }
    public void setStatusStudenta(StatusStudenta statusStudenta) { this.statusStudenta = statusStudenta; }

    public List<Ocena> getOcene() { return ocene; }
    public void setOcene(List<Ocena> ocene) { this.ocene = ocene; }

    public List<Uverenje> getUverenja() { return uverenja; }
    public void setUverenja(List<Uverenje> uverenja) { this.uverenja = uverenja; }
}
