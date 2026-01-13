package com.example.fakultet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

@Entity
@Table(
        name = "ocene",
        uniqueConstraints = @UniqueConstraint(name = "uq_ocene_student_ispit", columnNames = {"student_id", "ispit_id"}),
        indexes = {
                @Index(name = "idx_ocene_student", columnList = "student_id"),
                @Index(name = "idx_ocene_ispit", columnList = "ispit_id")
        }
)
public class Ocena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(5)
    @Max(10)
    @Column(nullable = false)
    private int vrednost;

    @Column(name = "datum_upisa", nullable = false)
    private LocalDate datumUpisa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ispit_id", nullable = false)
    private Ispit ispit;

    public Ocena() {}

    public Long getId() { return id; }

    public int getVrednost() { return vrednost; }
    public void setVrednost(int vrednost) { this.vrednost = vrednost; }

    public LocalDate getDatumUpisa() { return datumUpisa; }
    public void setDatumUpisa(LocalDate datumUpisa) { this.datumUpisa = datumUpisa; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Ispit getIspit() { return ispit; }
    public void setIspit(Ispit ispit) { this.ispit = ispit; }
}
