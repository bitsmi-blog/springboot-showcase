package com.bitsmi.springbootshowcase.sampleapps.webmvc.main.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Tag("ManualTest")
class SetupApiManualTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SetupApiManualTests.class);

    private static final String HOST = "http://localhost:8080";

    private RestClient client;

    @BeforeEach
    void setUp()
    {
        client = RestClient.builder()
                .baseUrl(HOST)
                .build();
    }

    @Test
    void createAdminUser()
    {
        Map<String, String> payload = Map.ofEntries(
                Map.entry("username", "admin"),
                Map.entry("password", "test")
        );

        ResponseEntity<Void> responseEntity = client.post()
                .uri("/api/setup/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .onStatus(
                        httpStatusCode -> httpStatusCode.isSameCodeAs(HttpStatus.NOT_FOUND),
                        (HttpRequest request, ClientHttpResponse response) -> LOGGER.warn("Not found")
                )
                .toBodilessEntity();

        LOGGER.info("status: {}", responseEntity.getStatusCode().value());
    }

    @Test
    void auth()
    {
        var basicAuth = new String(Base64.getEncoder().encode("admin:test".getBytes(StandardCharsets.UTF_8)));

        ResponseEntity<String> response = client.post()
                .uri("/auth")
                .header("Authorization", "Basic " + basicAuth)
                .retrieve()
                .toEntity(String.class);

        LOGGER.info("status: {}; message: {}", response.getStatusCode().value(), response.getBody());
    }
}
