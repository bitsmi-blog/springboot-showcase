package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "Request to change an existing user's password")
public record ChangeUserPasswordRequest(
        @NotEmpty
        @Schema(description = "New password as a char array")
        char[] password
) {

}
