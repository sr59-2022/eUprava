package com.example.fakultet.repository;

import com.example.fakultet.model.IspitniRok;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IspitniRokRepository extends JpaRepository<IspitniRok, Long> {
    boolean existsByNaziv(String naziv);
}
