package com.example.fakultet.service;

import com.example.fakultet.dto.OcenaPregledDto;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.OcenaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcenaService {

    private final OcenaRepository ocenaRepository;
    private final StudentService studentService;

    public OcenaService(OcenaRepository ocenaRepository, StudentService studentService) {
        this.ocenaRepository = ocenaRepository;
        this.studentService = studentService;
    }

    public List<OcenaPregledDto> mojeOcene(Long authUid) {
        Student s = studentService.getByAuthUid(authUid);
        return ocenaRepository.findPregledByStudentId(s.getId());
    }
}
