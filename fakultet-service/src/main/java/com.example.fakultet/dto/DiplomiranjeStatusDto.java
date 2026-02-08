package com.example.fakultet.dto;

public class DiplomiranjeStatusDto {

    private String status;
    private int ukupnoEspb;
    private int potrebnoEspb;
    private boolean zavrsniRadOdbranjen;

    public DiplomiranjeStatusDto(String status,
                                 int ukupnoEspb,
                                 int potrebnoEspb,
                                 boolean zavrsniRadOdbranjen) {
        this.status = status;
        this.ukupnoEspb = ukupnoEspb;
        this.potrebnoEspb = potrebnoEspb;
        this.zavrsniRadOdbranjen = zavrsniRadOdbranjen;
    }

    public String getStatus() {
        return status;
    }

    public int getUkupnoEspb() {
        return ukupnoEspb;
    }

    public int getPotrebnoEspb() {
        return potrebnoEspb;
    }

    public boolean isZavrsniRadOdbranjen() {
        return zavrsniRadOdbranjen;
    }
}
