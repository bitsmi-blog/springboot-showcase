package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.application.controller;

import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/application", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "application.api")
public class ApplicationApiController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationApiController.class);

    @GetMapping(value = "/hello", produces = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    public String getHello()
    {
        LOGGER.info("[getHello] Hello request");
        return "Hello from SpringBoot Showcase application";
    }
}
