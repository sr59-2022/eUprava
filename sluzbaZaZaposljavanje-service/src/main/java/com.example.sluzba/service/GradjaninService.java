package com.example.sluzba.service;

import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.model.StatusNezaposlenosti;
import com.example.sluzba.repository.GradjaninRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GradjaninService {

    private final GradjaninRepository gradjaninRepository;

    public GradjaninService(GradjaninRepository gradjaninRepository) {
        this.gradjaninRepository = gradjaninRepository;
    }

    @Transactional
    public Gradjanin createGradjanin(Long authGradjaninId, String ime, String prezime) {
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
}


