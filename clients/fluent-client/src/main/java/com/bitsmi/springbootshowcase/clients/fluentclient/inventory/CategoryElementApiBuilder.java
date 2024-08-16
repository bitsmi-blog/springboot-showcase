package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

import java.util.Optional;

public class CategoryElementApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final Long id;

    public CategoryElementApiBuilder(RestClient restClient, Validator validator, Long id)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.id = id;
    }

    public Optional<Category> get()
    {
        return new GetCategoryOperation(restClient, id).get();
    }

    public Category update(CategoryData data)
    {
        return new UpdateCategoryOperation(restClient, validator, id, data)
                .update();
    }

    /* Provide access to "product" subdomain API Builder. Parameters
     * received by this API Builder are propagated to the downstream API Builder
     */
    public ProductSetApiBuilder products() {
        return new ProductSetApiBuilder(restClient, id);
    }
}
