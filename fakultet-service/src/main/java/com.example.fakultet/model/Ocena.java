package com.example.fakultet.model;

import jakarta.persistence.*;

@Entity
public class Ocena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int vrednost;
    private String datumUpisa;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Ispit ispit;

    public Long getId() {
        return id;
    }

    public int getVrednost() {
        return vrednost;
    }

    public void setVrednost(int vrednost) {
        this.vrednost = vrednost;
    }

    public String getDatumUpisa() {
        return datumUpisa;
    }

    public void setDatumUpisa(String datumUpisa) {
        this.datumUpisa = datumUpisa;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Ispit getIspit() {
        return ispit;
    }

    public void setIspit(Ispit ispit) {
        this.ispit = ispit;
    }
}