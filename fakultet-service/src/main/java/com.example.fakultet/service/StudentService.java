package com.example.fakultet.service;

import com.example.fakultet.dto.DiplomiranjeStatusDto;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.OcenaRepository;
import com.example.fakultet.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private static final int POTREBNO_ESPB = 240; // promeni na 180 ako treba

    private final StudentRepository studentRepository;
    private final OcenaRepository ocenaRepository;

    public StudentService(StudentRepository studentRepository, OcenaRepository ocenaRepository) {
        this.studentRepository = studentRepository;
        this.ocenaRepository = ocenaRepository;
    }

    public Student getByAuthUid(Long authUid) {
        return studentRepository.findByAuthUid(authUid)
                .orElseThrow(() -> new RuntimeException("Student nije pronađen za uid=" + authUid));
    }


    public DiplomiranjeStatusDto proveraDiplomiranja(Long authUid) {
        Student s = getByAuthUid(authUid);

        int ukupnoEspb = ocenaRepository.findPolozeniPredmetiEspb(s.getId())
                .stream()
                .mapToInt(row -> (Integer) row[1])
                .sum();

        boolean zavrsniRadOdbranjen = s.isZavrsniRadOdbranjen();

        boolean ispunjava = ukupnoEspb >= POTREBNO_ESPB && zavrsniRadOdbranjen;

        return new DiplomiranjeStatusDto(
                ispunjava ? "ISPUNJAVA" : "NE_ISPUNJAVA",
                ukupnoEspb,
                POTREBNO_ESPB,
                zavrsniRadOdbranjen
        );
    }
}
