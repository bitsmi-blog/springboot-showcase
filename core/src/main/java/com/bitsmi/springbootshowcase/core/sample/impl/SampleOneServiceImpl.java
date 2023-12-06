package com.bitsmi.springbootshowcase.core.sample.impl;

import com.bitsmi.springbootshowcase.core.sample.ISampleService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SampleOneServiceImpl implements ISampleService
{
    @Override
    public String getSample()
    {
        return "Sample One";
    }
}
