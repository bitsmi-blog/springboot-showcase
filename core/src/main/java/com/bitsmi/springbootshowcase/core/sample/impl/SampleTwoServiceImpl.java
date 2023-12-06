package com.bitsmi.springbootshowcase.core.sample.impl;

import com.bitsmi.springbootshowcase.core.sample.ISampleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("sampleTwo")
public class SampleTwoServiceImpl implements ISampleService
{
    @Override
    public String getSample()
    {
        return "Sample Two";
    }
}
