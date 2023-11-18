package com.bitsmi.springbootshowcase.web.setup.controller;

import com.bitsmi.springbootshowcase.core.common.CommonConstants;
import com.bitsmi.springbootshowcase.core.common.IUserManagementService;
import com.bitsmi.springbootshowcase.web.application.controller.request.CreateAdminUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.nio.CharBuffer;
import java.util.List;

@RestController
@RequestMapping(value = "/api/setup", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Validated
public class SetupApiController
{
    @Autowired
    private IUserManagementService userManagementService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdminUser(@RequestBody @Valid CreateAdminUserRequest request)
    {
        boolean existUsers = userManagementService.existUsers();
        if(existUsers) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }

        String encodedPassword = passwordEncoder.encode(CharBuffer.wrap(request.password()));
        userManagementService.createUser(request.username(), encodedPassword, List.of(CommonConstants.USER_GROUP_ADMIN, CommonConstants.USER_GROUP_USER));
        // Clear in memory
        request.clearPassword();
    }
}
