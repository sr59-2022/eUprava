package com.example.fakultet.service;

import com.example.fakultet.dto.IspitCreateDto;
import com.example.fakultet.model.Ispit;
import com.example.fakultet.model.IspitniRok;
import com.example.fakultet.model.Predmet;
import com.example.fakultet.repository.IspitRepository;
import com.example.fakultet.repository.IspitniRokRepository;
import com.example.fakultet.repository.PredmetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IspitService {

    private final IspitRepository ispitRepo;
    private final PredmetRepository predmetRepo;
    private final IspitniRokRepository rokRepo;

    public IspitService(IspitRepository ispitRepo, PredmetRepository predmetRepo, IspitniRokRepository rokRepo) {
        this.ispitRepo = ispitRepo;
        this.predmetRepo = predmetRepo;
        this.rokRepo = rokRepo;
    }

    @Transactional
    public Ispit kreiraj(IspitCreateDto dto) {
        if (dto.getPrijavaDo().isAfter(dto.getDatumOdrzavanja())) {
            throw new RuntimeException("Prijava do ne može biti posle datuma održavanja.");
        }

        if (dto.getDatumOdrzavanja().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Ne možeš zakazati ispit u prošlosti.");
        }


        if (ispitRepo.existsByPredmetIdAndRokId(dto.getPredmetId(), dto.getRokId())) {
            throw new RuntimeException("Ispit za taj predmet u tom ispitnom roku već postoji.");
        }

        Predmet p = predmetRepo.findById(dto.getPredmetId())
                .orElseThrow(() -> new RuntimeException("Predmet ne postoji (id=" + dto.getPredmetId() + ")"));

        IspitniRok r = rokRepo.findById(dto.getRokId())
                .orElseThrow(() -> new RuntimeException("Rok ne postoji (id=" + dto.getRokId() + ")"));


        var datumIspita = dto.getDatumOdrzavanja().toLocalDate();
        if (r.getPocetak() != null && datumIspita.isBefore(r.getPocetak())) {
            throw new RuntimeException("Datum ispita mora biti u okviru izabranog roka (pre početka roka).");
        }
        if (r.getKraj() != null && datumIspita.isAfter(r.getKraj())) {
            throw new RuntimeException("Datum ispita mora biti u okviru izabranog roka (posle kraja roka).");
        }

        Ispit i = new Ispit();
        i.setPredmet(p);
        i.setRok(r);
        i.setDatumOdrzavanja(dto.getDatumOdrzavanja());
        i.setPrijavaDo(dto.getPrijavaDo());
        i.setSala(dto.getSala().trim());

        return ispitRepo.save(i);
    }


    @Transactional(readOnly = true)
    public List<Ispit> sviAktivniZaPrijavu() {
        return ispitRepo.findByPrijavaDoAfter(LocalDateTime.now());
    }
}
