package com.example.sluzba.service;

import com.example.sluzba.dto.PrikazPrijaveDTO;
import com.example.sluzba.dto.PrikazPrijavePoslodavacDTO;
import com.example.sluzba.model.*;
import com.example.sluzba.repository.GradjaninRepository;
import com.example.sluzba.repository.OglasRepository;
import com.example.sluzba.repository.PoslodavacRepository;
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
    private final PoslodavacRepository poslodavacRepository;

    public PrijavaService(PrijavaRepository prijavaRepository,
                          GradjaninRepository gradjaninRepository,
                          OglasRepository oglasRepository,
                          PoslodavacRepository poslodavacRepository) {
        this.prijavaRepository = prijavaRepository;
        this.gradjaninRepository = gradjaninRepository;
        this.oglasRepository = oglasRepository;
        this.poslodavacRepository = poslodavacRepository;
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


    public List<PrikazPrijavePoslodavacDTO> prijaveZaPoslodavca(Long authPoslodavacId) {
        Poslodavac p = poslodavacRepository.findByAuthPoslodavacId(authPoslodavacId)
                .orElseThrow(() -> new RuntimeException("Poslodavac ne postoji"));

        return prijavaRepository.findByOglas_Poslodavac_Id(p.getId())
                .stream()
                .map(this::mapToPoslodavacDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PrikazPrijaveDTO prihvatiPrijavu(Long prijavaId) {
        Prijava p = prijavaRepository.findById(prijavaId)
                .orElseThrow(() -> new RuntimeException("Prijava ne postoji"));

        p.setStatus(StatusPrijave.PRIHVACENA);
        return mapToDTO(p);
    }


    @Transactional
    public PrikazPrijaveDTO odbijPrijavu(Long prijavaId, String razlog) {
        Prijava p = prijavaRepository.findById(prijavaId)
                .orElseThrow(() -> new RuntimeException("Prijava ne postoji"));

        p.setStatus(StatusPrijave.ODBIJENA);
        p.setRazlogOdbijanja(razlog);
        return mapToDTO(p);
    }

    private PrikazPrijaveDTO mapToDTO(Prijava p) {
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

    private PrikazPrijavePoslodavacDTO mapToPoslodavacDTO(Prijava p) {
        Gradjanin g = p.getGradjanin();
        Oglas o = p.getOglas();

        return new PrikazPrijavePoslodavacDTO(
                p.getIdPrijave(),
                o.getIdOglasa(),
                o.getNazivPozicije(),
                o.getNazivKompanije(),
                p.getDatumPrijave(),
                p.getStatus(),
                p.getRazlogOdbijanja(),
                g.getIme(),
                g.getPrezime(),
                g.getOblastZainteresovanosti(),
                g.getRadniStatus() != null ? g.getRadniStatus().name() : null
        );
    }


}


