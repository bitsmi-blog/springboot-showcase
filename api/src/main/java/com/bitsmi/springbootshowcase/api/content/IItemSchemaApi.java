package com.bitsmi.springbootshowcase.api.content;

import com.bitsmi.springbootshowcase.api.common.request.PageRequest;
import com.bitsmi.springbootshowcase.api.common.response.PagedResponse;
import com.bitsmi.springbootshowcase.api.content.request.CreateItemSchemaRequest;
import com.bitsmi.springbootshowcase.api.content.response.ItemSchema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface IItemSchemaApi
{
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    PagedResponse<ItemSchema> getSchemas(final PageRequest pageable);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    com.bitsmi.springbootshowcase.api.content.response.ItemSchema createSchema(@RequestBody @Valid CreateItemSchemaRequest request);
}
