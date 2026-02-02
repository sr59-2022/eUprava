package com.example.fakultet.dto;

public class PrijavljeniStudentDto {
    private Long studentId;
    private String brojIndeksa;
    private String ime;
    private String prezime;
    private String status;
    private Integer ocena;

    public PrijavljeniStudentDto(Long studentId, String brojIndeksa, String ime, String prezime, String status, Integer ocena) {
        this.studentId = studentId;
        this.brojIndeksa = brojIndeksa;
        this.ime = ime;
        this.prezime = prezime;
        this.status = status;
        this.ocena = ocena;
    }

    public Long getStudentId() { return studentId; }
    public String getBrojIndeksa() { return brojIndeksa; }
    public String getIme() { return ime; }
    public String getPrezime() { return prezime; }
    public String getStatus() { return status; }
    public Integer getOcena() { return ocena; }
}
