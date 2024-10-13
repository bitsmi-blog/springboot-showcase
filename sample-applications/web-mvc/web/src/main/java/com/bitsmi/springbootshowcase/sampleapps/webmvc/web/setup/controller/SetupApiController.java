package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.setup.controller;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRegistryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.setup.controller.request.CreateAdminUserRequest;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/setup", produces = MediaType.APPLICATION_JSON_VALUE)
@Hidden
@Validated
public class SetupApiController
{
    @Autowired
    private UserRegistryApplicationService userCreationApplicationService;

    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdminUser(@RequestBody @Valid CreateAdminUserRequest request)
    {
        try {
            userCreationApplicationService.createAdminUser(
                    request.username(),
                    request.password()
            );
        }
        catch(ElementAlreadyExistsException e) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }
        request.clearPassword();
    }
}
