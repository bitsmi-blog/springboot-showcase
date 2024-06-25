package com.bitsmi.springbootshowcase.springcore.cache.application.inventory;

import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface ProductApplicationQuery
{
    Optional<Product> findProductByExternalId(@NotNull String externalId);
}
