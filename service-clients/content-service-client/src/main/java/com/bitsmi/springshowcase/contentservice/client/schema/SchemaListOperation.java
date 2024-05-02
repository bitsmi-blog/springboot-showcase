package com.bitsmi.springshowcase.contentservice.client.schema;

import com.bitsmi.springshowcase.contentservice.client.common.response.PagedResponse;
import com.bitsmi.springshowcase.contentservice.client.schema.request.SchemaSetSelector;
import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class SchemaListOperation
{
    public static final String ENDPOINT_PATH = "/api/schema";

    private final RestClient restClient;
    private final Validator validator;

    private final SchemaSetSelector selector;
    private Integer pageNumber;
    private Integer pageSize;

    SchemaListOperation(RestClient restClient, Validator validator, SchemaSetSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    public SchemaListOperation paginate(int pageNumber, int pageSize)
    {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        return this;
    }

    /**
     * TODO public SchemaListOperation sort()
     */
    public SchemaListOperation sort()
    {
        return this;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link PagedResponse}&lt;{@link ItemSchema}&gt; -
     */
    public PagedResponse<ItemSchema> get()
    {
        validateRequest();

        return restClient.get()
                .uri(this::buildURI)
                .retrieve()
                .body(new ParameterizedTypeReference<PagedResponse<ItemSchema>>(){});
    }

    private void validateRequest()
    {
        if(validator==null) {
            return;
        }

        if(selector==null) {
            return;
        }

        Set<ConstraintViolation<SchemaSetSelector>> violations = validator.validate(selector);
        if(!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private URI buildURI(UriBuilder uriBuilder) {
        uriBuilder.path(ENDPOINT_PATH);
        if(selector != null) {
            uriBuilder.queryParam("selector", URLEncoder.encode(selector.toString(), StandardCharsets.UTF_8));
        }
        if(pageNumber!=null) {
            uriBuilder.queryParam("page", pageNumber);
        }
        if(pageSize!=null) {
            uriBuilder.queryParam("pageSize", pageSize);
        }

        return uriBuilder.build();
    }
}
