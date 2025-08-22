package com.moonkeyeu.core.api.launch.services.impl.apod;

import com.moonkeyeu.core.api.launch.dto.NasaApodDTO;
import com.moonkeyeu.core.api.launch.services.NasaApodClientService;
import com.moonkeyeu.core.api.settings.exceptions.NasaApodFetchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
public class NasaApodClientServiceImpl implements NasaApodClientService {
    private final WebClient webClient;

    @Value("${application.api.nasa-apod.key}")
    private String apiKey;

    @Autowired
    public NasaApodClientServiceImpl(
            WebClient.Builder webClientBuilder,
            @Value("${application.api.nasa-apod.url}") String url
    ) {
        this.webClient = webClientBuilder
                .baseUrl(url)
                .build();
    }

    @Override
    public NasaApodDTO fetchNasaAstronomyPictureOfTheDay() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", apiKey).build())
                .retrieve()
                .onStatus(
                        this::isErrorStatus,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(body -> new NasaApodFetchException(
                                        "Error fetching nasa apod data: " + body, clientResponse.statusCode())
                                )
                )
                .bodyToMono(NasaApodDTO.class)
                .block();
    }
    private boolean isErrorStatus(HttpStatusCode response) {
        return response.is4xxClientError() || response.is5xxServerError();
    }

}
