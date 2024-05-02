package com.bitsmi.springshowcase.contentservice.client.testsupport.schema.request;

import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaFieldData;
import com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response.ItemSchemaFieldTestDataBuilder;

import java.util.Set;

public class ItemSchemaDataTestDataBuilder
{
    public static final String EXTERNAL_ID_SCHEMA1 = "schema-1";
    public static final String EXTERNAL_ID_SCHEMA2 = "schema-2";

    public static ItemSchemaData schema1()
    {
        return builder()
                .emptySchema1()
                .defaultFields()
                .build();
    }

    public static ItemSchemaData schema2()
    {
        return builder()
                .emptySchema2()
                .defaultFields()
                .build();
    }

    public static ItemSchemaDataTestDataBuilder.Builder builder()
    {
        return new ItemSchemaDataTestDataBuilder.Builder();
    }

    public static final class Builder
    {
        private final ItemSchemaData.Builder delegate = ItemSchemaData.builder();

        public ItemSchemaDataTestDataBuilder.Builder emptySchema1()
        {
            delegate
                    .externalId(EXTERNAL_ID_SCHEMA1)
                    .name("Test schema 1");

            return this;
        }

        public ItemSchemaDataTestDataBuilder.Builder emptySchema2()
        {
            delegate.externalId(EXTERNAL_ID_SCHEMA2)
                    .name("Test schema 2");

            return this;
        }

        public ItemSchemaDataTestDataBuilder.Builder externalId(String externalId)
        {
            delegate.externalId(externalId);
            return this;
        }

        public ItemSchemaDataTestDataBuilder.Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public ItemSchemaDataTestDataBuilder.Builder defaultFields()
        {
            delegate.fields(Set.of(
                    ItemSchemaFieldDataTestDataBuilder.stringField(1L),
                    ItemSchemaFieldDataTestDataBuilder.builder()
                            .numericField()
                            .comments("Sample comments for numeric field")
                            .build()
            ));

            return this;
        }

        public ItemSchemaDataTestDataBuilder.Builder fields(Set<ItemSchemaFieldData> fields)
        {
            delegate.fields(fields);
            return this;
        }

        public ItemSchemaData build()
        {
            return delegate.build();
        }
    }
}
