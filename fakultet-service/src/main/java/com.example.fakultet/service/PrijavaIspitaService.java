package com.example.fakultet.service;

import com.example.fakultet.dto.IspitOpcijaDto;
import com.example.fakultet.dto.PrijavaIspitaDto;
import com.example.fakultet.model.Ispit;
import com.example.fakultet.model.PrijavaIspita;
import com.example.fakultet.model.StatusPrijave;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.IspitRepository;
import com.example.fakultet.repository.OcenaRepository;
import com.example.fakultet.repository.PrijavaIspitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PrijavaIspitaService {

    private final PrijavaIspitaRepository prijavaRepo;
    private final IspitRepository ispitRepo;
    private final OcenaRepository ocenaRepo;

    public PrijavaIspitaService(
            PrijavaIspitaRepository prijavaRepo,
            IspitRepository ispitRepo,
            OcenaRepository ocenaRepo
    ) {
        this.prijavaRepo = prijavaRepo;
        this.ispitRepo = ispitRepo;
        this.ocenaRepo = ocenaRepo;
    }

    public PrijavaIspitaDto prijavi(Student student, Long ispitId) {
        Ispit ispit = ispitRepo.findById(ispitId)
                .orElseThrow(() -> new RuntimeException("Ispit ne postoji (id=" + ispitId + ")"));

        if (LocalDateTime.now().isAfter(ispit.getPrijavaDo())) {
            throw new RuntimeException("Rok za prijavu je istekao.");
        }

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

        // nova prijava
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

    @Transactional(readOnly = true)
    public List<IspitOpcijaDto> dostupniIspiti(Student student) {


        List<Ispit> aktivni = ispitRepo.findByPrijavaDoAfter(LocalDateTime.now());


        Set<Long> vecPrijavljeni = prijavaRepo
                .findByStudentIdAndStatus(student.getId(), StatusPrijave.PRIJAVLJEN)
                .stream()
                .map(p -> p.getIspit().getId())
                .collect(Collectors.toSet());


        Set<Long> polozeniPredmeti = new HashSet<>(
                ocenaRepo.findPolozeniPredmetIds(student.getId())
        );


        return aktivni.stream()
                .filter(i -> !vecPrijavljeni.contains(i.getId()))
                .filter(i -> !polozeniPredmeti.contains(i.getPredmet().getId()))
                .sorted((a, b) -> a.getDatumOdrzavanja().compareTo(b.getDatumOdrzavanja()))
                .map(i -> new IspitOpcijaDto(
                        i.getId(),
                        i.getPredmet().getNaziv(),
                        i.getRok().getNaziv(),
                        i.getDatumOdrzavanja(),
                        i.getPrijavaDo()
                ))
                .toList();
    }
}
