package com.example.fakultet.service;

import com.example.fakultet.dto.PrijavaIspitaDto;
import com.example.fakultet.model.Ispit;
import com.example.fakultet.model.PrijavaIspita;
import com.example.fakultet.model.StatusPrijave;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.IspitRepository;
import com.example.fakultet.repository.PrijavaIspitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrijavaIspitaService {

    private final PrijavaIspitaRepository prijavaRepo;
    private final IspitRepository ispitRepo;

    public PrijavaIspitaService(PrijavaIspitaRepository prijavaRepo, IspitRepository ispitRepo) {
        this.prijavaRepo = prijavaRepo;
        this.ispitRepo = ispitRepo;
    }

    public PrijavaIspitaDto prijavi(Student student, Long ispitId) {
        Ispit ispit = ispitRepo.findById(ispitId)
                .orElseThrow(() -> new RuntimeException("Ispit ne postoji (id=" + ispitId + ")"));

        // 1) rok prijave
        if (LocalDateTime.now().isAfter(ispit.getPrijavaDo())) {
            throw new RuntimeException("Rok za prijavu je istekao.");
        }

        // 2) postoji već prijava?
        var existingOpt = prijavaRepo.findByStudentIdAndIspitId(student.getId(), ispitId);

        if (existingOpt.isPresent()) {
            PrijavaIspita existing = existingOpt.get();

            if (existing.getStatus() == StatusPrijave.PRIJAVLJEN) {
                throw new RuntimeException("Već si prijavljen/a na ovaj ispit.");
            }

            // ako je bilo OTKAZAN -> reaktiviraj
            existing.setStatus(StatusPrijave.PRIJAVLJEN);
            existing.setDatumPrijave(LocalDateTime.now());
            PrijavaIspita saved = prijavaRepo.save(existing);
            return toDto(saved);
        }

        // 3) napravi novu prijavu
        PrijavaIspita p = new PrijavaIspita();
        p.setStudent(student);
        p.setIspit(ispit);
        p.setStatus(StatusPrijave.PRIJAVLJEN);
        p.setDatumPrijave(LocalDateTime.now());

        PrijavaIspita saved = prijavaRepo.save(p);
        return toDto(saved);
    }

    public PrijavaIspitaDto otkazi(Student student, Long ispitId) {
        PrijavaIspita p = prijavaRepo.findByStudentIdAndIspitId(student.getId(), ispitId)
                .orElseThrow(() -> new RuntimeException("Ne postoji prijava za ovaj ispit."));

        if (p.getStatus() == StatusPrijave.OTKAZAN) {
            return toDto(p);
        }

        // pravilo: ne može otkazati nakon prijavaDo (možeš promijeniti na datumOdrzavanja ako želiš)
        if (LocalDateTime.now().isAfter(p.getIspit().getPrijavaDo())) {
            throw new RuntimeException("Ne možeš otkazati prijavu nakon isteka roka.");
        }

        p.setStatus(StatusPrijave.OTKAZAN);
        PrijavaIspita saved = prijavaRepo.save(p);
        return toDto(saved);
    }

    public List<PrijavaIspitaDto> mojePrijave(Student student) {
        return prijavaRepo.findByStudentId(student.getId())
                .stream()
                .map(this::toDto)
                .toList();
    }

    private PrijavaIspitaDto toDto(PrijavaIspita p) {
        // ovdje pristupamo nazivima — radi dok smo u transakciji (service je ok)
        return new PrijavaIspitaDto(
                p.getId(),
                p.getIspit().getId(),
                p.getIspit().getPredmet().getNaziv(),
                p.getIspit().getRok().getNaziv(),
                p.getIspit().getDatumOdrzavanja(),
                p.getIspit().getPrijavaDo(),
                p.getStatus(),
                p.getDatumPrijave()
        );
    }
}
