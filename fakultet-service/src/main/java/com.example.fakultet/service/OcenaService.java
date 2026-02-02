package com.example.fakultet.service;

import com.example.fakultet.dto.OcenaPregledDto;
import com.example.fakultet.model.Ispit;
import com.example.fakultet.model.Ocena;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.IspitRepository;
import com.example.fakultet.repository.OcenaRepository;
import com.example.fakultet.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OcenaService {

    private final OcenaRepository ocenaRepository;
    private final StudentService studentService;

    private final StudentRepository studentRepository;
    private final IspitRepository ispitRepository;

    public OcenaService(
            OcenaRepository ocenaRepository,
            StudentService studentService,
            StudentRepository studentRepository,
            IspitRepository ispitRepository
    ) {
        this.ocenaRepository = ocenaRepository;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.ispitRepository = ispitRepository;
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


    @Transactional
    public void upisiOcenu(Long studentId, Long ispitId, int vrednost) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student ne postoji (id=" + studentId + ")"));

        Ispit i = ispitRepository.findById(ispitId)
                .orElseThrow(() -> new RuntimeException("Ispit ne postoji (id=" + ispitId + ")"));

        Ocena o = ocenaRepository.findByStudentIdAndIspitId(studentId, ispitId)
                .orElseGet(Ocena::new);

        o.setStudent(s);
        o.setIspit(i);
        o.setVrednost(vrednost);
        o.setDatumUpisa(LocalDate.now());

        ocenaRepository.save(o);
    }
}
