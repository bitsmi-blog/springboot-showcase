package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.impl;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.ProductService;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductServiceImpl implements ProductService
{
    public boolean createProduct(@NotNull @Valid ProductDto productDto)
    {
        // Create product...
        return true;
    }
}
