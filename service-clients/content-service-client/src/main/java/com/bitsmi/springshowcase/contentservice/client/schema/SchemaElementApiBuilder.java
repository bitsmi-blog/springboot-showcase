package com.bitsmi.springshowcase.contentservice.client.schema;

import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

import java.util.Optional;

public class SchemaElementApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final Long id;

    public SchemaElementApiBuilder(RestClient restClient, Validator validator, Long id)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.id = id;
    }

    public Optional<ItemSchema> get()
    {
        return new SchemaGetOperation(restClient, id).get();
    }

    public ItemSchema update(ItemSchemaData data)
    {
        return new SchemaUpdateOperation(restClient, validator, id, data)
                .update();
    }
}
