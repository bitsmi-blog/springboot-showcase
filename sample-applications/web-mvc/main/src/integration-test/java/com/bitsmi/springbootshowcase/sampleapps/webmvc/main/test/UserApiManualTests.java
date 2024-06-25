package com.bitsmi.springbootshowcase.sampleapps.webmvc.main.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag("ManualTest")
class UserApiManualTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiManualTests.class);

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
    void getUserDetails()
    {
        final String token = auth();
        final ResponseEntity<String> responseEntity = client.get()
                .uri("/api/user/details")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(String.class);

        LOGGER.info("status: {}; message: {}", responseEntity.getStatusCode().value(), responseEntity.getBody());
    }

    @Test
    void getAdminDetails()
    {
        final String token = auth();
        final ResponseEntity<String> responseEntity = client.get()
                .uri("/api/user/admin/details")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(String.class);

        LOGGER.info("status: {}; message: {}", responseEntity.getStatusCode().value(), responseEntity.getBody());
    }

    /*---------------------------*
     * CONFIG AND HELPERS
     *---------------------------*/
    private String auth()
    {
        var basicAuth = new String(Base64.getEncoder().encode("admin:test".getBytes(StandardCharsets.UTF_8)));

        return client.post()
                .uri("/auth")
                .header("Authorization", "Basic " + basicAuth)
                .retrieve()
                .body(String.class);
    }
}
