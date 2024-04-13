package com.bitsmi.springshowcase.contentservice.client.schema;

import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Set;

public class SchemaCreateOperation
{
    public static final String ENDPOINT_PATH = "/api/schema";

    private final RestClient restClient;
    private final Validator validator;
    private final ItemSchemaData data;

    SchemaCreateOperation(RestClient restClient, Validator validator, ItemSchemaData data)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.data = data;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link ItemSchema}
     */
    public ItemSchema create()
    {
        validateRequest();

        return restClient.post()
                .uri(this::buildURI)
                .body(data)
                .retrieve()
                .body(ItemSchema.class);
    }

    private void validateRequest()
    {
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
        return uriBuilder.build();
    }
}
