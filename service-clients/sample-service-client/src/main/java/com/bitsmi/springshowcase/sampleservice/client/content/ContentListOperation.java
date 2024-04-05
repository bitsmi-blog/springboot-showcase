package com.bitsmi.springshowcase.sampleservice.client.content;

import com.bitsmi.springshowcase.sampleservice.client.common.response.PagedResponse;
import com.bitsmi.springshowcase.sampleservice.client.content.request.ContentSelector;
import com.bitsmi.springshowcase.sampleservice.client.content.response.Content;
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

public class ContentListOperation
{
    public static final String ENDPOINT_PATH = "/api/content";

    private final RestClient restClient;
    private final Validator validator;

    private final ContentSelector selector;
    private Integer pageNumber;
    private Integer pageSize;

    ContentListOperation(RestClient restClient, Validator validator, ContentSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    public ContentListOperation paged(int pageNumber, int pageSize)
    {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        return this;
    }

    /**
     * TODO public ContentListOperation sort()
     */
    public ContentListOperation sort()
    {
        return this;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link PagedResponse}&lt;{@link Content}&gt; -
     */
    public PagedResponse<Content> get()
    {
        validateRequest();

        return restClient.get()
                .uri(this::buildURI)
                .retrieve()
                .body(new ParameterizedTypeReference<PagedResponse<Content>>(){});
    }

    private void validateRequest()
    {
        if(validator==null) {
            return;
        }

        Set<ConstraintViolation<ContentSelector>> violations = validator.validate(selector);
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
