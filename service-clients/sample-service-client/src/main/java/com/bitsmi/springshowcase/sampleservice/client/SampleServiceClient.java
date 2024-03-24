package com.bitsmi.springshowcase.sampleservice.client;

import com.bitsmi.springshowcase.sampleservice.client.content.ContentSelectionApiBuilder;
import com.bitsmi.springshowcase.sampleservice.client.content.request.ContentSelector;
import com.bitsmi.springshowcase.sampleservice.client.info.InfoApiBuilder;
import jakarta.validation.NoProviderFoundException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Objects;

public class SampleServiceClient
{
    private final RestClient restClient;
    private final Validator validator;

    private SampleServiceClient(RestClient restClient, Validator validator)
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

    public ContentSelectionApiBuilder content(ContentSelector selector)
    {
        return new ContentSelectionApiBuilder(restClient, validator, selector);
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

        public SampleServiceClient build()
        {
            Objects.requireNonNull(baseUrl);

            RestClient restClient = RestClient.builder()
                    // Use HTTPClient
                    .requestFactory(new HttpComponentsClientHttpRequestFactory())
                    .baseUrl(baseUrl)
                    .build();

            return new SampleServiceClient(restClient, validator);
        }
    }
}
