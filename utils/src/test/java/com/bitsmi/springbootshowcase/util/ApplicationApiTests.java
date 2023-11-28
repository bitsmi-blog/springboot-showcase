package com.bitsmi.springbootshowcase.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@Tag("ManualTest")
public class ApplicationApiTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationApiTests.class);

    private static final String HOST = "http://localhost:8080";
    private WebClient client;

    @Test
    public void applicationHello() throws Exception
    {
        ResponseEntity<String> response = client.get()
                .uri("/api/application/hello")
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
