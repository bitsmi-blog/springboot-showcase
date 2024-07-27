package com.bitsmi.springbootshowcase.springmvc.apiversioning.web.headersstrategy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissingVersionHeaderController
{
    @RequestMapping(
            value = "/api/headers-strategy/**",
            headers = "!X-Showcase-Api-Version"
    )
    public ProblemDetail missingVersionHeader()
    {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Missing header");
        problemDetail.setDetail("Missing API version selector header (X-Showcase-Api-Version)");

        return problemDetail;
    }
}
