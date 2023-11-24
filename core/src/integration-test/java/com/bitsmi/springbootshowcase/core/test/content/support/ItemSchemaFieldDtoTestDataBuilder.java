package com.bitsmi.springbootshowcase.core.test.content.support;

import com.bitsmi.springbootshowcase.core.content.dto.DataType;
import com.bitsmi.springbootshowcase.core.content.dto.ItemSchemaFieldDto;

public class ItemSchemaFieldDtoTestDataBuilder
{
    public static ItemSchemaFieldDto stringField()
    {
        return builder()
                .stringField()
                .build();
    }

    public static ItemSchemaFieldDto numericField()
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
        private final ItemSchemaFieldDto.Builder delegate = ItemSchemaFieldDto.builder();

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

        public ItemSchemaFieldDto build()
        {
            return delegate.build();
        }
    }
}
