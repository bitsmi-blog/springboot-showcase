package com.bitsmi.springbootshowcase.core.sample.impl;

import com.bitsmi.springbootshowcase.core.sample.ISampleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Qualifier("sampleThree")
@Profile("SAMPLE_PROFILE")
public class SampleThreeServiceImpl implements ISampleService
{
    @Override
    public String getSample()
    {
        return "Sample Three";
    }
}
