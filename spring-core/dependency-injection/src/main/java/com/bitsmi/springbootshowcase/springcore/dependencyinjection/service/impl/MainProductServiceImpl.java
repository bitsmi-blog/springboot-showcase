package com.bitsmi.springbootshowcase.springcore.dependencyinjection.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.service.ProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MainProductServiceImpl implements ProductService
{
    @Override
    public String getProductName()
    {
        return "Main Product";
    }
}
