package com.bitsmi.springbootshowcase.springmvc.apiversioning.web.headersstrategy.controller;

import com.bitsmi.springbootshowcase.springmvc.apiversioning.web.headersstrategy.v2.controller.SampleApiV2Controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/headers-strategy/sample",
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = "!X-Showcase-Api-Version"
)
public class SampleApiController extends SampleApiV2Controller
{

}
