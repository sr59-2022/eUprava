package com.example.fakultet.repository;

import com.example.fakultet.dto.PredmetIndeksDto;
import com.example.fakultet.model.Predmet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PredmetRepository extends JpaRepository<Predmet, Long> {
    boolean existsBySifra(String sifra);

    @Query("""
    select new com.example.fakultet.dto.PredmetIndeksDto(
        p.id,
        p.sifra,
        p.naziv,
        p.espb,
        (
            select max(o.vrednost)
            from Ocena o
            join o.ispit i
            where o.student.id = :studentId
              and i.predmet.id = p.id
        ),
        case when (
            select max(o2.vrednost)
            from Ocena o2
            join o2.ispit i2
            where o2.student.id = :studentId
              and i2.predmet.id = p.id
        ) >= 6 then true else false end
    )
    from Predmet p
    where (:q is null
        or lower(p.naziv) like concat('%', :q, '%')
        or lower(p.sifra) like concat('%', :q, '%'))
    order by p.naziv
""")
    List<PredmetIndeksDto> indeksZaStudenta(
            @Param("studentId") Long studentId,
            @Param("q") String q
    );
}
