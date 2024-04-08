package com.bitsmi.springshowcase.contentservice.client.content;

import com.bitsmi.springshowcase.contentservice.client.content.request.ContentSelector;
import jakarta.validation.Validator;
import org.springframework.web.client.RestClient;

public class ContentSetApiBuilder
{
    private final RestClient restClient;
    private final Validator validator;

    private final ContentSelector selector;

    public ContentSetApiBuilder(RestClient restClient, Validator validator, ContentSelector selector)
    {
        this.restClient = restClient;
        this.validator = validator;
        this.selector = selector;
    }

    public ContentListOperation list() {
        return new ContentListOperation(restClient, validator, selector);
    }
}
