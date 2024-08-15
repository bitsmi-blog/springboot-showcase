package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ChangeUserPasswordRequest(
        @NotEmpty char[] password
) {

}
