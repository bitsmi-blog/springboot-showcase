package com.bitsmi.springbootshowcase.application.content.impl;

import com.bitsmi.springbootshowcase.application.content.IRetrieveItemSchemaFlowQuery;
import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.IRetrieveItemSchemaQuery;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetrieveItemSchemaFlowQueryImpl implements IRetrieveItemSchemaFlowQuery
{
    @Autowired
    private IRetrieveItemSchemaQuery retrieveItemSchemaQuery;

    @Override
    public List<ItemSchema> retrieveAllItemSchemas()
    {
        return retrieveItemSchemaQuery.retrieveAllItemSchemas();
    }

    @Override
    public PagedData<ItemSchema> retrieveAllItemSchemas(Page page)
    {
        return retrieveItemSchemaQuery.retrieveAllItemSchemas(page);
    }
}
