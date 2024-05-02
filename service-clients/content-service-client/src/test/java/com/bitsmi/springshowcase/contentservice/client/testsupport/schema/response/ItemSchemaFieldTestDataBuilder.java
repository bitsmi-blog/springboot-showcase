package com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response;

import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchemaField;
import com.bitsmi.springshowcase.contentservice.client.schema.shared.DataType;

import java.time.LocalDateTime;

public class ItemSchemaFieldTestDataBuilder
{
    public static final LocalDateTime DEFAULT_TIMESTAMP = LocalDateTime.of(2024, 1, 2, 10, 20);

    public static ItemSchemaField stringField(Long id)
    {
        return builder()
                .id(id)
                .stringField()
                .timestamps(DEFAULT_TIMESTAMP)
                .build();
    }

    public static ItemSchemaField numericField(Long id)
    {
        return builder()
                .id(id)
                .numericField()
                .timestamps(DEFAULT_TIMESTAMP)
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final ItemSchemaField.Builder delegate = ItemSchemaField.builder();

        public Builder id(Long id)
        {
            delegate.id(id);
            return this;
        }

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

        public Builder timestamps(LocalDateTime timestamp)
        {
            delegate.creationDate(timestamp);
            delegate.lastUpdated(timestamp);
            return this;
        }

        public ItemSchemaField build()
        {
            return delegate.build();
        }
    }
}
