package com.bitsmi.springbootshowcase.api.content.response;

import com.bitsmi.springbootshowcase.api.content.shared.DataType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ItemSchemaField(
        Long id,
        @NotNull
        String name,
        @NotNull
        DataType dataType,
        String comments,
        LocalDateTime creationDate,
        LocalDateTime lastUpdated
)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("creationDate", creationDate)
                .append("lastUpdated", lastUpdated)
                .build();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemSchemaField other = (ItemSchemaField) o;
        return Objects.equals(name, other.name);
    }
}
