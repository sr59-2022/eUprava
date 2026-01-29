package com.example.fakultet.controller;

import com.example.fakultet.model.Student;
import com.example.fakultet.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/fakultet")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/me")
    public Student me(JwtAuthenticationToken auth) {
        Jwt jwt = auth.getToken();

        Object raw = jwt.getClaims().get("uid");   // <-- umesto getClaim()
        if (raw == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token nema claim 'uid'");
        }

        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());

        Student s = studentService.getByAuthUid(uid);
        if (s == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Student za uid=" + uid + " ne postoji u fakultet servisu");
        }
        return s;
    }
}
