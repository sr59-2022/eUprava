package com.example.fakultet.repository;

import com.example.fakultet.dto.OcenaPregledDto;
import com.example.fakultet.model.Ocena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OcenaRepository extends JpaRepository<Ocena, Long> {

    @Query("""
        select new com.example.fakultet.dto.OcenaPregledDto(
            o.id, o.vrednost, o.datumUpisa,
            i.id, i.datumOdrzavanja,
            p.id, p.sifra, p.naziv, p.espb,
            r.naziv
        )
        from Ocena o
        join o.ispit i
        join i.predmet p
        join i.rok r
        where o.student.id = :studentId
        order by o.datumUpisa desc
    """)
    List<OcenaPregledDto> findPregledByStudentId(Long studentId);
}
