package com.example.sluzba.controller;

import com.example.sluzba.client.FakultetClient;
import com.example.sluzba.dto.DiplomiraniStudentDto;
import com.example.sluzba.dto.KreirajPrijavuDiplomiraniDTO;
import com.example.sluzba.dto.PrikazPrijaveDTO;
import com.example.sluzba.model.Prijava;
import com.example.sluzba.service.DiplomiraniStudentService;
import com.example.sluzba.service.PrijavaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sluzba")
public class SluzbaController {

    private final DiplomiraniStudentService diplomiraniStudentService;
    private final PrijavaService prijavaService;


    public SluzbaController(DiplomiraniStudentService diplomiraniStudentService, PrijavaService prijavaService) {
        this.diplomiraniStudentService = diplomiraniStudentService;
        this.prijavaService = prijavaService;
    }

    @PostMapping("/diplomirani")
    public String primiDiplomirane(@RequestBody List<DiplomiraniStudentDto> studenti) {
        diplomiraniStudentService.sacuvajDiplomirane(studenti);
        return "Primljeno " + studenti.size() + " diplomiranih studenata.";
    }

    @GetMapping("/diplomirani")
    public List<DiplomiraniStudentDto> dobaviSveDiplomirane() {
        return diplomiraniStudentService.sviDiplomirani()
                .stream()
                .map(ds -> new DiplomiraniStudentDto(
                        ds.getIme(),
                        ds.getPrezime(),
                        ds.getBrojIndeksa(),
                        ds.isDostupanZaZaposljavanje()
                ))
                .toList();
    }

    @PostMapping("/prijave/diplomirani")
    public PrikazPrijaveDTO prijaviDiplomiranog(@RequestBody KreirajPrijavuDiplomiraniDTO dto) {
        Prijava p = prijavaService.prijaviSeDiplomirani(dto);
        return prijavaService.prijavaDTO(p);
    }
}
