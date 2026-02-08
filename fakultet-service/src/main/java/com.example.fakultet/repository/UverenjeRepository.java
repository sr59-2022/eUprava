package com.example.fakultet.repository;

import com.example.fakultet.model.Uverenje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UverenjeRepository extends JpaRepository<Uverenje, Long> {

    List<Uverenje> findByStudentIdOrderByDatumIzdavanjaDesc(Long studentId);

    boolean existsByBrojDokumenta(String brojDokumenta);
}
