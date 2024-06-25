package com.bitsmi.springbootshowcase.clients.fluentclient.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.common.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategorySetSelector;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
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

public class CategoryListOperation
{
    public static final String ENDPOINT_PATH = "/api/category";

    private final RestClient restClient;
    private final Validator validator;

    private final CategorySetSelector selector;
    private Integer pageNumber;
    private Integer pageSize;

    CategoryListOperation(RestClient restClient, Validator validator, CategorySetSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    public CategoryListOperation paginate(int pageNumber, int pageSize)
    {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        return this;
    }

    /**
     * TODO public CategoryListOperation sort()
     */
    public CategoryListOperation sort()
    {
        return this;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link PaginatedResponse}&lt;{@link Category}&gt; -
     */
    public PaginatedResponse<Category> get()
    {
        validateRequest();

        return restClient.get()
                .uri(this::buildURI)
                .retrieve()
                .body(new ParameterizedTypeReference<PaginatedResponse<Category>>(){});
    }

    private void validateRequest()
    {
        if(validator==null) {
            return;
        }

        if(selector==null) {
            return;
        }

        Set<ConstraintViolation<CategorySetSelector>> violations = validator.validate(selector);
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
