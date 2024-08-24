package com.bitsmi.springbootshowcase.springcore.validation.application.inventory;

import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.ProductDto;
import com.bitsmi.springbootshowcase.springcore.validation.application.inventory.dto.StoreDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface InventoryService
{
    boolean createProduct(@NotNull @Valid ProductDto productDto);

    boolean createStoreWithMandatoryData(@NotNull @Valid StoreDto storeDto);
    boolean createStoreWithFullData(@NotNull @Valid StoreDto storeDto);
}
