package com.example.sluzba.repository;


import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.model.Oglas;
import com.example.sluzba.model.Prijava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {

    List<Prijava> findByGradjaninId(Long gradjaninId);
    List<Prijava> findByOglasPoslodavacId(Long poslodavacId);
    boolean existsByGradjaninIdAndOglas_IdOglasa(Long gradjaninId, Long oglasId);
}

