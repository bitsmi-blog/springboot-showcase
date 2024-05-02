package com.bitsmi.springshowcase.contentservice.client.schema;

import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

public class SchemaUpdateOperation
{
    public static final String ENDPOINT_PATH = "/api/schema/{id}";

    private final RestClient restClient;
    private final Validator validator;
    private final Long id;
    private final ItemSchemaData data;

    SchemaUpdateOperation(RestClient restClient, Validator validator, Long id, ItemSchemaData data)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.id = id;
        this.data = data;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link ItemSchema}
     */
    public ItemSchema update()
    {
        validateRequest();

        return restClient.put()
                .uri(this::buildURI)
                .body(data)
                .retrieve()
                .body(ItemSchema.class);
    }

    private void validateRequest()
    {
        Objects.requireNonNull(id);

        if(validator==null) {
            return;
        }

        Set<ConstraintViolation<ItemSchemaData>> violations = validator.validate(data);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private URI buildURI(UriBuilder uriBuilder) {
        uriBuilder.path(ENDPOINT_PATH);
        return uriBuilder.build(id);
    }
}
