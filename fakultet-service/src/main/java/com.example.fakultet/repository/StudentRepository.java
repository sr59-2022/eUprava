package com.example.fakultet.repository;

import com.example.fakultet.model.Student;
import com.example.fakultet.model.StatusStudenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByAuthUid(Long authUid);


    @Query("""
    SELECT YEAR(s.datumDiplomiranja), COUNT(s)
    FROM Student s
    WHERE s.statusStudenta = com.example.fakultet.model.StatusStudenta.DIPLOMIRAO
      AND s.datumDiplomiranja IS NOT NULL
    GROUP BY YEAR(s.datumDiplomiranja)
    ORDER BY YEAR(s.datumDiplomiranja)
""")
    List<Object[]> countDiplomiraniPoGodini();

}
