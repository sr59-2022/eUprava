package com.example.fakultet.repository;

import com.example.fakultet.model.Student;
import com.example.fakultet.model.StatusStudenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByAuthUid(Long authUid);
    List<Student> findByStatusStudenta(StatusStudenta statusStudenta);



    @Query("""
    SELECT YEAR(s.datumDiplomiranja), COUNT(s)
    FROM Student s
    WHERE s.statusStudenta = com.example.fakultet.model.StatusStudenta.DIPLOMIRAO
      AND s.datumDiplomiranja IS NOT NULL
    GROUP BY YEAR(s.datumDiplomiranja)
    ORDER BY YEAR(s.datumDiplomiranja)
""")
    List<Object[]> countDiplomiraniPoGodini();

    @Query("""
    SELECT s FROM Student s
    WHERE (:q IS NULL OR :q = '' OR
           LOWER(s.ime) LIKE LOWER(CONCAT('%', :q, '%')) OR
           LOWER(s.prezime) LIKE LOWER(CONCAT('%', :q, '%')) OR
           LOWER(s.brojIndeksa) LIKE LOWER(CONCAT('%', :q, '%')))
""")
    Page<Student> search(@org.springframework.data.repository.query.Param("q") String q, Pageable pageable);

}


