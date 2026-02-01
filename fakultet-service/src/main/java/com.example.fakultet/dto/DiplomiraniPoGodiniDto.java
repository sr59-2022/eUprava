package com.example.fakultet.dto;

public class DiplomiraniPoGodiniDto {

    private int godina;
    private long brojDiplomiranih;

    public DiplomiraniPoGodiniDto(int godina, long brojDiplomiranih) {
        this.godina = godina;
        this.brojDiplomiranih = brojDiplomiranih;
    }

    public int getGodina() {
        return godina;
    }

    public long getBrojDiplomiranih() {
        return brojDiplomiranih;
    }
}
