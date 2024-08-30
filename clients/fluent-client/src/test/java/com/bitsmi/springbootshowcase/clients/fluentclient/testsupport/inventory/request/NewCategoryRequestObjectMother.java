package com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.request;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.NewCategoryRequest;

public final class NewCategoryRequestObjectMother {

    private NewCategoryRequestObjectMother() { }

    public static NewCategoryRequest aCategory1CreateRequest() {
        return builder()
                .data(CategoryDataObjectMother.category1())
                .build();
    }

    public static NewCategoryRequest aCategory2CreateRequest() {
        return builder()
                .data(CategoryDataObjectMother.category2())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final NewCategoryRequest.Builder delegate = NewCategoryRequest.builder();

        public Builder data(CategoryData data)
        {
            delegate.data(data);
            return this;
        }

        public NewCategoryRequest build() {
            return delegate.build();
        }
    }
}
