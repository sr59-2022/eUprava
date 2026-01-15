package com.example.auth.repository;

import com.example.auth.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Optional<Korisnik> findByKorisnickoIme(String korisnickoIme);

    boolean existsByKorisnickoIme(String korisnickoIme);
    boolean existsByEmail(String email);
}
