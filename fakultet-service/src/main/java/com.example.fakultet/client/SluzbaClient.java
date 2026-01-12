package com.example.fakultet.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class SluzbaClient {

    private final WebClient webClient;

    public SluzbaClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getInfo() {
        return webClient
                .get()
                .uri("http://sluzba-service:8082/api/sluzba/info")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

