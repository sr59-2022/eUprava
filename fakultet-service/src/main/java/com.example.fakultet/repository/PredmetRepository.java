package com.example.fakultet.repository;

import com.example.fakultet.model.Predmet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredmetRepository extends JpaRepository<Predmet, Long> {
    boolean existsBySifra(String sifra);
}
