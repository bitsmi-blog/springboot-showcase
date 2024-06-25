package com.bitsmi.springbootshowcase.springcore.cache.web.controller;

import com.bitsmi.springbootshowcase.springcore.cache.application.inventory.ProductApplicationQuery;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/inventory/product", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class ProductApiController
{
    private final ProductApplicationQuery productApplicationQuery;

    public ProductApiController(ProductApplicationQuery productApplicationQuery)
    {
        this.productApplicationQuery = productApplicationQuery;
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<Product> getProductByExternalId(@RequestParam String externalId)
    {
        return productApplicationQuery.findProductByExternalId(externalId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
