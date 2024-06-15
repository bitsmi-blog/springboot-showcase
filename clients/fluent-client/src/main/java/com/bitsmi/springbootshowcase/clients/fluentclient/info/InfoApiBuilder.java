package com.bitsmi.springbootshowcase.clients.fluentclient.info;

import org.springframework.web.client.RestClient;

public class InfoApiBuilder {

    private final RestClient restClient;

    public InfoApiBuilder(RestClient restClient) {
        this.restClient = restClient;
    }

    public ServiceVersionOperation serviceVersion() {
        return new ServiceVersionOperation(restClient);
    }
}
