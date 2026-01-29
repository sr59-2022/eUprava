package com.example.sluzba.service;

import com.example.sluzba.dto.GradjaninDTO;
import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.model.PotvrdaNezaposlenosti;
import com.example.sluzba.model.RadniStatus;
import com.example.sluzba.repository.GradjaninRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GradjaninService {

    private final GradjaninRepository gradjaninRepository;

    public GradjaninService(GradjaninRepository gradjaninRepository) {
        this.gradjaninRepository = gradjaninRepository;
    }

    @Transactional
    public Gradjanin createGradjanin(Long authGradjaninId, String ime, String prezime, Collection<String> roles) {
        boolean isGradjanin = roles != null && roles.stream()
                .anyMatch(r -> r.equalsIgnoreCase("GRADJANIN") || r.equalsIgnoreCase("ROLE_GRADJANIN"));

        if (!isGradjanin) {
            return null;
        }

        return gradjaninRepository.findByAuthGradjaninId(authGradjaninId)
                .orElseGet(() -> {
                    Gradjanin g = new Gradjanin();
                    g.setAuthGradjaninId(authGradjaninId);
                    g.setIme(ime);
                    g.setPrezime(prezime);
                    g.setRadniStatus(RadniStatus.NEZAPOSLEN);
                    return gradjaninRepository.save(g);
                });
    }

    public Gradjanin updateGradjanin(Long authId, Gradjanin updated) {
        Gradjanin g = gradjaninRepository
                .findByAuthGradjaninId(authId)
                .orElseThrow(() -> new RuntimeException("Gradjanin ne postoji"));

        g.setIme(updated.getIme());
        g.setPrezime(updated.getPrezime());

        if (g.getJmbg() == null) {
            g.setJmbg(updated.getJmbg());
        }

        g.setRadniStatus(updated.getRadniStatus());
        g.setOblastZainteresovanosti(updated.getOblastZainteresovanosti());

        return gradjaninRepository.save(g);
    }

    public GradjaninDTO getProfilGradjanin(Long gradjaninId) {
        Gradjanin g = gradjaninRepository.findById(gradjaninId)
                .orElseThrow(() -> new RuntimeException("Gradjanin ne postoji"));

        PotvrdaNezaposlenosti p = g.getPotvrda();

        return new GradjaninDTO(
                g.getId(),
                g.getIme(),
                g.getPrezime(),
                g.getJmbg(),
                g.getRadniStatus(),
                p != null,
                p != null ? p.getIdPotvrde() : null,
                g.getOblastZainteresovanosti()
        );
    }

}


