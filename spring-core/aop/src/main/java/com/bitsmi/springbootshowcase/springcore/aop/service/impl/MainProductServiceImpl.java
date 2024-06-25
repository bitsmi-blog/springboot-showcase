package com.bitsmi.springbootshowcase.springcore.aop.service.impl;

import com.bitsmi.springbootshowcase.springcore.aop.service.ProductService;
import com.bitsmi.springbootshowcase.springcore.aop.util.Enhanced;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MainProductServiceImpl implements ProductService
{
    @Override
    public String getProductReference()
    {
        return "ref-main";
    }

    @Override
    @Enhanced
    public String getProductName()
    {
        return "Main Product";
    }
}
