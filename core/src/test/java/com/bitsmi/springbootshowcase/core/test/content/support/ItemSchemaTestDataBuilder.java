package com.bitsmi.springbootshowcase.core.test.content.support;

import com.bitsmi.springbootshowcase.core.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchemaField;

import java.util.Set;

public class ItemSchemaTestDataBuilder
{
    public static final Long ID_SCHEMA1 = 1001L;
    public static final String EXTERNAL_ID_SCHEMA1 = "schema-1";

    public static ItemSchema schema1()
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
        private final ItemSchema.Builder delegate = ItemSchema.builder();

        public Builder emptySchema1()
        {
            delegate
                .id(ID_SCHEMA1)
                .externalId(EXTERNAL_ID_SCHEMA1)
                .name("Test schema");

            return this;
        }

        public Builder id(Long id)
        {
            delegate.id(id);
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
            delegate.fields(Set.of(
                ItemSchemaFieldTestDataBuilder.stringField(),
                ItemSchemaFieldTestDataBuilder.builder().numericField().comments("Sample comments for numeric field").build()
            ));

            return this;
        }

        public Builder fields(Set<ItemSchemaField> fields)
        {
            delegate.fields(fields);
            return this;
        }

        public ItemSchema build()
        {
            return delegate.build();
        }
    }
}
