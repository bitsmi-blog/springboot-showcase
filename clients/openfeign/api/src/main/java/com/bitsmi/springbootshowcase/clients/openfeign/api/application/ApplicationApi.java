package com.bitsmi.springbootshowcase.clients.openfeign.api.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ApplicationApi
{
    @GetMapping(value = "/hello", produces = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    String getHello();
}
