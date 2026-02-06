package com.example.fakultet.dto;

import com.example.fakultet.model.StatusStudenta;
import jakarta.validation.constraints.NotNull;

public class StudentStatusRequest {

    @NotNull
    private StatusStudenta status;

    public StatusStudenta getStatus() {
        return status;
    }

    public void setStatus(StatusStudenta status) {
        this.status = status;
    }
}