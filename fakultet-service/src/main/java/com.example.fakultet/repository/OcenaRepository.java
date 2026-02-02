package com.example.fakultet.repository;

import com.example.fakultet.dto.OcenaPregledDto;
import com.example.fakultet.model.Ocena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
          and (:min is null or o.vrednost >= :min)
          and (:max is null or o.vrednost <= :max)
          and (
                :polozio is null
                or (:polozio = true and o.vrednost >= 6)
                or (:polozio = false and o.vrednost < 6)
              )
          and (
                :predmetQ is null
                or lower(p.naziv) like concat('%', :predmetQ, '%')
                or lower(p.sifra) like concat('%', :predmetQ, '%')
              )
        order by o.datumUpisa desc
    """)
    List<OcenaPregledDto> findPregledByFilters(
            @Param("studentId") Long studentId,
            @Param("min") Integer min,
            @Param("max") Integer max,
            @Param("polozio") Boolean polozio,
            @Param("predmetQ") String predmetQ
    );


    @Query("""
        select p.id, coalesce(p.espb, 0)
        from Ocena o
        join o.ispit i
        join i.predmet p
        where o.student.id = :studentId
          and o.vrednost >= 6
        group by p.id, p.espb
    """)
    List<Object[]> findPolozeniPredmetiEspb(@Param("studentId") Long studentId);

    @Query("""
  select distinct p.id
  from Ocena o
  join o.ispit i
  join i.predmet p
  where o.student.id = :studentId
    and o.vrednost >= 6
""")
    List<Long> findPolozeniPredmetIds(@Param("studentId") Long studentId);

}
