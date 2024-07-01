package com.bitsmi.springbootshowcase.springcore.cache.web.controller;

import com.bitsmi.springbootshowcase.springcore.cache.application.inventory.ProductApplicationService;
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
    private final ProductApplicationService productApplicationService;

    public ProductApiController(ProductApplicationService productApplicationService)
    {
        this.productApplicationService = productApplicationService;
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<Product> getProductByExternalId(@RequestParam String externalId)
    {
        return productApplicationService.findProductByExternalId(externalId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
