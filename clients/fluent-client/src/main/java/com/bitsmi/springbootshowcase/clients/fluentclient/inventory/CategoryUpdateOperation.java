package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

public class CategoryUpdateOperation
{
    public static final String ENDPOINT_PATH = "/api/category/{id}";

    private final RestClient restClient;
    private final Validator validator;
    private final Long id;
    private final CategoryData data;

    CategoryUpdateOperation(RestClient restClient, Validator validator, Long id, CategoryData data)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.id = id;
        this.data = data;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link Category}
     */
    public Category update()
    {
        validateRequest();

        return restClient.put()
                .uri(this::buildURI)
                .body(data)
                .retrieve()
                .body(Category.class);
    }

    private void validateRequest()
    {
        Objects.requireNonNull(id);

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
        return uriBuilder.build(id);
    }
}
