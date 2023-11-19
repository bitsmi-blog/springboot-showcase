package com.bitsmi.springbootshowcase.api.user;

import com.bitsmi.springbootshowcase.api.user.response.UserDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface IUserApi
{
    @GetMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    UserDetailsResponse getDetails();
}
