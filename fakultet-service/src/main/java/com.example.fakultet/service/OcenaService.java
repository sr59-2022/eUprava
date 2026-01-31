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


    public List<OcenaPregledDto> mojeOceneFilter(
            Long authUid,
            Integer ocena,
            Integer ocenaMin,
            Integer ocenaMax,
            Boolean polozio,
            String predmet
    ) {
        Student s = studentService.getByAuthUid(authUid);

        Integer min = (ocena != null) ? ocena : ocenaMin;
        Integer max = (ocena != null) ? ocena : ocenaMax;

        String q = (predmet == null || predmet.isBlank()) ? null : predmet.trim().toLowerCase();

        return ocenaRepository.findPregledByFilters(s.getId(), min, max, polozio, q);
    }


    public List<OcenaPregledDto> mojeOcene(Long authUid) {
        return mojeOceneFilter(authUid, null, null, null, null, null);
    }
}
