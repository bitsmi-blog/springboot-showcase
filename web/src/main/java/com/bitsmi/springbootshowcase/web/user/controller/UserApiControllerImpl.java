package com.bitsmi.springbootshowcase.web.user.controller;

import com.bitsmi.springbootshowcase.api.user.IUserApi;
import com.bitsmi.springbootshowcase.api.user.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.web.common.service.IAuthenticationPrincipalService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "user.api")
public class UserApiControllerImpl implements IUserApi
{
    @Autowired
    private IAuthenticationPrincipalService authenticationPrincipalService;

    public UserDetailsResponse getDetails()
    {
        final UserDetails userDetails = authenticationPrincipalService.getAuthenticationPrincipal();

        return UserDetailsResponse.builder()
                .username(userDetails.getUsername())
                .build();
    }
}
