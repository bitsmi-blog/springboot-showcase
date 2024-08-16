package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Set;

public class CreateCategoryOperation
{
    public static final String ENDPOINT_PATH = "/api/category";

    private final RestClient restClient;
    private final Validator validator;
    private final CategoryData data;

    CreateCategoryOperation(RestClient restClient, Validator validator, CategoryData data)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.data = data;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link Category}
     */
    public Category create()
    {
        validateRequest();

        return restClient.post()
                .uri(this::buildURI)
                .body(data)
                .retrieve()
                .body(Category.class);
    }

    private void validateRequest()
    {
        if(validator==null) {
            return;
        }

        Set<ConstraintViolation<CategoryData>> violations = validator.validate(data);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private URI buildURI(UriBuilder uriBuilder) {
        uriBuilder.path(ENDPOINT_PATH);
        return uriBuilder.build();
    }
}
