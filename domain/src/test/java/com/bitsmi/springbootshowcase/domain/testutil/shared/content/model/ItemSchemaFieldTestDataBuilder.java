package com.bitsmi.springbootshowcase.domain.testutil.shared.content.model;

import com.bitsmi.springbootshowcase.domain.content.model.DataType;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaField;

public class ItemSchemaFieldTestDataBuilder
{
    public static ItemSchemaField stringField()
    {
        return builder()
                .stringField()
                .build();
    }

    public static ItemSchemaField numericField()
    {
        return builder()
                .numericField()
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final ItemSchemaField.Builder delegate = ItemSchemaField.builder();

        public Builder stringField()
        {
            name("stringField")
                    .dataType(DataType.STRING);

            return this;
        }

        public Builder numericField()
        {
            name("numericField")
                    .dataType(DataType.NUMBER);

            return this;
        }

        public Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public Builder dataType(DataType dataType)
        {
            delegate.dataType(dataType);
            return this;
        }

        public Builder comments(String comments)
        {
            delegate.comments(comments);
            return this;
        }

        public ItemSchemaField build()
        {
            return delegate.build();
        }
    }
}
