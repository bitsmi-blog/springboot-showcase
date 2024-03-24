package com.bitsmi.springshowcase.sampleservice.client.content.response;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ContentDetailResponse(Content content)
{
    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("content", content)
                .build();
    }
}
