package com.bitsmi.springshowcase.sampleservice.client.info;

import org.springframework.web.client.RestClient;

public class ServiceVersionOperation
{
    private final RestClient restClient;

    ServiceVersionOperation(RestClient restClient)
    {
        this.restClient = restClient;
    }

    public String get()
    {
        return restClient.get()
                .uri("/api/info/service-version")
                .retrieve()
                .body(String.class);
    }
}
