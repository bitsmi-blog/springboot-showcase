package com.bitsmi.springbootshowcase.clients.fluentclient;

import com.bitsmi.springbootshowcase.clients.fluentclient.common.exception.ClientErrorServiceException;
import com.bitsmi.springbootshowcase.clients.fluentclient.common.exception.ServerErrorServiceException;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.CategoryCreationApiBuilder;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.CategoryElementApiBuilder;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.CategorySetApiBuilder;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategorySetSelector;
import com.bitsmi.springbootshowcase.clients.fluentclient.info.InfoApiBuilder;
import jakarta.validation.NoProviderFoundException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ServiceClient
{
    private final RestClient restClient;
    private final Validator validator;

    private ServiceClient(RestClient restClient, Validator validator)
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

    public CategorySetApiBuilder categories()
    {
        return new CategorySetApiBuilder(restClient, validator);
    }

    public CategorySetApiBuilder categories(CategorySetSelector selector)
    {
        return new CategorySetApiBuilder(restClient, validator, selector);
    }

    public CategoryElementApiBuilder category(Long id)
    {
        return new CategoryElementApiBuilder(restClient, validator, id);
    }

    public CategoryCreationApiBuilder category(CategoryData data)
    {
        return new CategoryCreationApiBuilder(restClient, validator, data);
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

        public ServiceClient build()
        {
            Objects.requireNonNull(baseUrl);

            RestClient restClient = RestClient.builder()
                    // Use HTTPClient
                    .requestFactory(new HttpComponentsClientHttpRequestFactory())
                    .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new ClientErrorServiceException(
                                Integer.toString(response.getStatusCode().value()),
                                IOUtils.toString(response.getBody(), StandardCharsets.UTF_8)
                        );
                    })
                    .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new ServerErrorServiceException(
                                Integer.toString(response.getStatusCode().value()),
                                IOUtils.toString(response.getBody(), StandardCharsets.UTF_8)
                        );
                    })
                    .baseUrl(baseUrl)
                    .build();

            return new ServiceClient(restClient, validator);
        }
    }
}
