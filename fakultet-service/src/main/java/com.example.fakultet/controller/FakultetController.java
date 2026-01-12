package com.example.fakultet.controller;

import com.example.fakultet.client.SluzbaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fakultet")
public class FakultetController {

    private final SluzbaClient sluzbaClient;

    public FakultetController(SluzbaClient sluzbaClient) {
        this.sluzbaClient = sluzbaClient;
    }

    @GetMapping("/provera-sluzbe")
    public String proveriSluzbu() {
        String odgovor = sluzbaClient.getInfo();
        return "Odgovor iz službe: " + odgovor;
    }

    @GetMapping("/info")
    public String info() {
        return "Fakultet OK";
    }

}

