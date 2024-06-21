package com.bitsmi.springbootshowcase.sampleapps.webmvc.main.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@Tag("ManualTest")
class ApplicationApiTests
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationApiTests.class);

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
    void applicationHello() throws Exception
    {
        ResponseEntity<String> response = client.get()
                .uri("/api/application/hello")
                .retrieve()
                .toEntity(String.class);

        LOGGER.info("status: {}; message: {}", response.getStatusCode().value(), response.getBody());
    }
}
