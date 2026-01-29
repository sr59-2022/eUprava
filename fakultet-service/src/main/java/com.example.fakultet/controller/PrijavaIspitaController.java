package com.example.fakultet.controller;

import com.example.fakultet.dto.PrijavaIspitaDto;
import com.example.fakultet.model.Student;
import com.example.fakultet.service.PrijavaIspitaService;
import com.example.fakultet.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ispiti")
public class PrijavaIspitaController {

    private final StudentService studentService;
    private final PrijavaIspitaService prijavaService;

    public PrijavaIspitaController(StudentService studentService, PrijavaIspitaService prijavaService) {
        this.studentService = studentService;
        this.prijavaService = prijavaService;
    }

    @PostMapping("/{ispitId}/prijava")
    @PreAuthorize("hasRole('STUDENT')")
    public PrijavaIspitaDto prijavi(@PathVariable Long ispitId, JwtAuthenticationToken auth) {
        Student s = getCurrentStudent(auth);
        return prijavaService.prijavi(s, ispitId);
    }

    @PostMapping("/{ispitId}/otkazi")
    @PreAuthorize("hasRole('STUDENT')")
    public PrijavaIspitaDto otkazi(@PathVariable Long ispitId, JwtAuthenticationToken auth) {
        Student s = getCurrentStudent(auth);
        return prijavaService.otkazi(s, ispitId);
    }

    @GetMapping("/moje-prijave")
    @PreAuthorize("hasRole('STUDENT')")
    public List<PrijavaIspitaDto> mojePrijave(JwtAuthenticationToken auth) {
        Student s = getCurrentStudent(auth);
        return prijavaService.mojePrijave(s);
    }

    private Student getCurrentStudent(JwtAuthenticationToken auth) {
        Object raw = auth.getToken().getClaims().get("uid");
        if (raw == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token nema claim 'uid'");
        }
        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());
        return studentService.getByAuthUid(uid);
    }
}
