package com.example.fakultet.controller;

import com.example.fakultet.dto.DiplomiranjeStatusDto;
import com.example.fakultet.dto.DiplomiraniPoGodiniDto;
import com.example.fakultet.dto.OglasDTO;
import com.example.fakultet.dto.StudentRowDto;
import com.example.fakultet.dto.StudentStatusRequest;
import com.example.fakultet.dto.ZavrsniRadStatusRequest;
import com.example.fakultet.model.StatusStudenta;
import com.example.fakultet.model.Student;
import com.example.fakultet.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fakultet")
public class StudentController {

    private final StudentService studentService;

    private static final String SERVICE_TOKEN = "TAJNA123";

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/internal/studenti")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void internalCreateStudent(
            @RequestHeader(value = "X-SERVICE-TOKEN", required = false) String token,
            @RequestBody Map<String, Object> body
    ) {
        if (token == null || !SERVICE_TOKEN.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        Object rawUid = body.get("authUid");
        if (rawUid == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nedostaje authUid");
        }

        Long authUid = (rawUid instanceof Number n) ? n.longValue() : Long.parseLong(rawUid.toString());
        String ime = body.get("ime") != null ? body.get("ime").toString() : null;
        String prezime = body.get("prezime") != null ? body.get("prezime").toString() : null;
        String brojIndeksa = body.get("brojIndeksa") != null ? body.get("brojIndeksa").toString() : null;

        studentService.createIfMissing(authUid, ime, prezime, brojIndeksa);
    }

    @GetMapping("/me")
    public Student me(JwtAuthenticationToken auth) {
        Jwt jwt = auth.getToken();

        Object raw = jwt.getClaims().get("uid");
        if (raw == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token nema claim 'uid'");

        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());
        return studentService.getByAuthUid(uid);
    }

    @GetMapping("/me/diplomiranje-status")
    public DiplomiranjeStatusDto diplomiranjeStatus(JwtAuthenticationToken auth) {
        Jwt jwt = auth.getToken();

        Object raw = jwt.getClaims().get("uid");
        if (raw == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token nema claim 'uid'");

        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());
        return studentService.proveraDiplomiranja(uid);
    }


    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/oglasi-za-diplomirane")
    public List<OglasDTO> oglasiZaDiplomirane(
            JwtAuthenticationToken auth,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        Jwt jwt = auth.getToken();

        Object raw = jwt.getClaims().get("uid");
        if (raw == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token nema claim 'uid'");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nedostaje Authorization header");
        }

        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());

        // StudentService proverava diplomiranje + poziva Sluzbu
        return studentService.oglasiZaDiplomirane(uid, authorizationHeader);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/me/diplomiraj")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void diplomiraj(JwtAuthenticationToken auth) {
        Jwt jwt = auth.getToken();

        Object raw = jwt.getClaims().get("uid");
        if (raw == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token nema claim 'uid'");

        Long uid = (raw instanceof Number n) ? n.longValue() : Long.parseLong(raw.toString());
        studentService.diplomirajAkoIspunjava(uid);
    }

    @GetMapping("/izvestaji/diplomirani-po-godini")
    public List<DiplomiraniPoGodiniDto> diplomiraniPoGodini() {
        return studentService.izvestajDiplomiraniPoGodini();
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PatchMapping("/studenti/{studentId}/zavrsni-rad")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postaviZavrsniRad(
            @PathVariable Long studentId,
            @Valid @RequestBody ZavrsniRadStatusRequest req
    ) {
        studentService.postaviZavrsniRad(studentId, req.getOdbranjen());
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @GetMapping("/studenti")
    public Page<StudentRowDto> studenti(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return studentService.listajStudente(q, page, size);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PatchMapping("/studenti/{studentId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postaviStatusStudenta(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentStatusRequest req
    ) {
        StatusStudenta status = req.getStatus();
        studentService.profesorPostaviStatus(studentId, status);
    }

    @PostMapping("/oglasi/{oglasId}/prijavi")
    public void prijaviSe(@AuthenticationPrincipal Jwt jwt,
                          @PathVariable Long oglasId,
                          @RequestHeader("Authorization") String authorizationHeader) {

        Long authUid = ((Number) jwt.getClaims().get("uid")).longValue();
        studentService.prijaviDiplomiraniNaOglas(authUid, oglasId, authorizationHeader);
    }
}