package com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.response;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Product;

public class ProductObjectMother
{
    public static final Long ID_PRODUCT1 = 1001L;
    public static final Long ID_PRODUCT2 = 1002L;
    public static final String EXTERNAL_ID_PRODUCT1 = "product-1";
    public static final String EXTERNAL_ID_PRODUCT2 = "product-2";

    public static Product product1()
    {
        return builder()
                .product1()
                .build();
    }

    public static Product product2()
    {
        return builder()
                .product2()
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final Product.Builder delegate = Product.builder();

        public Builder product1()
        {
            delegate.id(ID_PRODUCT1)
                    .externalId(EXTERNAL_ID_PRODUCT1)
                    .name("Test product 1");

            return this;
        }

        public Builder product2()
        {
            delegate.id(ID_PRODUCT2)
                    .externalId(EXTERNAL_ID_PRODUCT2)
                    .name("Test product 2");

            return this;
        }

        public Builder id(Long id)
        {
            delegate.id(id);
            return this;
        }

        public Builder externalId(String externalId)
        {
            delegate.externalId(externalId);
            return this;
        }

        public Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public Product build()
        {
            return delegate.build();
        }
    }
}
