package com.example.fakultet.repository;

import com.example.fakultet.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByAuthUid(Long authUid);
}
