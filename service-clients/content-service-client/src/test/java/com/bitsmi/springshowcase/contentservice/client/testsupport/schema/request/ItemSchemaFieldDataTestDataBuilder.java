package com.bitsmi.springshowcase.contentservice.client.testsupport.schema.request;

import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaFieldData;
import com.bitsmi.springshowcase.contentservice.client.schema.shared.DataType;

public class ItemSchemaFieldDataTestDataBuilder
{
    public static ItemSchemaFieldData stringField(Long id)
    {
        return builder()
                .stringField()
                .build();
    }

    public static ItemSchemaFieldData numericField(Long id)
    {
        return builder()
                .numericField()
                .build();
    }

    public static ItemSchemaFieldDataTestDataBuilder.Builder builder()
    {
        return new ItemSchemaFieldDataTestDataBuilder.Builder();
    }

    public static final class Builder
    {
        private final ItemSchemaFieldData.Builder delegate = ItemSchemaFieldData.builder();

        public ItemSchemaFieldDataTestDataBuilder.Builder stringField()
        {
            name("stringField")
                    .dataType(DataType.STRING);

            return this;
        }

        public ItemSchemaFieldDataTestDataBuilder.Builder numericField()
        {
            name("numericField")
                    .dataType(DataType.NUMBER);

            return this;
        }

        public ItemSchemaFieldDataTestDataBuilder.Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public ItemSchemaFieldDataTestDataBuilder.Builder dataType(DataType dataType)
        {
            delegate.dataType(dataType);
            return this;
        }

        public ItemSchemaFieldDataTestDataBuilder.Builder comments(String comments)
        {
            delegate.comments(comments);
            return this;
        }

        public ItemSchemaFieldData build()
        {
            return delegate.build();
        }
    }
}
