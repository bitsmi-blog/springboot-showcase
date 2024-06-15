package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.common.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Product;
import org.springframework.web.client.RestClient;

public class ProductSetApiBuilder
{
    private final RestClient restClient;
    private final Long categoryId;

    public ProductSetApiBuilder(RestClient restClient, Long categoryId)
    {
        this.restClient = restClient;
        this.categoryId = categoryId;
    }

    public PaginatedResponse<Product> list()
    {
        return new ProductListOperation(restClient, categoryId).get();
    }
}
