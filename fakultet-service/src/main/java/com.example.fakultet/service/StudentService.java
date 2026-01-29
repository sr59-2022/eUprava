package com.example.fakultet.service;

import com.example.fakultet.model.Student;
import com.example.fakultet.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getByAuthUid(Long authUid) {
        return studentRepository.findByAuthUid(authUid)
                .orElseThrow(() -> new RuntimeException("Student nije pronađen za uid=" + authUid));
    }
}
