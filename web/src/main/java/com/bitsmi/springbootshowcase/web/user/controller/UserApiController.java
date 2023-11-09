package com.bitsmi.springbootshowcase.web.user.controller;

import com.bitsmi.springbootshowcase.web.user.controller.dto.response.UserDetailsResponse;
import io.micrometer.observation.annotation.Observed;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "user.api")
public class UserApiController
{
    @GetMapping("/details")
    public UserDetailsResponse getDetails(@AuthenticationPrincipal UserDetails userDetails)
    {
        return UserDetailsResponse.builder()
                .username(userDetails.getUsername())
                .build();
    }
}
