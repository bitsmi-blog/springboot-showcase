package com.bitsmi.springbootshowcase.springcore.cache.test.infrastructure.inventory;

import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.model.Product;
import com.bitsmi.springbootshowcase.springcore.cache.domain.inventory.ProductDomainRepository;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.repository.ProductRepository;
import com.bitsmi.springbootshowcase.springcore.cache.testsupport.infrastructure.inventory.entity.ProductEntityObjectMother;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
// Test-managed transaction should be rolled back after the test method has completed.
@Rollback
@EnableAutoConfiguration
//In-memory database (H2). Entity auto-scan
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Import({ ValidationAutoConfiguration.class })

@Tag("IntegrationTest")
class ProductDomainRepositoryIntTests
{
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductDomainRepository sut;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void tearDown()
    {
        // Clear caches so each test finds an empty cache
        cacheManager.getCacheNames()
                .forEach(name -> cacheManager.getCache(name).clear());
    }

    @Test
    @DisplayName("findProductByExternalId should cache results when executed multiple times for same provided externalId")
    void findProductByExternalIdCacheTest1()
    {
        final String providedProduct1ExternalId = ProductEntityObjectMother.EXTERNAL_ID_PRODUCT1;
        final String providedProduct2ExternalId = ProductEntityObjectMother.EXTERNAL_ID_PRODUCT2;
        final ProductEntity expectedProductEntity1 = ProductEntityObjectMother.product1();
        final ProductEntity expectedProductEntity2 = ProductEntityObjectMother.product2();

        when(productRepository.findByExternalId(providedProduct1ExternalId))
                .thenReturn(Optional.of(expectedProductEntity1));
        when(productRepository.findByExternalId(providedProduct2ExternalId))
                .thenReturn(Optional.of(expectedProductEntity2));

        Optional<Product> optResponseProduct1_1st = sut.findProductByExternalId(providedProduct1ExternalId);
        Optional<Product> optResponseProduct1_2nd = sut.findProductByExternalId(providedProduct1ExternalId);
        Optional<Product> optResponseProduct2 = sut.findProductByExternalId(providedProduct2ExternalId);

        assertThat(optResponseProduct1_1st).isPresent();
        assertThat(optResponseProduct1_2nd).isPresent();
        assertThat(optResponseProduct2).isPresent();

        // Only 1 call to the repository. The following data is retrieved from cache
        verify(productRepository).findByExternalId(providedProduct1ExternalId);
        // Non cached values should call repository
        verify(productRepository).findByExternalId(providedProduct2ExternalId);
    }

    @Test
    @DisplayName("updateProduct should evict cache given cached product when its externalId is provided")
    void findProductByExternalIdCacheTest2()
    {
        final Product providedProduct = Product.builder()
                .id(ProductEntityObjectMother.ID_PRODUCT1)
                .externalId(ProductEntityObjectMother.EXTERNAL_ID_PRODUCT1)
                .name("Updated product")
                .build();
        final ProductEntity expectedProductEntity1 = ProductEntityObjectMother.product1();

        when(productRepository.findByExternalId(providedProduct.externalId()))
                .thenReturn(Optional.of(expectedProductEntity1));
        when(productRepository.findById(providedProduct.id()))
                .thenReturn(Optional.of(expectedProductEntity1));

        Optional<Product> optResponseProduct1_1st = sut.findProductByExternalId(providedProduct.externalId());
        sut.updateProduct(providedProduct.id(), providedProduct);
        Optional<Product> optResponseProduct1_2nd = sut.findProductByExternalId(providedProduct.externalId());

        assertThat(optResponseProduct1_1st).isPresent();
        assertThat(optResponseProduct1_2nd).isPresent();

        verify(productRepository, times(2)).findByExternalId(providedProduct.externalId());
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