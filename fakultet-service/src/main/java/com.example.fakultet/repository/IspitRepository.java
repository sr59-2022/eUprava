package com.example.fakultet.repository;

import com.example.fakultet.model.Ispit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IspitRepository extends JpaRepository<Ispit, Long> {
    List<Ispit> findByPrijavaDoAfter(LocalDateTime now);
    List<Ispit> findAllByOrderByDatumOdrzavanjaDesc();

}
