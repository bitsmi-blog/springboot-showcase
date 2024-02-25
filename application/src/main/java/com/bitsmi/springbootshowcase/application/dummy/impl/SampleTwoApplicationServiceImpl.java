package com.bitsmi.springbootshowcase.application.dummy.impl;

import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("sampleTwo")
public class SampleTwoApplicationServiceImpl implements ISampleApplicationService
{
    @Override
    public String getSample()
    {
        return "Sample Two";
    }
}
