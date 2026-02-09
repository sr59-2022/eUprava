package com.example.fakultet.controller;

import com.example.fakultet.client.SluzbaClient;
import com.example.fakultet.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fakultet")
public class FakultetController {

    private final SluzbaClient sluzbaClient;
    private final StudentService studentService;

    public FakultetController(SluzbaClient sluzbaClient,
                              StudentService studentService) {
        this.sluzbaClient = sluzbaClient;
        this.studentService = studentService;
    }

    @GetMapping("/posalji-diplomirane")
    public String posaljiDiplomiraneSluzbi() {

        var diplomirani = studentService.getSviDiplomiraniDto();

        sluzbaClient.primiDiplomiraneStudente(diplomirani);

        return "Poslato " + diplomirani.size() + " diplomiranih studenata službi";
    }


}

