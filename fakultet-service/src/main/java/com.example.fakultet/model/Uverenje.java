package com.example.fakultet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(
        name = "uverenja",
        indexes = {
                @Index(name = "idx_uverenja_student", columnList = "student_id"),
                @Index(name = "idx_uverenja_broj_dokumenta", columnList = "broj_dokumenta")
        }
)
public class Uverenje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(name = "broj_dokumenta", nullable = false, unique = true, length = 40)
    private String brojDokumenta;

    @Column(name = "datum_izdavanja", nullable = false)
    private LocalDate datumIzdavanja;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipUverenja tip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Uverenje() {}

    public Long getId() { return id; }

    public String getBrojDokumenta() { return brojDokumenta; }
    public void setBrojDokumenta(String brojDokumenta) { this.brojDokumenta = brojDokumenta; }

    public LocalDate getDatumIzdavanja() { return datumIzdavanja; }
    public void setDatumIzdavanja(LocalDate datumIzdavanja) { this.datumIzdavanja = datumIzdavanja; }

    public TipUverenja getTip() { return tip; }
    public void setTip(TipUverenja tip) { this.tip = tip; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}
