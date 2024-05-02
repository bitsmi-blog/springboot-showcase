package com.bitsmi.springshowcase.contentservice.client.schema;

import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

public class SchemaCreationApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final ItemSchemaData data;

    public SchemaCreationApiBuilder(RestClient restClient, Validator validator, ItemSchemaData data)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.data = data;
    }

    public ItemSchema create()
    {
        return new SchemaCreateOperation(restClient, validator, data)
                .create();
    }
}
