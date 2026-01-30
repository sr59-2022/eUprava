package com.example.sluzba.repository;
import com.example.sluzba.model.Oglas;
import com.example.sluzba.model.Poslodavac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface OglasRepository extends
        JpaRepository<Oglas, Long>,
        JpaSpecificationExecutor<Oglas> {
    List<Oglas> findAllByPoslodavac(Poslodavac poslodavac);

}