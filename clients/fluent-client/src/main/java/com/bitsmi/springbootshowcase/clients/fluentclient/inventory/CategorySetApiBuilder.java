package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategorySetSelector;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

public class CategorySetApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final CategorySetSelector selector;

    public CategorySetApiBuilder(RestClient restClient, Validator validator)
    {
        this(restClient, validator, null);
    }

    public CategorySetApiBuilder(RestClient restClient, Validator validator, CategorySetSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    public CategoryListOperation list()
    {
        return new CategoryListOperation(restClient, validator, selector);
    }
}
