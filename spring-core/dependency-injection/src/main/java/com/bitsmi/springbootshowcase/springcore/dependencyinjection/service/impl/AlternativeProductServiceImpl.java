package com.bitsmi.springbootshowcase.springcore.dependencyinjection.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.Constants;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(Constants.QUALIFIER_ALTERNATIVE_PRODUCT)
public class AlternativeProductServiceImpl implements ProductService
{
    @Override
    public String getProductName()
    {
        return "Alternative Product";
    }
}
