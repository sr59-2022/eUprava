package com.example.sluzba.repository;

import com.example.sluzba.model.DiplomiraniStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiplomiraniStudentRepository
        extends JpaRepository<DiplomiraniStudent, Long> {

    Optional<DiplomiraniStudent> findByAuthUid(Long authUid);
}
