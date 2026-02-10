package com.example.fakultet.controller;

import com.example.fakultet.client.SluzbaClient;
import com.example.fakultet.dto.OglasDTO;
import com.example.fakultet.model.Student;
import com.example.fakultet.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/fakultet")
public class OglasController {

    private final StudentService studentService;
    private final SluzbaClient sluzbaClient;

    public OglasController(StudentService studentService, SluzbaClient sluzbaClient) {
        this.studentService = studentService;
        this.sluzbaClient = sluzbaClient;
    }

    @GetMapping("/oglasi")
    public List<OglasDTO> oglasi(JwtAuthenticationToken auth,
                                 @RequestHeader("Authorization") String authorization) {

        Long uid = ((Number) auth.getToken().getClaims().get("uid")).longValue();
        Student s = studentService.getByAuthUid(uid);

        if (!studentService.jeDiplomirao(s)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Samo diplomirani studenti vide oglase");
        }

        return sluzbaClient.getOglasi(authorization);
    }
}