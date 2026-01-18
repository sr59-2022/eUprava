package com.example.sluzba.repository;
import com.example.sluzba.model.Oglas;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OglasRepository extends JpaRepository<Oglas, Long> {
}