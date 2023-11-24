package com.bitsmi.springbootshowcase.core.content.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ItemSchemaFieldDto(
        @NotNull
        String name,
        @NotNull
        DataType dataType,
        String comments
)
{

}
