package com.bitsmi.springbootshowcase.api.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface IApplicationApi
{
    @GetMapping(value = "/hello", produces = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    String getHello();
}
