package com.bitsmi.springbootshowcase.springcore.validation.application.inventory;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface ProductService
{
    boolean createProduct(@NotNull @Valid ProductDto productDto);
}
