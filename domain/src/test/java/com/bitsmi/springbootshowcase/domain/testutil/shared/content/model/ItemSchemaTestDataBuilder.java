package com.bitsmi.springbootshowcase.domain.testutil.shared.content.model;

import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaField;

import java.util.Set;

public class ItemSchemaTestDataBuilder
{
    public static final Long ID_SCHEMA1 = 1001L;
    public static final Long ID_SCHEMA2 = 1002L;
    public static final String EXTERNAL_ID_SCHEMA1 = "schema-1";
    public static final String EXTERNAL_ID_SCHEMA2 = "schema-2";

    public static ItemSchema schema1()
    {
        return builder()
                .emptySchema1()
                .defaultFields()
                .build();
    }

    public static ItemSchema schema2()
    {
        return builder()
                .emptySchema2()
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
                .name("Test schema 1");

            return this;
        }

        public Builder emptySchema2()
        {
            delegate
                    .id(ID_SCHEMA2)
                    .externalId(EXTERNAL_ID_SCHEMA2)
                    .name("Test schema 2");

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
