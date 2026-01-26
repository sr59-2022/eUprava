package com.example.sluzba.service;

import com.example.sluzba.dto.GradjaninDTO;
import com.example.sluzba.model.Gradjanin;
import com.example.sluzba.model.PotvrdaNezaposlenosti;
import com.example.sluzba.model.RadniStatus;
import com.example.sluzba.repository.GradjaninRepository;
import com.example.sluzba.repository.PotvrdaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PotvrdaService {

    private final GradjaninRepository gradjaninRepo;
    private final PotvrdaRepository potvrdaRepo;

    public PotvrdaService(GradjaninRepository gradjaninRepo,
                          PotvrdaRepository potvrdaRepo) {
        this.gradjaninRepo = gradjaninRepo;
        this.potvrdaRepo = potvrdaRepo;
    }

    // ADMIN izdaje potvrdu
    public PotvrdaNezaposlenosti izdajPotvrdu(Long gradjaninId) {

        Gradjanin gradjanin = gradjaninRepo.findById(gradjaninId)
                .orElseThrow(() -> new RuntimeException("Građanin ne postoji"));

        if (gradjanin.getRadniStatus() != RadniStatus.NEZAPOSLEN) {
            throw new IllegalStateException(
                    "Potvrda se može izdati samo nezaposlenom licu."
            );
        }

        if (potvrdaRepo.existsByGradjanin(gradjanin)) {
            throw new IllegalStateException("Građanin već ima potvrdu.");
        }

        PotvrdaNezaposlenosti potvrda = new PotvrdaNezaposlenosti();
        potvrda.setGradjanin(gradjanin);
        potvrda.setDatumIzdavanja(LocalDate.now());
        potvrda.setValidnaDo(LocalDate.now().plusMonths(1));

        gradjanin.setZahtevZatrazen(false);
        gradjaninRepo.save(gradjanin);

        return potvrdaRepo.save(potvrda);
    }

    // GRAĐANIN šalje zahtev
    public void zatraziPotvrdu(Long gradjaninId) {
        Gradjanin g = gradjaninRepo.findById(gradjaninId)
                .orElseThrow(() -> new RuntimeException("Gradjanin nije pronadjen"));
        g.setZahtevZatrazen(true);
        gradjaninRepo.save(g);
    }


    public List<GradjaninDTO> getGradjaniSaPotvrdama() {
        List<Gradjanin> gradjani = gradjaninRepo.findAll().stream()
                .filter(g -> g.isZahtevZatrazen() || g.getPotvrda() != null)
                .collect(Collectors.toList());

        return gradjani.stream()
                .map(g -> new GradjaninDTO(
                        g.getId(),
                        g.getIme(),
                        g.getPrezime(),
                        g.getRadniStatus(),
                        g.getPotvrda() != null // imaPotvrdu
                ))
                .collect(Collectors.toList());
    }
}

