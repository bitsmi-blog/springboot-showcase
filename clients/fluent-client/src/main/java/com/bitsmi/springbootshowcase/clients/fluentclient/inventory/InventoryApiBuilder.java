package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategorySetSelector;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

public class InventoryApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    public InventoryApiBuilder(RestClient restClient, Validator validator)
    {
        this.restClient = restClient;
        this.validator = validator;
    }

    public CategorySetApiBuilder categories()
    {
        return new CategorySetApiBuilder(restClient, validator);
    }

    public CategorySetApiBuilder categories(CategorySetSelector selector)
    {
        return new CategorySetApiBuilder(restClient, validator, selector);
    }

    public CategoryElementApiBuilder category(Long id)
    {
        return new CategoryElementApiBuilder(restClient, validator, id);
    }

    public CategoryCreationApiBuilder category(CategoryData data)
    {
        return new CategoryCreationApiBuilder(restClient, validator, data);
    }
}
