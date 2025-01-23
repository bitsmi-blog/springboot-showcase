package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.test.infrastructure.inventory;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.InfrastructurePackage;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductStockProjection;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductSummaryProjection;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository.ProductRepository;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
// Test-managed transaction should be rolled back after the test method has completed.
@Rollback
@EnableAutoConfiguration
@AutoConfigureCache
@AutoConfigureDataJpa
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(
        properties = {
                "spring.liquibase.change-log=classpath:db/changelogs/test/infrastructure/inventory/product_repository_tests_main.xml",
                "spring.datasource.url = jdbc:tc:postgresql:16.0:///test-database",
                "spring.datasource.driver-class-name = org.testcontainers.jdbc.ContainerDatabaseDriver"
        }
)
@org.junit.jupiter.api.Tag("IntegrationTest")
class ProductRepositoryTestContainersTests
{
    // Singleton container. Don't start at every test using @Container annotation
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag("16.0"));

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    static void setUpAll()
    {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag("16.0"));
        postgres.start();
    }

    @AfterAll
    static void tearDownAll()
    {
        postgres.stop();
    }

    @Test
    @DisplayName("findAll should return all products")
    void findAllTest1()
    {
        List<ProductEntity> products = productRepository.findAll();

        assertThat(products).hasSize(5);
    }

    @Test
    @DisplayName("findByField should return corresponding products given existing products when their field value is provided")
    void findByFieldTest1()
    {
        List<ProductEntity> actualProducts = productRepository.findByField("externalId", "product-1.1");

        assertThat(actualProducts).hasSize(1)
                .first()
                .satisfies(product -> {
                    assertThat(product.getId()).isEqualTo(1001L);
                    assertThat(product.getExternalId()).isEqualTo("product-1.1");
                    assertThat(product.getName()).isEqualTo("Product 1.1");
                });
    }

    @Test
    @DisplayName("findByField should return unique product given an existing product when its field value is provided")
    void findUniqueByFieldTest1()
    {
        Optional<ProductEntity> actualProduct = productRepository.findUniqueByField("externalId", "product-1.1");

        assertThat(actualProduct).isPresent()
                .get()
                .satisfies(product -> {
                    assertThat(product.getId()).isEqualTo(1001L);
                    assertThat(product.getExternalId()).isEqualTo("product-1.1");
                    assertThat(product.getName()).isEqualTo("Product 1.1");
                });
    }

    @Test
    @DisplayName("findStockProjectionByExternalId should return product stock given an existing product when its externalId is provided")
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
    @DisplayName("findSummaryProjectionByExternalId should return product summary given an existing product when its externalId is provided")
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
    @ComponentScan(
            basePackageClasses = { InfrastructurePackage.class },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
    )
    static class TestConfig
    {

    }
}
