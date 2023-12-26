package com.bitsmi.springbootshowcase.core.dummy.impl;

import com.bitsmi.springbootshowcase.core.dummy.ISampleCommand;
import com.bitsmi.springbootshowcase.core.dummy.ISampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SampleCommandImpl implements ISampleCommand
{
    @Autowired
    private ISampleService sampleService;

    private final String message;

    public SampleCommandImpl(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return sampleService.getSample() + ": " + message;
    }
}
