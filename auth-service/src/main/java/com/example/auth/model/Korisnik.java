package com.example.auth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "korisnici")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String korisnickoIme;

    @Column(nullable = false, unique = true)
    private String email;

    // BCrypt hash lozinke
    @Column(nullable = false)
    private String lozinka;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "korisnik_uloge",
            joinColumns = @JoinColumn(name = "korisnik_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "uloga", nullable = false)
    private Set<Uloga> uloge;



    public Korisnik() {}



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Set<Uloga> getUloge() {
        return uloge;
    }

    public void setUloge(Set<Uloga> uloge) {
        this.uloge = uloge;
    }



}
