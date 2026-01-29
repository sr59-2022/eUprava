package com.example.sluzba.service;

import com.example.sluzba.dto.ObavestenjeDTO;
import com.example.sluzba.model.Obavestenje;
import com.example.sluzba.model.Poslodavac;
import com.example.sluzba.repository.ObavestenjeRepository;
import com.example.sluzba.repository.PoslodavacRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObavestenjeService {

    private final ObavestenjeRepository obavestenjeRepository;
    private final PoslodavacRepository poslodavacRepository;

    public ObavestenjeService(ObavestenjeRepository obavestenjeRepository,
                              PoslodavacRepository poslodavacRepository) {
        this.obavestenjeRepository = obavestenjeRepository;
        this.poslodavacRepository = poslodavacRepository;
    }

    public List<ObavestenjeDTO> getObavestenjaZaPoslodavca(Long authPoslodavacId) {

        Poslodavac p = poslodavacRepository.findByAuthPoslodavacId(authPoslodavacId)
                .orElseThrow(() -> new RuntimeException("Poslodavac ne postoji"));

        return obavestenjeRepository
                .findByPoslodavacIdAndProcitanoFalseOrderByDatumDesc(p.getId())
                .stream()
                .map(o -> new ObavestenjeDTO(
                        o.getId(),
                        o.getPoruka(),
                        o.isProcitano(),
                        o.getDatum()
                ))
                .collect(Collectors.toList());
    }



    @Transactional
    public void oznaciKaoProcitano(Long id) {
        Obavestenje o = obavestenjeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obaveštenje ne postoji"));

        o.setProcitano(true);
    }

    public long brojNeprocitanihObavestenja(Long authPoslodavacId) {
        Poslodavac p = poslodavacRepository.findByAuthPoslodavacId(authPoslodavacId)
                .orElseThrow(() -> new RuntimeException("Poslodavac ne postoji"));

        return obavestenjeRepository.countByPoslodavacIdAndProcitanoFalse(p.getId());
    }

}
