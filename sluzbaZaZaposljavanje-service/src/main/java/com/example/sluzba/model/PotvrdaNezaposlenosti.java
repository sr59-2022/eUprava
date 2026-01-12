package com.example.sluzba.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class PotvrdaNezaposlenosti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPotvrde;

    private LocalDate datumIzdavanja;
    private LocalDate validnaDo;

    @OneToOne
    @JoinColumn(name = "gradjanin_id", unique = true)
    private Gradjanin gradjanin;

    public PotvrdaNezaposlenosti() {}
    public PotvrdaNezaposlenosti(Long idPotvrde, LocalDate datumIzdavanja, LocalDate validnaDo) {
        this.idPotvrde = idPotvrde;
        this.datumIzdavanja = datumIzdavanja;
        this.validnaDo = validnaDo;
    }


    public Long getIdPotvrde() { return idPotvrde; }
    public void setIdPotvrde(Long idPotvrde) { this.idPotvrde = idPotvrde; }
    public LocalDate getDatumIzdavanja() { return datumIzdavanja; }
    public void setDatumIzdavanja(LocalDate datumIzdavanja) { this.datumIzdavanja = datumIzdavanja; }
    public LocalDate getValidnaDo() { return validnaDo; }
    public void setValidnaDo(LocalDate validnaDo) { this.validnaDo = validnaDo; }
    public Gradjanin getGradjanin() { return gradjanin; }
    public void setGradjanin(Gradjanin gradjanin) { this.gradjanin = gradjanin; }
}
