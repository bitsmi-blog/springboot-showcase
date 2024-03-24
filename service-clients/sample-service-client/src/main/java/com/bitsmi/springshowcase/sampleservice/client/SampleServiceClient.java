package com.bitsmi.springshowcase.sampleservice.client;

import com.bitsmi.springshowcase.sampleservice.client.info.InfoApiBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class SampleServiceClient
{
    private final RestClient restClient;

    public SampleServiceClient(String baseUrl) {
        restClient = RestClient.builder()
                // Use HTTPClient
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(baseUrl)
                .build();
    }

    public InfoApiBuilder info() {
        return new InfoApiBuilder(restClient);
    }
}
