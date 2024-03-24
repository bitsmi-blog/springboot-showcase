package com.bitsmi.springshowcase.sampleservice.client.content;

import com.bitsmi.springshowcase.sampleservice.client.content.request.ContentSelector;
import com.bitsmi.springshowcase.sampleservice.client.content.response.ContentDetailResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ContentDetailsOperation
{
    private final RestClient restClient;
    private final Validator validator;

    private final ContentSelector selector;

    ContentDetailsOperation(RestClient restClient, Validator validator, ContentSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    /**
     * @throws jakarta.validation.ValidationException
     * @return {@link ContentDetailResponse} -
     */
    public ContentDetailResponse get()
    {
        validateRequest();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/content")
                    .queryParam("selector", URLEncoder.encode(selector.toString(), StandardCharsets.UTF_8))
                    .build()
                )
                .retrieve()
                .body(ContentDetailResponse.class);
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
}
