package com.example.sluzba.repository;
import com.example.sluzba.model.Poslodavac;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PoslodavacRepository extends JpaRepository<Poslodavac, Long> {
    Optional<Poslodavac> findByAuthPoslodavacId(Long authPoslodavacId);
}
