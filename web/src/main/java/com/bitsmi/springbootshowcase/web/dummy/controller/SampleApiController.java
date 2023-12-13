package com.bitsmi.springbootshowcase.web.dummy.controller;

import com.bitsmi.springbootshowcase.core.dummy.ISampleService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/dummy/sample", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "dummy.sample.api")
public class SampleApiController
{
    @Autowired
    private ISampleService sampleService;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getSample()
    {
        String sample = sampleService.getSample();
        sampleService.getSample();
        sampleService.getSample();

        return sample;
    }
}
