package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.test.infrastructure.inventory;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductStockProjection;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductSummaryProjection;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository.ProductRepository;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.testsupport.internal.RepositoryIntegrationTest;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.util.IgnoreOnComponentScan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIntegrationTest
@TestPropertySource(
    properties = {
        "spring.liquibase.change-log=classpath:db/changelogs/test/infrastructure/inventory/product_repository_tests_main.xml"
    }
)
class ProductRepositoryIntTests
{
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("findAll should return all products")
    void findAllTest1()
    {
        List<ProductEntity> products = productRepository.findAll();

        assertThat(products).hasSize(5);
    }

    @Test
    @DisplayName("findByField should return corresponding products given field value")
    void findByFieldTest1()
    {
        List<ProductEntity> products = productRepository.findByField("externalId", "product-1.1");

        assertThat(products).hasSize(1)
                .first()
                .satisfies(product -> {
                    assertThat(product.getId()).isEqualTo(1001L);
                    assertThat(product.getExternalId()).isEqualTo("product-1.1");
                    assertThat(product.getName()).isEqualTo("Product 1.1");
                });
    }

    @Test
    @DisplayName("findByField should return corresponding product given field value")
    void findUniqueByFieldTest1()
    {
        Optional<ProductEntity> products = productRepository.findUniqueByField("externalId", "product-1.1");

        assertThat(products).isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getId()).isEqualTo(1001L);
                    assertThat(product.getExternalId()).isEqualTo("product-1.1");
                    assertThat(product.getName()).isEqualTo("Product 1.1");
                });
    }

    @Test
    @DisplayName("findStockProjectionByExternalId should return product stock given an existing product externalId")
    void findStockProjectionByExternalIdTest1()
    {
        Optional<ProductStockProjection> actualProduct = productRepository.findStockProjectionByExternalId("product-1.1");

        assertThat(actualProduct).isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getExternalId()).isEqualTo("product-1.1");
                    assertThat(product.getName()).isEqualTo("Product 1.1");
                    assertThat(product.getTotalStock()).isEqualTo(300);
                });
    }

    @Test
    @DisplayName("findSummaryProjectionByExternalId should return product summary given an existing product externalId")
    void findSummaryProjectionByExternalIdTest1()
    {
        Optional<ProductSummaryProjection> actualProduct = productRepository.findSummaryProjectionByExternalId("product-1.1");

        assertThat(actualProduct).isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getExternalId()).isEqualTo("product-1.1");
                    assertThat(product.getName()).isEqualTo("Product 1.1");
                    assertThat(product.getCategoryName()).isEqualTo("Category 1");
                });
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ InfrastructureModuleConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }
}
