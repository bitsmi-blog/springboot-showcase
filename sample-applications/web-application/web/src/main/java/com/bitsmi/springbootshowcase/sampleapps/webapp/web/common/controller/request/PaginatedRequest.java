package com.bitsmi.springbootshowcase.sampleapps.webapp.web.common.controller.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@Builder(toBuilder = true, builderClassName = "Builder")
public class PaginatedRequest
{
    protected int page;
    protected int pageSize;
    protected List<String> sort;

    public PaginatedRequest(int page, int pageSize, List<String> sort)
    {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public static PaginatedRequest of(int page, int pageSize)
    {
        return builder().page(page)
                .pageSize(pageSize)
                .sort(Collections.emptyList())
                .build();
    }

    public static PaginatedRequest of(int page, int pageSize, List<String> sort)
    {
        return builder().page(page)
                .pageSize(pageSize)
                .sort(sort)
                .build();
    }

    public static class Builder
    {
        public PaginatedRequest build()
        {
            return new PaginatedRequest(
                    page,
                    pageSize>0 ? pageSize : 5,
                    sort!=null ? sort : Collections.emptyList()
            );
        }
    }
}
