package com.bitsmi.springbootshowcase.web.content.controller;

import com.bitsmi.springbootshowcase.api.common.request.PagedRequest;
import com.bitsmi.springbootshowcase.api.common.response.PagedResponse;
import com.bitsmi.springbootshowcase.api.common.response.Sort;
import com.bitsmi.springbootshowcase.api.content.IItemSchemaApi;
import com.bitsmi.springbootshowcase.api.content.request.CreateItemSchemaRequest;
import com.bitsmi.springbootshowcase.application.content.ICreateItemSchemaApplicationCommand;
import com.bitsmi.springbootshowcase.application.content.IRetrieveItemSchemaApplicationQuery;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.web.common.mapper.IPaginationMapper;
import com.bitsmi.springbootshowcase.web.content.mapper.IItemSchemaApiMapper;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/api/content/schema", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "content.schema.api")
public class ItemSchemaApiControllerImpl implements IItemSchemaApi
{
    @Autowired
    private IRetrieveItemSchemaApplicationQuery retrieveItemSchemaQuery;
    @Autowired
    private ICreateItemSchemaApplicationCommand createItemSchemaCommand;
    @Autowired
    private IPaginationMapper paginationMapper;
    @Autowired
    private IItemSchemaApiMapper itemSchemaMapper;
    @Autowired
    private ObservationRegistry observationRegistry;

    @Override
    public PagedResponse<com.bitsmi.springbootshowcase.api.content.response.ItemSchema> getSchemas(
            PagedRequest pagedRequest
    )
    {
        final Pagination page = paginationMapper.fromRequest(pagedRequest);

        if(page!=null) {
            final PagedData<ItemSchema> results = retrieveItemSchemaQuery.retrieveAllItemSchemas(page);
            return PagedResponse.<com.bitsmi.springbootshowcase.api.content.response.ItemSchema>builder()
                    .content(results.content()
                            .stream()
                            .map(itemSchemaMapper::mapResponseFromModel)
                            .toList()
                    )
                    .pagination(paginationMapper.fromDomain(results.pagination()))
                    .pageCount(results.pageCount())
                    .totalCount(results.totalCount())
                    .totalPages(results.totalPages())
                    .build();
        }
        else {
            final List<ItemSchema> results = retrieveItemSchemaQuery.retrieveAllItemSchemas();
            return PagedResponse.<com.bitsmi.springbootshowcase.api.content.response.ItemSchema>builder()
                    .content(results.stream()
                            .map(itemSchemaMapper::mapResponseFromModel)
                            .toList()
                    )
                    .pagination(com.bitsmi.springbootshowcase.api.common.response.Pagination.of(
                            0,
                            results.size(),
                            Sort.UNSORTED)
                    )
                    .pageCount(results.size())
                    .totalCount(results.size())
                    .totalPages(1)
                    .build();
        }
    }

    @Override
    public com.bitsmi.springbootshowcase.api.content.response.ItemSchema createSchema(@RequestBody @Valid CreateItemSchemaRequest request)
    {
        try {
            ItemSchema inputItemSchema = itemSchemaMapper.mapModelFromRequest(request);
            ItemSchema createdItemSchema = createItemSchemaCommand.createItemSchema(inputItemSchema);

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
