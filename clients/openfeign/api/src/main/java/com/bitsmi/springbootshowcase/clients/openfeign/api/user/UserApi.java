package com.bitsmi.springbootshowcase.clients.openfeign.api.user;

import com.bitsmi.springbootshowcase.clients.openfeign.api.user.response.UserDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserApi
{
    @GetMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    UserDetailsResponse getDetails();

    @GetMapping("/admin/details")
    @ResponseStatus(HttpStatus.OK)
    UserDetailsResponse getAdminDetails();
}
