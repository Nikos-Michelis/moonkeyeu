package com.moonkeyeu.etl.api.service.fetch;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class ThrottleService {
    private final WebClient webClient;
    @Value("${application.api.the-space-devs.version}")
    private String version;
    public ThrottleService(
            WebClient.Builder webClientBuilder,
            @Value("${application.api.the-space-devs.url}") String baseUrl
    ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<Long> getThrottleDelay() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(version, "api-throttle")
                        .path("/")
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> {
                    int requestLimit = response.path("your_request_limit").asInt();
                    int currentUse = response.path("current_use").asInt();
                    long nextUseSeconds = response.path("next_use_secs").asLong();
                         if (currentUse >= requestLimit && nextUseSeconds > 0) {
                             log.info("Request limit reached. Waiting for " + nextUseSeconds + " seconds...");
                             return nextUseSeconds;
                         } else if (currentUse <= requestLimit && nextUseSeconds < 0) {
                             log.info("Request limit reached. Waiting for " + (nextUseSeconds) + " seconds...");
                             return -(nextUseSeconds);
                         }
                    return 0L;
                });
    }
}