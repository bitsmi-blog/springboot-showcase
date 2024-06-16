package com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.request;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.ProductData;

public class ProductDataObjectMother
{
    public static final String EXTERNAL_ID_PRODUCT1 = "product-1";
    public static final String EXTERNAL_ID_PRODUCT2 = "product-2";

    public static ProductData product1()
    {
        return builder()
                .product1()
                .build();
    }

    public static ProductData product2()
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
        private final ProductData.Builder delegate = ProductData.builder();

        public Builder product1()
        {
            delegate
                    .externalId(EXTERNAL_ID_PRODUCT1)
                    .name("Test product 1");

            return this;
        }

        public Builder product2()
        {
            delegate.externalId(EXTERNAL_ID_PRODUCT2)
                    .name("Test product 2");

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

        public ProductData build()
        {
            return delegate.build();
        }
    }
}
