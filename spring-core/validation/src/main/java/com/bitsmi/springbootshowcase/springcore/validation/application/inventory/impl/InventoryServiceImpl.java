package com.bitsmi.springbootshowcase.springcore.validation.application.inventory.impl;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.InventoryService;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.StoreDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.FullValidationGroup;
import com.bitsmi.springbootshowcase.springcore.validation.application.util.MandatoryValidationGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class InventoryServiceImpl implements InventoryService
{
    public boolean createProduct(@NotNull @Valid ProductDto productDto) {
        // Create product...
        return true;
    }

    @Override
    @Validated({ MandatoryValidationGroup.class })
    public boolean createStoreWithMandatoryData(@NotNull @Valid StoreDto storeDto) {
        return true;
    }

    @Override
    @Validated({ FullValidationGroup.class })
    public boolean createStoreWithFullData(@NotNull @Valid StoreDto storeDto) {
        return true;
    }
}
