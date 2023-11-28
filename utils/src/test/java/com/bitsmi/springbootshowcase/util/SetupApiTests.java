package com.bitsmi.springbootshowcase.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Tag("ManualTest")
public class SetupApiTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SetupApiTests.class);

    private static final String HOST = "http://localhost:8080";

    private WebClient client;

    @Test
    public void createAdminUser()
    {
        Map<String, String> payload = Map.ofEntries(
                Map.entry("username", "admin"),
                Map.entry("password", "test")
        );

        ResponseEntity<Void> response = client.post()
                .uri("/api/setup/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onRawStatus(httpStatusCode -> httpStatusCode==404, clientResponse -> Mono.empty())
                .toBodilessEntity()
                .block();

        LOGGER.info("status: {}", response.getStatusCode().value());
    }

    @Test
    public void auth()
    {
        var basicAuth = new String(Base64.getEncoder().encode("admin:test".getBytes(StandardCharsets.UTF_8)));

        ResponseEntity<String> response = client.post()
                .uri("/auth")
                .header("Authorization", "Basic " + basicAuth)
                .retrieve()
                .toEntity(String.class)
                .block();

        LOGGER.info("status: {}; message: {}", response.getStatusCode().value(), response.getBody());
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
}
