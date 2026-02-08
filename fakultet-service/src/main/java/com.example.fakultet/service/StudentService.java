package com.example.fakultet.service;

import com.example.fakultet.dto.DiplomiranjeStatusDto;
import com.example.fakultet.dto.DiplomiraniPoGodiniDto;
import com.example.fakultet.dto.StudentRowDto;
import com.example.fakultet.model.StatusStudenta;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.OcenaRepository;
import com.example.fakultet.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private static final int POTREBNO_ESPB = 240;

    private final StudentRepository studentRepository;
    private final OcenaRepository ocenaRepository;

    public StudentService(StudentRepository studentRepository,
                          OcenaRepository ocenaRepository) {
        this.studentRepository = studentRepository;
        this.ocenaRepository = ocenaRepository;
    }

    @Transactional
    public void createIfMissing(Long authUid, String ime, String prezime, String brojIndeksa) {
        if (studentRepository.findByAuthUid(authUid).isPresent()) return;

        Student s = new Student();
        s.setAuthUid(authUid);

        String safeIndex = (brojIndeksa != null && !brojIndeksa.isBlank())
                ? brojIndeksa.trim()
                : "AUTO-" + authUid;

        s.setBrojIndeksa(safeIndex);
        s.setIme((ime != null && !ime.isBlank()) ? ime.trim() : "NOVI");
        s.setPrezime((prezime != null && !prezime.isBlank()) ? prezime.trim() : "STUDENT");

        s.setStatusStudenta(StatusStudenta.AKTIVAN);
        s.setZavrsniRadOdbranjen(false);
        s.setDatumDiplomiranja(null);

        studentRepository.save(s);
    }

    public Student getByAuthUid(Long authUid) {
        return studentRepository.findByAuthUid(authUid)
                .orElseThrow(() -> new RuntimeException("Student nije pronađen za uid=" + authUid));
    }

    private int izracunajUkupnoEspb(Long studentId) {
        return ocenaRepository.findPolozeniPredmetiEspb(studentId)
                .stream()
                .mapToInt(row -> ((Number) row[1]).intValue())
                .sum();
    }


    public DiplomiranjeStatusDto proveraDiplomiranja(Long authUid) {
        Student s = getByAuthUid(authUid);
        int ukupnoEspb = izracunajUkupnoEspb(s.getId());

        boolean zavrsniRadOdbranjen = s.isZavrsniRadOdbranjen();
        boolean ispunjava = ukupnoEspb >= POTREBNO_ESPB && zavrsniRadOdbranjen;

        return new DiplomiranjeStatusDto(
                ispunjava ? "ISPUNJAVA" : "NE_ISPUNJAVA",
                ukupnoEspb,
                POTREBNO_ESPB,
                zavrsniRadOdbranjen
        );
    }


    @Transactional
    public void diplomirajAkoIspunjava(Long authUid) {
        Student s = getByAuthUid(authUid);

        if (s.getStatusStudenta() == StatusStudenta.DIPLOMIRAO) return;

        int ukupnoEspb = izracunajUkupnoEspb(s.getId());
        boolean zavrsni = s.isZavrsniRadOdbranjen();

        if (ukupnoEspb >= POTREBNO_ESPB && zavrsni) {
            s.setStatusStudenta(StatusStudenta.DIPLOMIRAO);
            s.setDatumDiplomiranja(LocalDate.now());
            studentRepository.save(s);
        }
    }


    @Transactional
    public void profesorPostaviStatus(Long studentId, StatusStudenta noviStatus) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student nije pronađen id=" + studentId));

        if (noviStatus == StatusStudenta.DIPLOMIRAO) {
            int ukupnoEspb = izracunajUkupnoEspb(s.getId());
            boolean zavrsni = s.isZavrsniRadOdbranjen();

            if (ukupnoEspb < POTREBNO_ESPB) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ne može diplomirati: nema dovoljno ESPB (" + ukupnoEspb + "/" + POTREBNO_ESPB + ")."
                );
            }
            if (!zavrsni) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ne može diplomirati: završni rad nije odbranjen."
                );
            }

            s.setStatusStudenta(StatusStudenta.DIPLOMIRAO);
            if (s.getDatumDiplomiranja() == null) {
                s.setDatumDiplomiranja(LocalDate.now());
            }
            studentRepository.save(s);
            return;
        }


        s.setStatusStudenta(noviStatus);

        if (noviStatus != StatusStudenta.DIPLOMIRAO) {
            s.setDatumDiplomiranja(null);
        }

        studentRepository.save(s);
    }

    public List<DiplomiraniPoGodiniDto> izvestajDiplomiraniPoGodini() {
        return studentRepository.countDiplomiraniPoGodini()
                .stream()
                .map(r -> new DiplomiraniPoGodiniDto((Integer) r[0], (Long) r[1]))
                .toList();
    }

    @Transactional
    public void postaviZavrsniRad(Long studentId, boolean odbranjen) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student nije pronađen id=" + studentId));

        s.setZavrsniRadOdbranjen(odbranjen);


        if (!odbranjen && s.getStatusStudenta() == StatusStudenta.DIPLOMIRAO) {
            s.setStatusStudenta(StatusStudenta.AKTIVAN);
            s.setDatumDiplomiranja(null);
        }

        studentRepository.save(s);
    }

    public Page<StudentRowDto> listajStudente(String q, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("prezime").ascending().and(Sort.by("ime").ascending()));
        return studentRepository.search(q, pageable)
                .map(st -> new StudentRowDto(
                        st.getId(),
                        st.getBrojIndeksa(),
                        st.getIme(),
                        st.getPrezime(),
                        st.getStatusStudenta().name(),
                        st.isZavrsniRadOdbranjen()
                ));
    }
}