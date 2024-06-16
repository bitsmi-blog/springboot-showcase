package com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.request;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;

public class CategoryDataObjectMother
{
    public static final String EXTERNAL_ID_CATEGORY1 = "category-1";
    public static final String EXTERNAL_ID_CATEGORY2 = "category-2";

    public static CategoryData category1()
    {
        return builder()
                .category1()
                .build();
    }

    public static CategoryData category2()
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
        private final CategoryData.Builder delegate = CategoryData.builder();

        public Builder category1()
        {
            delegate
                    .externalId(EXTERNAL_ID_CATEGORY1)
                    .name("Test category 1");

            return this;
        }

        public Builder category2()
        {
            delegate.externalId(EXTERNAL_ID_CATEGORY2)
                    .name("Test category 2");

            return this;
        }

        public CategoryDataObjectMother.Builder externalId(String externalId)
        {
            delegate.externalId(externalId);
            return this;
        }

        public CategoryDataObjectMother.Builder name(String name)
        {
            delegate.name(name);
            return this;
        }

        public CategoryData build()
        {
            return delegate.build();
        }
    }
}
