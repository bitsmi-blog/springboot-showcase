package com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.service.impl;

import com.bitsmi.springbootshowcase.springcore.dependencyinjection.Constants;
import com.bitsmi.springbootshowcase.springcore.dependencyinjection.product.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Qualifier(Constants.QUALIFIER_OPTIONAL_PRODUCT)
@Profile(Constants.PROFILE_OPTIONAL)
public class OptionalProfileProductServiceImpl implements ProductService
{
    @Override
    public String getProductName()
    {
        return "Optional Product";
    }
}
