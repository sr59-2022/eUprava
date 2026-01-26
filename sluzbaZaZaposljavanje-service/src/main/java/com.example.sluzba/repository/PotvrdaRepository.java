package com.example.sluzba.repository;

import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.model.PotvrdaNezaposlenosti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotvrdaRepository
        extends JpaRepository<PotvrdaNezaposlenosti, Long> {

    boolean existsByGradjanin(Gradjanin gradjanin);
}

