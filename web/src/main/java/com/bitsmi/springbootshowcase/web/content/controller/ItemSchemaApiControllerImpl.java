package com.bitsmi.springbootshowcase.web.content.controller;

import com.bitsmi.springbootshowcase.api.common.request.PageRequest;
import com.bitsmi.springbootshowcase.api.common.response.PagedResponse;
import com.bitsmi.springbootshowcase.api.content.IItemSchemaApi;
import com.bitsmi.springbootshowcase.api.content.request.CreateItemSchemaRequest;
import com.bitsmi.springbootshowcase.core.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.core.content.IItemSchemaManagementService;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.web.content.mapper.IItemSchemaApiMapper;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/api/content/schema", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "content.schema.api")
public class ItemSchemaApiControllerImpl implements IItemSchemaApi
{
    @Autowired
    private IItemSchemaManagementService itemSchemaManagementService;
    @Autowired
    private IItemSchemaApiMapper itemSchemaMapper;
    @Autowired
    private ObservationRegistry observationRegistry;

    @Override
    public PagedResponse<com.bitsmi.springbootshowcase.api.content.response.ItemSchema> getSchemas(@Valid final PageRequest pageRequest)
    {
        final Pageable pageable = pageRequest!=null
                ? org.springframework.data.domain.PageRequest.of(pageRequest.page(), pageRequest.size())
                : Pageable.unpaged();

        final Page<ItemSchema> results = itemSchemaManagementService.findAllSchemas(pageable);

        return PagedResponse.<com.bitsmi.springbootshowcase.api.content.response.ItemSchema>builder()
                .content(results.getContent()
                        .stream()
                        .map(itemSchemaMapper::mapResponseFromModel)
                        .toList())
                .pageSize(results.getSize())
                .totalElements(results.getTotalElements())
                .totalPages(results.getTotalPages())
                .build();
    }

    @Override
    public com.bitsmi.springbootshowcase.api.content.response.ItemSchema createSchema(@RequestBody @Valid CreateItemSchemaRequest request)
    {
        try {
            ItemSchema inputItemSchema = itemSchemaMapper.mapModelFromRequest(request);
            ItemSchema createdItemSchema = itemSchemaManagementService.createSchema(inputItemSchema);

            // Event bound to the observation
            observationRegistry.getCurrentObservation()
                    .event(Observation.Event.of("user.created.event", "User created event"));


            return itemSchemaMapper.mapResponseFromModel(createdItemSchema);
        }
        catch(ElementAlreadyExistsException e) {
            observationRegistry.getCurrentObservation().error(e);

            throw HttpClientErrorException.create(HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    null,
                    null,
                    StandardCharsets.UTF_8);
        }
    }
}
