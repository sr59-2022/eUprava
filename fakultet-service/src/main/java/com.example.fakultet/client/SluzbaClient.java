package com.example.fakultet.client;

import com.example.fakultet.dto.KreirajPrijavuDiplomiraniDto;
import com.example.fakultet.dto.OglasDTO;
import com.example.fakultet.dto.StudentRowDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

@Service
public class SluzbaClient {

    private final WebClient webClient;

    public SluzbaClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://sluzba-service:8082")
                .build();
    }


    public void primiDiplomiraneStudente(List<StudentRowDto> studenti) {
        webClient.post()
                .uri("/api/sluzba/diplomirani")
                .bodyValue(studenti)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


    public List<OglasDTO> getOglasi(String authorizationHeader) {
        return webClient.get()
                .uri("/api/oglasi")
                .header("Authorization", authorizationHeader) // "Bearer <token>"
                .retrieve()
                .bodyToFlux(OglasDTO.class)
                .collectList()
                .block();
    }

    public void posaljiPrijavuDiplomiranog(KreirajPrijavuDiplomiraniDto dto, String authorizationHeader) {
        webClient.post()
                .uri("/api/sluzba/prijave/diplomirani")
                .header("Authorization", authorizationHeader)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}