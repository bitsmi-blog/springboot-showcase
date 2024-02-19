package com.bitsmi.springbootshowcase.web.content.controller;

import com.bitsmi.springbootshowcase.api.common.request.PageRequest;
import com.bitsmi.springbootshowcase.api.common.response.PagedResponse;
import com.bitsmi.springbootshowcase.api.content.IItemSchemaApi;
import com.bitsmi.springbootshowcase.api.content.request.CreateItemSchemaRequest;
import com.bitsmi.springbootshowcase.application.content.IRetrieveItemSchemaFlowQuery;
import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.web.content.mapper.IItemSchemaApiMapper;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/content/schema", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "content.schema.api")
public class ItemSchemaApiControllerImpl implements IItemSchemaApi
{
    @Autowired
    private IRetrieveItemSchemaFlowQuery retrieveItemSchemaFlowQuery;
    @Autowired
    private IItemSchemaApiMapper itemSchemaMapper;
    @Autowired
    private ObservationRegistry observationRegistry;

    @Override
    public PagedResponse<com.bitsmi.springbootshowcase.api.content.response.ItemSchema> getSchemas(@Valid final PageRequest pageRequest)
    {
        final Page page = pageRequest!=null
                ? Page.of(pageRequest.page(), pageRequest.size())
                : null;

        if(pageRequest!=null) {
            final PagedData<ItemSchema> results = retrieveItemSchemaFlowQuery.retrieveAllItemSchemas(page);
            return PagedResponse.<com.bitsmi.springbootshowcase.api.content.response.ItemSchema>builder()
                    .content(results.content()
                            .stream()
                            .map(itemSchemaMapper::mapResponseFromModel)
                            .toList()
                    )
                    .pageSize(results.pageSize())
                    .totalElements(results.totalElements())
                    .totalPages(results.totalPages())
                    .build();
        }
        else {
            final List<ItemSchema> results = retrieveItemSchemaFlowQuery.retrieveAllItemSchemas();
            return PagedResponse.<com.bitsmi.springbootshowcase.api.content.response.ItemSchema>builder()
                    .content(results.stream()
                            .map(itemSchemaMapper::mapResponseFromModel)
                            .toList()
                    )
                    .pageSize(results.size())
                    .totalElements(results.size())
                    .totalPages(1)
                    .build();
        }
    }

    @Override
    public com.bitsmi.springbootshowcase.api.content.response.ItemSchema createSchema(@RequestBody @Valid CreateItemSchemaRequest request)
    {
        // TODO
//        try {
//            ItemSchema inputItemSchema = itemSchemaMapper.mapModelFromRequest(request);
//            ItemSchema createdItemSchema = itemSchemaManagementService.createSchema(inputItemSchema);
//
//            // Event bound to the observation
//            observationRegistry.getCurrentObservation()
//                    .event(Observation.Event.of("user.created.event", "User created event"));
//
//
//            return itemSchemaMapper.mapResponseFromModel(createdItemSchema);
//        }
//        catch(ElementAlreadyExistsException e) {
//            observationRegistry.getCurrentObservation().error(e);
//
//            throw HttpClientErrorException.create(HttpStatus.BAD_REQUEST,
//                    e.getMessage(),
//                    null,
//                    null,
//                    StandardCharsets.UTF_8);
//        }

        return null;
    }
}
