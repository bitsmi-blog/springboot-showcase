package com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.request;

import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.UpdateCategoryRequest;

public final class UpdateCategoryRequestObjectMother {

    private UpdateCategoryRequestObjectMother() { }

    public static UpdateCategoryRequest aCategory1UpdateRequest() {
        return builder()
                .data(CategoryDataObjectMother.category1())
                .build();
    }

    public static UpdateCategoryRequest aCategory2UpdateRequest() {
        return builder()
                .data(CategoryDataObjectMother.category2())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final UpdateCategoryRequest.Builder delegate = UpdateCategoryRequest.builder();

        public Builder data(CategoryData data)
        {
            delegate.data(data);
            return this;
        }

        public UpdateCategoryRequest build() {
            return delegate.build();
        }
    }
}
