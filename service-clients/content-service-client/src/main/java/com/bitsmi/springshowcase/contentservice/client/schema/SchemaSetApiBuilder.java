package com.bitsmi.springshowcase.contentservice.client.schema;

import com.bitsmi.springshowcase.contentservice.client.schema.request.SchemaSetSelector;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

public class SchemaSetApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final SchemaSetSelector selector;

    public SchemaSetApiBuilder(RestClient restClient, Validator validator)
    {
        this(restClient, validator, null);
    }

    public SchemaSetApiBuilder(RestClient restClient, Validator validator, SchemaSetSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    public SchemaListOperation list()
    {
        return new SchemaListOperation(restClient, validator, selector);
    }
}
