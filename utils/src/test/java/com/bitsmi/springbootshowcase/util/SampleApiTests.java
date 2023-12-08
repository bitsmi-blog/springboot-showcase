package com.bitsmi.springbootshowcase.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag("ManualTest")
public class SampleApiTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleApiTests.class);

    private static final String HOST = "http://localhost:8080";

    private WebClient client;

    @Test
    public void getSample()
    {
        auth().flatMap(token -> client.get()
                .uri("/api/dummy/sample")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(String.class)
            )
            .doOnSuccess(response -> LOGGER.info("status: {}; message: {}", response.getStatusCode().value(), response.getBody()))
            .block();
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @BeforeEach
    public void setUp()
    {
        client = WebClient.builder()
                .baseUrl(HOST)
                .build();
    }

    private Mono<String> auth()
    {
        var basicAuth = new String(Base64.getEncoder().encode("admin:test".getBytes(StandardCharsets.UTF_8)));

        return client.post()
                .uri("/auth")
                .header("Authorization", "Basic " + basicAuth)
                .retrieve()
                .toEntity(String.class)
                .map(entity -> entity.getBody());
    }
}
