package com.bitsmi.springbootshowcase.web.setup.controller;

import com.bitsmi.springbootshowcase.application.common.IUserCreationFlowCommand;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.web.setup.controller.request.CreateAdminUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/setup", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Validated
public class SetupApiController
{
    @Autowired
    private IUserCreationFlowCommand userCreationFlowCommand;

    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdminUser(@RequestBody @Valid CreateAdminUserRequest request)
    {
        try {
            userCreationFlowCommand.createAdminUser(
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
