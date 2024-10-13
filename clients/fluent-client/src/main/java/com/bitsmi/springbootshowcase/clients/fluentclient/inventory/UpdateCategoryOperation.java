package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.UpdateCategoryRequest;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

public class UpdateCategoryOperation {

    public static final String ENDPOINT_PATH = "/api/categories/{id}";

    private final RestClient restClient;
    private final Validator validator;
    private final Long id;
    private final CategoryData data;

    UpdateCategoryOperation(RestClient restClient, Validator validator, Long id, CategoryData data) {
        this.restClient = restClient;
        this.validator = validator;
        this.id = id;
        this.data = data;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link Category}
     */
    public Category execute() {
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .data(data)
                .build();

        validateRequest(request);

        return restClient.put()
                .uri(this::buildURI)
                .body(request)
                .retrieve()
                .body(Category.class);
    }

    private void validateRequest(UpdateCategoryRequest request) {
        Objects.requireNonNull(id);

        if(validator==null) {
            return;
        }

        Set<ConstraintViolation<UpdateCategoryRequest>> violations = validator.validate(request);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private URI buildURI(UriBuilder uriBuilder) {
        uriBuilder.path(ENDPOINT_PATH);
        return uriBuilder.build(id);
    }
}
