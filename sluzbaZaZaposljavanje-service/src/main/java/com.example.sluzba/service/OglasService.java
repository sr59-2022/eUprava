package com.example.sluzba.service;

import com.example.sluzba.dto.OglasDTO;
import com.example.sluzba.dto.OglasRequest;
import com.example.sluzba.model.*;
import com.example.sluzba.repository.GradjaninRepository;
import com.example.sluzba.repository.OglasRepository;
import com.example.sluzba.repository.PoslodavacRepository;
import com.example.sluzba.search.OglasPretraga;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OglasService {

    private final OglasRepository oglasRepo;
    private final PoslodavacRepository poslodavacRepo;
    private final GradjaninRepository gradjaninRepo;

    public OglasService(OglasRepository oglasRepo, PoslodavacRepository poslodavacRepo, GradjaninRepository gradjaninRepo) {
        this.oglasRepo = oglasRepo;
        this.poslodavacRepo = poslodavacRepo;
        this.gradjaninRepo = gradjaninRepo;
    }

    public Oglas dodajOglas(OglasRequest req, Long authPoslodavacId) {
        Poslodavac poslodavac = poslodavacRepo.findByAuthPoslodavacId(authPoslodavacId)
                .orElseGet(() -> {
                    Poslodavac novi = new Poslodavac();
                    novi.setAuthPoslodavacId(authPoslodavacId);
                    return poslodavacRepo.save(novi);
                });


        Oglas o = new Oglas();
        o.setNazivPozicije(req.nazivPozicije);
        o.setOpis(req.opis);
        o.setDatumObjave(LocalDate.now());
        o.setRokPrijave(req.rokPrijave);
        o.setNazivKompanije(req.nazivKompanije);
        o.setTipOglasa(req.tipOglasa);
        o.setPoslodavac(poslodavac);
        o.setOblast(req.oblast);


        return oglasRepo.save(o);
    }

    public List<Oglas> getAllOglasi() {
        return oglasRepo.findAll();
    }

    public List<Oglas> pretragaOglasa(
            String nazivPozicije,
            TipOglasa tipOglasa) {

        return oglasRepo.findAll(
                OglasPretraga.aktivniOglasi(nazivPozicije, tipOglasa)
        );
    }


    public List<Oglas> generisiPreporuke(Long authGradjaninId) {
        Gradjanin g = gradjaninRepo.findByAuthGradjaninId(authGradjaninId)
                .orElseThrow(() -> new RuntimeException("Gradjanin ne postoji"));

        List<Oglas> sviOglasi = oglasRepo.findAll();

        return sviOglasi.stream()
                .filter(o -> {
                    // filtriranje po oblasti zainteresovanosti
                    if (g.getOblastZainteresovanosti() != null && o.getOblast() != null) {
                        String oblastKorisnika = g.getOblastZainteresovanosti().toLowerCase();
                        String[] oblastiOglasa = o.getOblast().toLowerCase().split(",");
                        boolean poklapanje = Arrays.stream(oblastiOglasa)
                                .anyMatch(a -> a.trim().equals(oblastKorisnika));
                        if (!poklapanje) return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    public OglasDTO oglasDTO(Oglas o) {
        return new OglasDTO(
                o.getIdOglasa(),
                o.getNazivPozicije(),
                o.getNazivKompanije(),
                o.getOblast(),
                o.getOpis(),
                o.getDatumObjave(),
                o.getRokPrijave(),
                o.getTipOglasa() != null ? o.getTipOglasa().name() : null
        );
    }

}

