package com.example.auth.dto;

import java.util.Set;

public class LoginResponse {
    private String token;

    private Set<String> uloge;

    public LoginResponse(String token, Set<String> uloge) {
        this.token = token;

        this.uloge = uloge;
    }

    public String getToken() { return token; }

    public Set<String> getUloge() { return uloge; }
}
