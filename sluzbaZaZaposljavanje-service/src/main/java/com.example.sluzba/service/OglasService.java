package com.example.sluzba.service;

import com.example.sluzba.dto.OglasRequest;
import com.example.sluzba.model.Oglas;
import com.example.sluzba.model.Poslodavac;
import com.example.sluzba.repository.OglasRepository;
import com.example.sluzba.repository.PoslodavacRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OglasService {

    private final OglasRepository oglasRepo;
    private final PoslodavacRepository poslodavacRepo;

    public OglasService(OglasRepository oglasRepo, PoslodavacRepository poslodavacRepo) {
        this.oglasRepo = oglasRepo;
        this.poslodavacRepo = poslodavacRepo;
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

        return oglasRepo.save(o);
    }

}
