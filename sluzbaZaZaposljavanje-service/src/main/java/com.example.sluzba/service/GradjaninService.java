package com.example.sluzba.service;

import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.model.StatusNezaposlenosti;
import com.example.sluzba.repository.GradjaninRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
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
                    g.setStatusNezaposlenosti(StatusNezaposlenosti.NEZAPOSLEN);
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

        g.setStatusNezaposlenosti(updated.getStatusNezaposlenosti());

        return gradjaninRepository.save(g);
    }

}


