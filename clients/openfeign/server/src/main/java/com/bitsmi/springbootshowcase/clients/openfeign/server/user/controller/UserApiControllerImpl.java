package com.bitsmi.springbootshowcase.clients.openfeign.server.user.controller;

import com.bitsmi.springbootshowcase.clients.openfeign.api.user.UserApi;
import com.bitsmi.springbootshowcase.clients.openfeign.api.user.response.UserDetailsResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class UserApiControllerImpl implements UserApi
{
    @Override
    public UserDetailsResponse getDetails()
    {
        return UserDetailsResponse.builder()
                .username("john.doe")
                .completeName("John Doe")
                .build();
    }

    @Override
    public UserDetailsResponse getAdminDetails()
    {
        return UserDetailsResponse.builder()
                .username("admin")
                .build();
    }
}
