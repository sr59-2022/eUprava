package com.example.sluzba.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prijave")
public class Prijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrijave;

    @Column(nullable = false)
    private LocalDate datumPrijave;

    @Enumerated(EnumType.STRING)
    private StatusPrijave status;

    @Column(length = 500)
    private String razlogOdbijanja;


    @ManyToOne
    @JoinColumn(name = "gradjanin_id", nullable = true)
    @JsonBackReference
    private Gradjanin gradjanin;

    @ManyToOne
    @JoinColumn(name = "diplomirani_student_id", nullable = true)
    private DiplomiraniStudent diplomiraniStudent;

    @ManyToOne
    @JoinColumn(name = "oglas_id", nullable = false)
    private Oglas oglas;


    public Prijava() {}
    public Prijava(Long idPrijave, LocalDate datumPrijave, StatusPrijave status, String razlogOdbijanja) {
        this.idPrijave = idPrijave;
        this.datumPrijave = datumPrijave;
        this.status = status;
        this.razlogOdbijanja = razlogOdbijanja;

    }


    public Long getIdPrijave() { return idPrijave; }
    public void setIdPrijave(Long idPrijave) { this.idPrijave = idPrijave; }
    public LocalDate getDatumPrijave() { return datumPrijave; }
    public void setDatumPrijave(LocalDate datumPrijave) { this.datumPrijave = datumPrijave; }
    public Gradjanin getGradjanin() { return gradjanin; }
    public void setGradjanin(Gradjanin gradjanin) { this.gradjanin = gradjanin; }
    public Oglas getOglas() { return oglas; }
    public void setOglas(Oglas oglas) { this.oglas = oglas; }

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

    public DiplomiraniStudent getDiplomiraniStudent() {
        return diplomiraniStudent;
    }

    public void setDiplomiraniStudent(DiplomiraniStudent diplomiraniStudent) {
        this.diplomiraniStudent = diplomiraniStudent;
    }
}
