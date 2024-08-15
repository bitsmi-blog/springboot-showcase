package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Generated;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginatedRequest {
    protected @PositiveOrZero Integer page;
    protected @PositiveOrZero Integer pageSize;
    protected List<String> sort;

    public PaginatedRequest(Integer page, Integer pageSize, List<String> sort) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public static PaginatedRequest of(Integer page, Integer pageSize) {
        return builder().page(page).pageSize(pageSize).sort(Collections.emptyList()).build();
    }

    public static PaginatedRequest of(Integer page, Integer pageSize, List<String> sort) {
        return builder().page(page).pageSize(pageSize).sort(new ArrayList(sort)).build();
    }

    public PaginatedRequest withDefaults(Integer page, Integer pageSize) {
        return builder().page((Integer) ObjectUtils.defaultIfNull(this.page, page)).pageSize((Integer)ObjectUtils.defaultIfNull(this.pageSize, pageSize)).sort(this.sort).build();
    }

    @Generated
    public static Builder builder() {
        return new Builder();
    }

    @Generated
    public Builder toBuilder() {
        return (new Builder()).page(this.page).pageSize(this.pageSize).sort(this.sort);
    }

    @Generated
    public Integer getPage() {
        return this.page;
    }

    @Generated
    public Integer getPageSize() {
        return this.pageSize;
    }

    @Generated
    public List<String> getSort() {
        return this.sort;
    }

    public static class Builder {
        @Generated
        private Integer page;
        @Generated
        private Integer pageSize;
        @Generated
        private List<String> sort;

        public PaginatedRequest build() {
            return new PaginatedRequest(this.page, this.pageSize, (List)ObjectUtils.defaultIfNull(this.sort, Collections.emptyList()));
        }

        @Generated
        Builder() {
        }

        @Generated
        public Builder page(final Integer page) {
            this.page = page;
            return this;
        }

        @Generated
        public Builder pageSize(final Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        @Generated
        public Builder sort(final List<String> sort) {
            this.sort = sort;
            return this;
        }

        @Generated
        public String toString() {
            Integer var10000 = this.page;
            return "PaginatedRequest.Builder(page=" + var10000 + ", pageSize=" + this.pageSize + ", sort=" + String.valueOf(this.sort) + ")";
        }
    }
}
