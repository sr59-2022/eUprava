package com.example.fakultet.repository;

import com.example.fakultet.model.PrijavaIspita;
import com.example.fakultet.model.StatusPrijave;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface PrijavaIspitaRepository extends JpaRepository<PrijavaIspita, Long> {

    Optional<PrijavaIspita> findByStudentIdAndIspitId(Long studentId, Long ispitId);

    List<PrijavaIspita> findByStudentId(Long studentId);

    List<PrijavaIspita> findByStudentIdAndStatus(Long studentId, StatusPrijave status);


    List<PrijavaIspita> findByIspitId(Long ispitId);
    List<PrijavaIspita> findByIspitIdAndStatus(Long ispitId, StatusPrijave status);

}
