package com.example.sluzba.repository;

import com.example.sluzba.model.Gradjanin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradjaninRepository extends JpaRepository<Gradjanin, Long> {

    Optional<Gradjanin> findByAuthGradjaninId(Long authGradjaninId);
}