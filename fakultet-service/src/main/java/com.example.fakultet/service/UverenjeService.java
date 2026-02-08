package com.example.fakultet.service;

import com.example.fakultet.dto.UverenjeDto;
import com.example.fakultet.model.Student;
import com.example.fakultet.model.TipUverenja;
import com.example.fakultet.model.Uverenje;
import com.example.fakultet.repository.UverenjeRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UverenjeService {

    private final UverenjeRepository uverenjeRepository;
    private final StudentService studentService;

    public UverenjeService(UverenjeRepository uverenjeRepository, StudentService studentService) {
        this.uverenjeRepository = uverenjeRepository;
        this.studentService = studentService;
    }

    public UverenjeDto izdajUverenje(Long authUid, TipUverenja tip) {
        Student s = studentService.getByAuthUid(authUid);

        Uverenje u = new Uverenje();
        u.setStudent(s);
        u.setDatumIzdavanja(LocalDate.now());
        u.setTip(tip);
        u.setBrojDokumenta(generisiBrojDokumentaUnique(s.getId()));

        Uverenje sacuvano = uverenjeRepository.save(u);

        return new UverenjeDto(
                sacuvano.getId(),
                sacuvano.getBrojDokumenta(),
                sacuvano.getDatumIzdavanja(),
                sacuvano.getTip().name()
        );
    }

    public List<UverenjeDto> mojaUverenja(Long authUid) {
        Student s = studentService.getByAuthUid(authUid);

        return uverenjeRepository.findByStudentIdOrderByDatumIzdavanjaDesc(s.getId())
                .stream()
                .map(u -> new UverenjeDto(u.getId(), u.getBrojDokumenta(), u.getDatumIzdavanja(), u.getTip().name()))
                .toList();
    }


    public byte[] generisiPdfZaUverenje(Long authUid, Long uverenjeId) {
        Student s = studentService.getByAuthUid(authUid);

        Uverenje u = uverenjeRepository.findById(uverenjeId)
                .orElseThrow(() -> new RuntimeException("Uverenje nije pronađeno: id=" + uverenjeId));


        if (!u.getStudent().getId().equals(s.getId())) {
            throw new RuntimeException("Nemate pravo da preuzmete ovo uverenje");
        }

        try (PDDocument doc = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                float x = 70;
                float y = 770;


                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.newLineAtOffset(x, y);
                cs.showText("UVERENJE");
                cs.endText();

                y -= 40;


                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(x, y);

                cs.showText("Broj dokumenta: " + u.getBrojDokumenta());
                cs.newLineAtOffset(0, -18);
                cs.showText("Datum izdavanja: " + u.getDatumIzdavanja());
                cs.newLineAtOffset(0, -18);
                cs.showText("Tip: " + u.getTip().name());
                cs.newLineAtOffset(0, -30);


                cs.showText("Potvrdjuje se da je " + s.getIme() + " " + s.getPrezime()
                        + ", broj indeksa " + s.getBrojIndeksa() + ", student fakulteta.");
                cs.newLineAtOffset(0, -18);
                cs.showText("Status studenta: " + s.getStatusStudenta().name());
                cs.newLineAtOffset(0, -40);

                cs.showText("Ovo uverenje je izdato na licni zahtev studenta.");
                cs.newLineAtOffset(0, -80);

                cs.showText("__________________________");
                cs.newLineAtOffset(0, -14);
                cs.showText("Studentska sluzba");

                cs.endText();
            }

            doc.save(baos);
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Greska pri generisanju PDF-a", e);
        }
    }

    private String generisiBrojDokumentaUnique(Long studentId) {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        for (int i = 0; i < 10; i++) {
            int rnd = ThreadLocalRandom.current().nextInt(1000, 10000);
            String broj = "UV-" + date + "-" + studentId + "-" + rnd;

            if (!uverenjeRepository.existsByBrojDokumenta(broj)) {
                return broj;
            }
        }
        throw new RuntimeException("Ne mogu da generišem jedinstven broj dokumenta");
    }
}
