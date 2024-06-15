package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

public class CategoryCreationApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final CategoryData data;

    public CategoryCreationApiBuilder(RestClient restClient, Validator validator, CategoryData data)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.data = data;
    }

    public Category create()
    {
        return new CategoryCreateOperation(restClient, validator, data)
                .create();
    }
}
