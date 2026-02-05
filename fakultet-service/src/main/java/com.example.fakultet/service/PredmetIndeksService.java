package com.example.fakultet.service;

import com.example.fakultet.dto.PredmetIndeksDto;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.PredmetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredmetIndeksService {

    private final PredmetRepository predmetRepository;
    private final StudentService studentService;

    public PredmetIndeksService(PredmetRepository predmetRepository, StudentService studentService) {
        this.predmetRepository = predmetRepository;
        this.studentService = studentService;
    }

    public List<PredmetIndeksDto> indeks(Long authUid, String predmet, Boolean polozio) {
        Student s = studentService.getByAuthUid(authUid);
        String q = (predmet == null || predmet.isBlank()) ? null : predmet.trim().toLowerCase();

        List<PredmetIndeksDto> all = predmetRepository.indeksZaStudenta(s.getId(), q);

        if (polozio == null) return all;
        return all.stream().filter(x -> x.polozio() == polozio).toList();
    }
}