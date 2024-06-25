package com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.response;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.response.ProductObjectMother.Builder;

public class CategoryObjectMother
{
    public static final Long ID_CATEGORY1 = 1001L;
    public static final Long ID_CATEGORY2 = 1002L;
    public static final String EXTERNAL_ID_CATEGORY1 = "category-1";
    public static final String EXTERNAL_ID_CATEGORY2 = "category-2";

    public static Category category1()
    {
        return builder()
                .category1()
                .build();
    }

    public static Category category2()
    {
        return builder()
                .category2()
                .build();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private final Category.Builder delegate = Category.builder();

        public Builder category1()
        {
            delegate.id(ID_CATEGORY1)
                    .externalId(EXTERNAL_ID_CATEGORY1)
                    .name("Test category 1");

            return this;
        }

        public Builder category2()
        {
            delegate.id(ID_CATEGORY2)
                    .externalId(EXTERNAL_ID_CATEGORY2)
                    .name("Test category 2");

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

        public Category build()
        {
            return delegate.build();
        }
    }
}
