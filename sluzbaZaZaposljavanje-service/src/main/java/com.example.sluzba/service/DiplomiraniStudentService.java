package com.example.sluzba.service;

import com.example.sluzba.dto.DiplomiraniStudentDto;
import com.example.sluzba.model.DiplomiraniStudent;
import com.example.sluzba.repository.DiplomiraniStudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiplomiraniStudentService {

    private final DiplomiraniStudentRepository repo;

    public DiplomiraniStudentService(DiplomiraniStudentRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void sacuvajDiplomirane(List<DiplomiraniStudentDto> studenti) {

        for (DiplomiraniStudentDto dto : studenti) {

            repo.findByBrojIndeksa(dto.getBrojIndeksa())
                    .orElseGet(() -> {
                        DiplomiraniStudent ds = new DiplomiraniStudent();
                        ds.setIme(dto.getIme());
                        ds.setPrezime(dto.getPrezime());
                        ds.setBrojIndeksa(dto.getBrojIndeksa());
                        ds.setDatumDiplomiranja(LocalDate.now());
                        ds.setDostupanZaZaposljavanje(true);
                        return repo.save(ds);
                    });
        }
    }

    public List<DiplomiraniStudent> sviDiplomirani() {
        return repo.findAll();
    }
}
