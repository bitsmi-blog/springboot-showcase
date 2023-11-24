package com.bitsmi.springbootshowcase.core.test.content.support;

import com.bitsmi.springbootshowcase.core.content.dto.ItemSchemaDto;
import com.bitsmi.springbootshowcase.core.content.dto.ItemSchemaFieldDto;

import java.util.List;

public class ItemSchemaDtoTestDataBuilder
{
    public static final String EXTERNAL_ID_SCHEMA1 = "schema-1";

    public static ItemSchemaDto schema1()
    {
        return builder()
                .emptySchema1()
                .defaultFields()
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final ItemSchemaDto.Builder delegate = ItemSchemaDto.builder();

        public Builder emptySchema1()
        {
            delegate
                .externalId(EXTERNAL_ID_SCHEMA1)
                .name("Test schema");

            return this;
        }

        public Builder externalId(String externalId)
        {
            delegate.externalId(externalId);
            return this;
        }

        public Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public Builder defaultFields()
        {
            delegate.fields(List.of(
                ItemSchemaFieldDtoTestDataBuilder.stringField(),
                ItemSchemaFieldDtoTestDataBuilder.builder().numericField().comments("Sample comments for numeric field").build()
            ));

            return this;
        }

        public Builder fields(List<ItemSchemaFieldDto> fields)
        {
            delegate.fields(fields);
            return this;
        }

        public ItemSchemaDto build()
        {
            return delegate.build();
        }
    }
}
