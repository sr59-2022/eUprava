package com.example.sluzba.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FakultetClient {

    private final WebClient webClient;

    public FakultetClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getInfo() {

        return webClient.get()
                .uri("http://fakultet-service:8081/api/fakultet/info")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
