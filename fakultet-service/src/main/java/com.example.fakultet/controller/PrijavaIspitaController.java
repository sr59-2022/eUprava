package com.example.fakultet.controller;

import com.example.fakultet.dto.IspitOpcijaDto;
import com.example.fakultet.dto.PrijavaIspitaDto;
import com.example.fakultet.dto.PrijavljeniStudentDto;
import com.example.fakultet.model.Student;
import com.example.fakultet.repository.IspitRepository;
import com.example.fakultet.service.PrijavaIspitaService;
import com.example.fakultet.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ispiti")
public class PrijavaIspitaController {

    private final StudentService studentService;
    private final PrijavaIspitaService prijavaService;


    private final IspitRepository ispitRepo;

    public PrijavaIspitaController(
            StudentService studentService,
            PrijavaIspitaService prijavaService,
            IspitRepository ispitRepo
    ) {
        this.studentService = studentService;
        this.prijavaService = prijavaService;
        this.ispitRepo = ispitRepo;
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

    @GetMapping("/dostupni")
    @PreAuthorize("hasRole('STUDENT')")
    public List<IspitOpcijaDto> dostupni(JwtAuthenticationToken auth) {
        Student s = getCurrentStudent(auth);
        return prijavaService.dostupniIspiti(s);
    }


    @GetMapping("/lista")
    @PreAuthorize("hasRole('PROFESOR') or hasRole('ADMIN')")
    public List<IspitOpcijaDto> listaIspitaZaProfesora() {
        return ispitRepo.findAll()
                .stream()
                // sortiraj da najskoriji bude prvi
                .sorted((a, b) -> b.getDatumOdrzavanja().compareTo(a.getDatumOdrzavanja()))
                .map(i -> new IspitOpcijaDto(
                        i.getId(),
                        i.getPredmet().getNaziv(),
                        i.getRok().getNaziv(),
                        i.getDatumOdrzavanja(),
                        i.getPrijavaDo()
                ))
                .toList();
    }


    @GetMapping("/{ispitId}/prijave")
    @PreAuthorize("hasRole('PROFESOR') or hasRole('ADMIN')")
    public List<PrijavljeniStudentDto> prijaveZaIspit(@PathVariable Long ispitId) {
        return prijavaService.prijavljeniZaIspit(ispitId);
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
