package com.example.sluzba.repository;

import com.example.sluzba.model.Obavestenje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObavestenjeRepository extends JpaRepository<Obavestenje, Long> {

    long countByPoslodavacIdAndProcitanoFalse(Long poslodavacId);
    List<Obavestenje> findByPoslodavacIdAndProcitanoFalseOrderByDatumDesc(Long poslodavacId);

}
