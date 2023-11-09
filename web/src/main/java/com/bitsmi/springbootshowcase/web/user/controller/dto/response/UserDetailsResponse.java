package com.bitsmi.springbootshowcase.web.user.controller.dto.response;

import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record UserDetailsResponse(
        String username
)
{

}
