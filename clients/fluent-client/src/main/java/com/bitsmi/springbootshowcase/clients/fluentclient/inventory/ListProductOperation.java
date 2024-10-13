package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.common.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

public class ListProductOperation
{
    public static final String ENDPOINT_PATH = "/api/categories/{categoryId}/products";

    private final RestClient restClient;
    private final Long categoryId;

    ListProductOperation(RestClient restClient, Long categoryId)
    {
        this.restClient = restClient;
        this.categoryId = categoryId;
    }

    public PaginatedResponse<Product> get()
    {
        return restClient.get()
                .uri(this::buildURI)
                .retrieve()
                .body(new ParameterizedTypeReference<PaginatedResponse<Product>>(){});
    }

    private URI buildURI(UriBuilder uriBuilder) {
        uriBuilder.path(ENDPOINT_PATH);
        return uriBuilder.build(categoryId);
    }
}
