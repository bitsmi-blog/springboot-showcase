package com.bitsmi.springbootshowcase.web.application.controller;

import com.bitsmi.springbootshowcase.api.application.IApplicationApi;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/application", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "application.api")
public class ApplicationApiControllerImpl implements IApplicationApi
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationApiControllerImpl.class);

    @Override
    public String getHello()
    {
        LOGGER.info("[getHello] Hello request");
        return "Hello from SpringBoot Showcase application";
    }
}
