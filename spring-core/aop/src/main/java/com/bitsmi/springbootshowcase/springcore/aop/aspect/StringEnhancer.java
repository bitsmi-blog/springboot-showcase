package com.bitsmi.springbootshowcase.springcore.aop.aspect;

import org.springframework.stereotype.Component;

@Component
public class StringEnhancer
{
    public Object enhance(Object input)
    {
        if(!(input instanceof String)) {
            throw new IllegalArgumentException("Input cannot be enhanced");
        }

        return "Enhanced " + input;
    }
}
