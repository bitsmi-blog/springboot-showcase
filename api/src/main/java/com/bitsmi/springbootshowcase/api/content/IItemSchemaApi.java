package com.bitsmi.springbootshowcase.api.content;

import com.bitsmi.springbootshowcase.api.common.request.PageRequest;
import com.bitsmi.springbootshowcase.api.common.response.PagedResponse;
import com.bitsmi.springbootshowcase.api.content.response.ItemSchema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface IItemSchemaApi
{
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    PagedResponse<ItemSchema> getSchemas(final PageRequest pageable);
}
