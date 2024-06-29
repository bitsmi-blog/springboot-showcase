package com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.scoped.service.ParameterizedService;

public class ParameterizedServiceImpl implements ParameterizedService
{
    private final String name;

    public ParameterizedServiceImpl(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
