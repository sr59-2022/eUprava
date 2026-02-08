package com.example.fakultet.dto;

public class StudentRowDto {
    private Long id;
    private String brojIndeksa;
    private String ime;
    private String prezime;
    private String statusStudenta;
    private boolean zavrsniRadOdbranjen;

    public StudentRowDto(Long id, String brojIndeksa, String ime, String prezime,
                         String statusStudenta, boolean zavrsniRadOdbranjen) {
        this.id = id;
        this.brojIndeksa = brojIndeksa;
        this.ime = ime;
        this.prezime = prezime;
        this.statusStudenta = statusStudenta;
        this.zavrsniRadOdbranjen = zavrsniRadOdbranjen;
    }

    public Long getId() { return id; }
    public String getBrojIndeksa() { return brojIndeksa; }
    public String getIme() { return ime; }
    public String getPrezime() { return prezime; }
    public String getStatusStudenta() { return statusStudenta; }
    public boolean isZavrsniRadOdbranjen() { return zavrsniRadOdbranjen; }
}