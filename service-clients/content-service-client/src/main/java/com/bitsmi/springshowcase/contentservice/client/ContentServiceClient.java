package com.bitsmi.springshowcase.contentservice.client;

import com.bitsmi.springshowcase.contentservice.client.content.ContentSetApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.content.request.ContentSelector;
import com.bitsmi.springshowcase.contentservice.client.info.InfoApiBuilder;
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

    public ContentSetApiBuilder content(ContentSelector selector)
    {
        return new ContentSetApiBuilder(restClient, validator, selector);
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
