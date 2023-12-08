package com.bitsmi.springbootshowcase.api.content.request;

import com.bitsmi.springbootshowcase.api.content.shared.DataType;
import jakarta.validation.constraints.NotNull;

public record ItemSchemaField(
        @NotNull
        String name,
        @NotNull
        DataType dataType,
        String comments
)
{

}