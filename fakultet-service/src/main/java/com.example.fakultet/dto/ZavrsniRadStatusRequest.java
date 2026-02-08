package com.example.fakultet.dto;

import jakarta.validation.constraints.NotNull;

public class ZavrsniRadStatusRequest {
    @NotNull
    private Boolean odbranjen;

    public Boolean getOdbranjen() { return odbranjen; }
    public void setOdbranjen(Boolean odbranjen) { this.odbranjen = odbranjen; }
}