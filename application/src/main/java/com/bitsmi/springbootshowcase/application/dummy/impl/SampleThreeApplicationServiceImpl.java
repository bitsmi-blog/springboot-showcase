package com.bitsmi.springbootshowcase.application.dummy.impl;

import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Qualifier("sampleThree")
@Profile("SAMPLE_PROFILE")
public class SampleThreeApplicationServiceImpl implements ISampleApplicationService
{
    @Override
    public String getSample()
    {
        return "Sample Three";
    }
}
