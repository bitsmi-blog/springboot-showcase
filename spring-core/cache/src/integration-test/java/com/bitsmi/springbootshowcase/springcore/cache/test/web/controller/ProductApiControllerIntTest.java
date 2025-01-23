package com.bitsmi.springbootshowcase.springcore.cache.test.web.controller;

import com.bitsmi.springbootshowcase.springcore.cache.application.ApplicationModulePackage;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.InfrastructureModulePackage;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.entity.ProductEntity;
import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.inventory.repository.ProductRepository;
import com.bitsmi.springbootshowcase.springcore.cache.testsupport.infrastructure.inventory.entity.ProductEntityObjectMother;
import com.bitsmi.springbootshowcase.springcore.cache.web.WebModulePackage;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@Transactional
// Test-managed transaction should be rolled back after the test method has completed.
@Rollback
@EnableAutoConfiguration
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Import({ ValidationAutoConfiguration.class })
@AutoConfigureMockMvc

@Tag("IntegrationTest")
class ProductApiControllerIntTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    @DisplayName("get product by externalId should cache results given same externalId when in same request")
    void getProductByExternalIdMemoizeCacheTest1() throws Exception {
        final String givenProduct1ExternalId = ProductEntityObjectMother.EXTERNAL_ID_PRODUCT1;
        final ProductEntity expectedProductEntity1 = ProductEntityObjectMother.product1();

        when(productRepository.findByExternalId(givenProduct1ExternalId))
                .thenReturn(Optional.of(expectedProductEntity1));

        MvcResult actualResponse1 = this.mockMvc.perform(get("/api/inventory/product")
                    .param("externalId", givenProduct1ExternalId)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        MvcResult actualResponse2 = this.mockMvc.perform(get("/api/inventory/product")
                        .param("externalId", givenProduct1ExternalId)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(actualResponse1.getResponse().getContentAsString())
                .isNotBlank();
        assertThat(actualResponse2.getResponse().getContentAsString())
                .isNotBlank();

        /* Every request make 2 calls to the repository. As caches are cleared after the request finishes,
         * we have 1 call to the repository + 1 cache hit in every request
         */
        verify(productRepository, times(2)).findByExternalId(givenProduct1ExternalId);
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(
            basePackageClasses = {
                    ApplicationModulePackage.class,
                    WebModulePackage.class,
                    InfrastructureModulePackage.class
            },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
    )
    static class TestConfig {

    }
}
