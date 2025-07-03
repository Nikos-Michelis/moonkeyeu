package com.moonkeyeu.etl.api.service.fetch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moonkeyeu.etl.api.settings.exceptions.RateLimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class FetchDataService {

    private final WebClient webClient;
    private final ThrottleService throttleService;
    private final ObjectMapper objectMapper;

    @Autowired
    public FetchDataService(WebClient webClient, ThrottleService throttleService, @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.throttleService = throttleService;
        this.objectMapper = objectMapper;
    }

    public Mono<Void> fetchData(String url, String fileName) {
        List<JsonNode> allResults = new ArrayList<>();
        return throttleService.getThrottleDelay()
                .flatMap(delay -> {
                    if (delay > 0) {
                        log.warn("Throttle delay detected: Waiting for " + delay + " seconds.");
                        return Mono.delay(Duration.ofSeconds(delay));
                    }
                    return Mono.empty();
                })
                .then(fetchNextPage(url, allResults))
                .expand(response -> hasNextPage(response) ? fetchThrottle(getNextPageUrl(response), allResults) : Mono.empty())
                .doOnTerminate(() -> {
                    saveJsonData(allResults, fileName);
                    log.info("Data fetch process completed.");
                }).then()
                .doOnSubscribe(subscription -> {
                    log.info("Fetching process started.");
                })
                .doOnSuccess(done -> {
                    log.info("Fetching process successfully completed.");
                })
                .doOnError(error -> {
                    log.error("An error occurred during fetching: " + error.getMessage());
                });
    }

    private Mono<JsonNode> fetchThrottle(String url, List<JsonNode> allResults) {
        return throttleService.getThrottleDelay()
                .flatMap(delay -> {
                    if (delay > 0) {
                        log.warn("Throttling applied. Waiting for " + delay + " seconds.");
                        return Mono.delay(Duration.ofSeconds(delay)).then(fetchNextPage(url, allResults));
                    } else {
                        return fetchNextPage(url, allResults);
                    }
                });
    }

    private Mono<JsonNode> fetchNextPage(String url, List<JsonNode> allResults) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.value() == 429, clientResponse -> {
                    log.warn("Rate limit hit (429). Fetching delay...");
                    return throttleService.getThrottleDelay()
                            .flatMap(delay -> {
                                if (delay > 0) {
                                    return Mono.delay(Duration.ofSeconds(delay))
                                            .then(Mono.error(new RateLimitExceededException("Rate limit hit. Waiting for " + delay + " seconds...")));
                                }
                                return Mono.empty();
                            });
                })
                .bodyToMono(JsonNode.class)
                .retryWhen(Retry.backoff(Long.MAX_VALUE, Duration.ofSeconds(1))
                        .maxBackoff(Duration.ofMinutes(3))
                        .filter(throwable ->
                                        throwable instanceof RateLimitExceededException ||
                                        throwable instanceof WebClientResponseException ||
                                        throwable instanceof TimeoutException ||
                                        throwable instanceof IOException)
                )
                .doOnSubscribe(subscription -> {
                    log.info("Request started, awaiting long response...");
                })
                .doOnError(error -> log.error("Error fetching data: " + error.getMessage()))
                .doOnNext(response -> {
                    if (response != null) {
                        allResults.add(response);
                    }
                });
    }

    private void saveJsonData(List<JsonNode> allResults, String fileName) {
        try {
            ObjectNode finalResult = objectMapper.createObjectNode();
            finalResult.set("all_results", objectMapper.valueToTree(allResults));
            Files.write(Paths.get(fileName), objectMapper.writeValueAsBytes(finalResult),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            log.info("Data written to JSON file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean hasNextPage(JsonNode response) {
        JsonNode nextPage = response.get("next");
        log.info("next --> " + nextPage);
        return nextPage != null && !nextPage.isNull();
    }
    private String getNextPageUrl(JsonNode response) {
        return response.get("next").asText();
    }

}
