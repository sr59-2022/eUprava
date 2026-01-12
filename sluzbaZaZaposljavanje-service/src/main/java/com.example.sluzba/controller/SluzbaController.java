package com.example.sluzba.controller;

import com.example.sluzba.client.FakultetClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sluzba")
public class SluzbaController {

    private final FakultetClient fakultetClient;

    public SluzbaController(FakultetClient fakultetClient) {
        this.fakultetClient = fakultetClient;
    }

    @GetMapping("/status")
    public String status() {
        return "Sluzba za zapošljavanje servis radi";
    }


    @GetMapping("/info")
    public String info() {
        String fakultetInfo = fakultetClient.getInfo();
        return "Sluzba OK, pozvao fakultet: " + fakultetInfo;
    }
}
