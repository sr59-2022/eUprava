package com.example.sluzba.controller;

import com.example.sluzba.client.FakultetClient;
import com.example.sluzba.dto.DiplomiraniStudentDto;
import com.example.sluzba.service.DiplomiraniStudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sluzba")
public class SluzbaController {

    private final DiplomiraniStudentService diplomiraniStudentService;

    public SluzbaController(DiplomiraniStudentService diplomiraniStudentService) {
        this.diplomiraniStudentService = diplomiraniStudentService;
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
}
