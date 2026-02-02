package com.example.fakultet.service;

import com.example.fakultet.dto.IspitniRokCreateDto;
import com.example.fakultet.model.IspitniRok;
import com.example.fakultet.repository.IspitniRokRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IspitniRokService {

    private final IspitniRokRepository rokRepo;

    public IspitniRokService(IspitniRokRepository rokRepo) {
        this.rokRepo = rokRepo;
    }

    @Transactional
    public IspitniRok kreiraj(IspitniRokCreateDto dto) {
        if (dto.getPocetak().isAfter(dto.getKraj())) {
            throw new RuntimeException("Pocetak ne može biti posle kraja.");
        }

        if (rokRepo.existsByNaziv(dto.getNaziv().trim())) {
            throw new RuntimeException("Rok sa tim nazivom već postoji.");
        }

        IspitniRok r = new IspitniRok();
        r.setNaziv(dto.getNaziv().trim());
        r.setPocetak(dto.getPocetak());
        r.setKraj(dto.getKraj());

        return rokRepo.save(r);
    }

    @Transactional(readOnly = true)
    public List<IspitniRok> svi() {
        return rokRepo.findAll();
    }
}
