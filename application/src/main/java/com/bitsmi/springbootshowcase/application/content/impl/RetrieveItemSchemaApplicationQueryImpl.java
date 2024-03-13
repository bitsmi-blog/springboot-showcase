package com.bitsmi.springbootshowcase.application.content.impl;

import com.bitsmi.springbootshowcase.application.content.IRetrieveItemSchemaApplicationQuery;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainQueryService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetrieveItemSchemaApplicationQueryImpl implements IRetrieveItemSchemaApplicationQuery
{
    @Autowired
    private IItemSchemaDomainQueryService itemSchemaQueryDomainService;

    @Override
    public List<ItemSchema> retrieveAllItemSchemas()
    {
        return itemSchemaQueryDomainService.findAllItemSchemas();
    }

    @Override
    public PagedData<ItemSchema> retrieveAllItemSchemas(Pagination page)
    {
        return itemSchemaQueryDomainService.findAllItemSchemas(page);
    }
}
