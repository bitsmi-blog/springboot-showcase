package com.bitsmi.springshowcase.contentservice.client.schema.request;

import com.bitsmi.springshowcase.contentservice.client.schema.shared.DataType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ItemSchemaFieldData(
        @NotNull
        String name,
        @NotNull
        DataType dataType,
        String comments
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("dataType", dataType)
                .append("comments", comments)
                .build();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof ItemSchemaFieldData other
                && Objects.equals(name, other.name);
    }
}
