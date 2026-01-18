package com.example.sluzba.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "poslodavci")
public class Poslodavac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auth_poslodavac_id", nullable = false, unique = true)
    private Long authPoslodavacId;

    public Poslodavac() {}
    public Poslodavac(Long id) {
        this.id = id;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAuthPoslodavacId() {
        return authPoslodavacId;
    }

    public void setAuthPoslodavacId(Long authPoslodavacId) {
        this.authPoslodavacId = authPoslodavacId;
    }
}
