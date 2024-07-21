package com.bitsmi.springbootshowcase.springmvc.apiversioning.web.headersstrategy.v2.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/headers-strategy/sample",
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = "X-Showcase-Api-Version=V2"
)
public class SampleApiV2Controller
{
    @GetMapping(produces = "text/plain")
    public String getSample()
    {
        return "SampleV2";
    }
}
