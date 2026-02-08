package com.example.fakultet.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OcenaUpisDto {

    @NotNull
    private Long studentId;

    @NotNull
    private Long ispitId;

    @Min(5)
    @Max(10)
    private int vrednost;

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getIspitId() { return ispitId; }
    public void setIspitId(Long ispitId) { this.ispitId = ispitId; }

    public int getVrednost() { return vrednost; }
    public void setVrednost(int vrednost) { this.vrednost = vrednost; }
}
