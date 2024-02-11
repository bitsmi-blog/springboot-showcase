package com.bitsmi.springbootshowcase.application.dummy.impl;

import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationCommand;
import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SampleApplicationCommandImpl implements ISampleApplicationCommand
{
    @Autowired
    private ISampleApplicationService sampleService;

    private final String message;

    public SampleApplicationCommandImpl(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return sampleService.getSample() + ": " + message;
    }
}
