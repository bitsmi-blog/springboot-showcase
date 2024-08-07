package com.bitsmi.springbootshowcase.testing.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.bitsmi.springbootshowcase.testing.Product;
import com.bitsmi.springbootshowcase.testing.testsupport.ProductObjectMother;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstancioTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstancioTests.class);

    @Test
    @DisplayName("ObjectMother random value using Instancio")
    void test1()
    {
        Product expectedProduct = ProductObjectMother.aRandomProduct();

        LOGGER.info(expectedProduct.toString());
        assertThat(expectedProduct)
            .isNotNull()
            .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("ObjectMother random value using Instancio Model")
    void test2()
    {
        Product expectedProduct = ProductObjectMother.aRandomProductFromModel();
        LOGGER.info(expectedProduct.toString());
        assertThat(expectedProduct)
            .isNotNull()
            .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("ObjectMother random list using Instancio")
    void test3()
    {
        List<Product> expectedProducts = ProductObjectMother.aListOfRandomProducts(10);
        LOGGER.info(expectedProducts.toString());
        assertThat(expectedProducts)
            .isNotEmpty()
            .allSatisfy(elem -> assertThat(elem).hasNoNullFieldsOrProperties());
    }

    @Test
    @DisplayName("ObjectMother random value using String feed")
    void test4()
    {
        Product expectedProduct = ProductObjectMother.aRandomProductFromTestValues();
        LOGGER.info(expectedProduct.toString());
        assertThat(expectedProduct)
            .isNotNull()
            .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("ObjectMother tagged value using String feed")
    void test5()
    {
        Product expectedProduct = ProductObjectMother.aTaggedProductFromTestValues("TEST_PRODUCT");
        LOGGER.info(expectedProduct.toString());
        assertThat(expectedProduct)
            .isNotNull()
            .hasNoNullFieldsOrProperties();
    }
}
