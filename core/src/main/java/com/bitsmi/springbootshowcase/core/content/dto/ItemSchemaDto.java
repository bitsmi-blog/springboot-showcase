package com.bitsmi.springbootshowcase.core.content.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ItemSchemaDto(
        @NotNull
        String externalId,
        @NotNull
        String name,
        List<@Valid ItemSchemaFieldDto> fields
)
{

}
