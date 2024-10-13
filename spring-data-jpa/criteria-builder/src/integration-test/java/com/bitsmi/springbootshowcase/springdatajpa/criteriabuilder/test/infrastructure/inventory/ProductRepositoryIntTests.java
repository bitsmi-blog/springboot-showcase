package com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.test.infrastructure.inventory;

import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.repository.ProductRepository;
import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.testsupport.internal.RepositoryIntegrationTest;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.repository.ProductSpecificationFactory.createdOnOrBefore;
import static com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.repository.ProductSpecificationFactory.hasExternalId;
import static com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.repository.ProductSpecificationFactory.nameContains;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.jpa.domain.Specification.where;

@RepositoryIntegrationTest
@TestPropertySource(
    properties = {
            "spring.liquibase.change-log=classpath:db/changelogs/test/infrastructure/inventory/product_repository_tests_main.xml",
            "spring.jpa.show-sql=true",
            "spring.jpa.properties.hibernate.format_sql=true"
    }
)
class ProductRepositoryIntTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("findAll should return filtered products when a externalId predicate is provided")
    void findBySimplePredicateTest1() {
        List<ProductEntity> products = productRepository.findAll(hasExternalId("product-1.1"));

        assertThat(products).hasSize(1);
    }

    @Test
    @DisplayName("findAll should return filtered products when a creation date filter is provided given a future date")
    void findBySimplePredicateTest2() {
        List<ProductEntity> products = productRepository.findAll(createdOnOrBefore(LocalDateTime.now()));

        assertThat(products).hasSize(5);
    }

    @Test
    @DisplayName("findAll should return filtered products when a creation date filter is provided given a past date")
    void findBySimplePredicateTest3() {
        List<ProductEntity> products = productRepository.findAll(createdOnOrBefore(LocalDateTime.of(2020, 1, 1, 0, 0)));

        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("findAll should return filtered products when name like and creation date 'AND' filters are provided")
    void findByMultiplePredicateTest1() {
        List<ProductEntity> products = productRepository.findAll(
            where(nameContains("Product 1"))
            .and(createdOnOrBefore(LocalDateTime.of(2024, 1, 1, 0, 0)))
        );

        assertThat(products).hasSize(1);
    }

    @Test
    @DisplayName("findAll should return filtered products when a name like and creation date 'OR' filters are provided")
    void findByMultiplePredicateTest2() {
        List<ProductEntity> products = productRepository.findAll(
                where(nameContains("Product 1")
                        .or(nameContains("Product 2"))
                )
                .and(createdOnOrBefore(LocalDateTime.of(2024, 1, 1, 0, 0)))
        );

        assertThat(products).hasSize(2);
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ InfrastructureModuleConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig {

    }
}
