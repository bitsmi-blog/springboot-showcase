package com.bitsmi.springshowcase.sampleservice.client.content;

import com.bitsmi.springshowcase.sampleservice.client.content.request.ContentSelector;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

public class ContentSelectionApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final ContentSelector selector;

    public ContentSelectionApiBuilder(RestClient restClient, Validator validator, ContentSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    public ContentDetailsOperation details() {
        return new ContentDetailsOperation(restClient, validator, selector);
    }
}
