package com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.Constants;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(Constants.QUALIFIER_ADDITIONAL_PRODUCT)
public class AdditionalProductServiceImpl implements ProductService
{
    @Override
    public String getProductName()
    {
        return "Additional Product";
    }
}
