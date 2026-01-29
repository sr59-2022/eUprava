package com.example.sluzba.service;

import com.example.sluzba.dto.PrikazPrijaveDTO;
import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.model.Oglas;
import com.example.sluzba.model.Prijava;
import com.example.sluzba.model.StatusPrijave;
import com.example.sluzba.repository.GradjaninRepository;
import com.example.sluzba.repository.OglasRepository;
import com.example.sluzba.repository.PrijavaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrijavaService {

    private final PrijavaRepository prijavaRepository;
    private final GradjaninRepository gradjaninRepository;
    private final OglasRepository oglasRepository;

    public PrijavaService(PrijavaRepository prijavaRepository,
                          GradjaninRepository gradjaninRepository,
                          OglasRepository oglasRepository) {
        this.prijavaRepository = prijavaRepository;
        this.gradjaninRepository = gradjaninRepository;
        this.oglasRepository = oglasRepository;
    }

    @Transactional
    public Prijava prijaviSe(Long authGradjaninId, Long oglasId) {

        Gradjanin g = gradjaninRepository
                .findByAuthGradjaninId(authGradjaninId)
                .orElseThrow(() -> new RuntimeException("Gradjanin ne postoji"));

        if (prijavaRepository.existsByGradjaninIdAndOglas_IdOglasa(g.getId(), oglasId)) {
            throw new RuntimeException("Već ste se prijavili na ovaj oglas.");
        }

        Oglas o = oglasRepository.findById(oglasId)
                .orElseThrow(() -> new RuntimeException("Oglas ne postoji"));

        Prijava p = new Prijava();
        p.setGradjanin(g);
        p.setOglas(o);
        p.setDatumPrijave(LocalDate.now());
        p.setStatus(StatusPrijave.PODNETA);

        return prijavaRepository.save(p);
    }

    public List<Prijava> prijaveZaGradjanina(Long authGradjaninId) {
        Gradjanin g = gradjaninRepository
                .findByAuthGradjaninId(authGradjaninId)
                .orElseThrow(() -> new RuntimeException("Gradjanin ne postoji"));

        return prijavaRepository.findByGradjaninId(g.getId());
    }


    public List<PrikazPrijaveDTO> prijaveZaGradjaninaDTO(Long authGradjaninId) {

        Gradjanin g = gradjaninRepository
                .findByAuthGradjaninId(authGradjaninId)
                .orElseThrow(() -> new RuntimeException("Gradjanin ne postoji"));

        return prijavaRepository.findByGradjaninId(g.getId())
                .stream()
                .map(p -> new PrikazPrijaveDTO(
                        p.getOglas().getIdOglasa(),
                        p.getIdPrijave(),
                        p.getOglas().getNazivPozicije(),
                        p.getOglas().getNazivKompanije(),
                        p.getDatumPrijave(),
                        p.getStatus(),
                        p.getRazlogOdbijanja()
                ))
                .collect(Collectors.toList());
    }
    public PrikazPrijaveDTO prijavaDTO(Prijava p) {
        return new PrikazPrijaveDTO(
                p.getOglas().getIdOglasa(),
                p.getIdPrijave(),
                p.getOglas().getNazivPozicije(),
                p.getOglas().getNazivKompanije(),
                p.getDatumPrijave(),
                p.getStatus(),
                p.getRazlogOdbijanja()
        );
    }

}




