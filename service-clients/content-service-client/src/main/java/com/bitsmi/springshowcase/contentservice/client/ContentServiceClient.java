package com.bitsmi.springshowcase.contentservice.client;

import com.bitsmi.springshowcase.contentservice.client.info.InfoApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaCreationApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaElementApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaSetApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.request.SchemaSetSelector;
import jakarta.validation.NoProviderFoundException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Objects;

public class ContentServiceClient
{
    private final RestClient restClient;
    private final Validator validator;

    private ContentServiceClient(RestClient restClient, Validator validator)
    {
        this.restClient = restClient;
        this.validator = validator;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public InfoApiBuilder info()
    {
        return new InfoApiBuilder(restClient);
    }

    public SchemaSetApiBuilder schemas()
    {
        return new SchemaSetApiBuilder(restClient, validator);
    }

    public SchemaSetApiBuilder schemas(SchemaSetSelector selector)
    {
        return new SchemaSetApiBuilder(restClient, validator, selector);
    }

    public SchemaElementApiBuilder schema(Long id)
    {
        return new SchemaElementApiBuilder(restClient, validator, id);
    }

    public SchemaCreationApiBuilder schema(ItemSchemaData data)
    {
        return new SchemaCreationApiBuilder(restClient, validator, data);
    }

    public static class Builder
    {
        private String baseUrl;
        private Validator validator;

        public Builder withBaseUrl(String baseUrl)
        {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder withValidator(Validator validator)
        {
            this.validator = validator;
            return this;
        }

        public Builder withDefaultValidation() throws NoProviderFoundException
        {
            this.validator = Validation.buildDefaultValidatorFactory().getValidator();
            return this;
        }

        public ContentServiceClient build()
        {
            Objects.requireNonNull(baseUrl);

            RestClient restClient = RestClient.builder()
                    // Use HTTPClient
                    .requestFactory(new HttpComponentsClientHttpRequestFactory())
                    .baseUrl(baseUrl)
                    .build();

            return new ContentServiceClient(restClient, validator);
        }
    }
}
