package com.example.fakultet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "prijave_ispita",
        uniqueConstraints = @UniqueConstraint(name = "uq_prijava_student_ispit", columnNames = {"student_id", "ispit_id"})
)
public class PrijavaIspita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ispit_id", nullable = false)
    private Ispit ispit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPrijave status = StatusPrijave.PRIJAVLJEN;

    @Column(name = "datum_prijave", nullable = false)
    private LocalDateTime datumPrijave = LocalDateTime.now();

    public PrijavaIspita() {}

    public Long getId() { return id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Ispit getIspit() { return ispit; }
    public void setIspit(Ispit ispit) { this.ispit = ispit; }

    public StatusPrijave getStatus() { return status; }
    public void setStatus(StatusPrijave status) { this.status = status; }

    public LocalDateTime getDatumPrijave() { return datumPrijave; }
    public void setDatumPrijave(LocalDateTime datumPrijave) { this.datumPrijave = datumPrijave; }
}
