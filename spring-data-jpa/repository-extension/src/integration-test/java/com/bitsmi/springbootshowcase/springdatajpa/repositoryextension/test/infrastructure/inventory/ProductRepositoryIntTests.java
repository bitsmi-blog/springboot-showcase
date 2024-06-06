package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.test.infrastructure.inventory;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository.IProductRepository;
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
    private IProductRepository productRepository;

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

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ InfrastructureModuleConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }
}
