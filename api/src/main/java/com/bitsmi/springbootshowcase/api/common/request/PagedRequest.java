package com.bitsmi.springbootshowcase.api.common.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@Builder(toBuilder = true, builderClassName = "Builder")
public class PagedRequest
{
    protected int page;
    protected int pageSize;
    protected List<String> sort;

    public PagedRequest(int page, int pageSize, List<String> sort)
    {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public static PagedRequest of(int page, int pageSize)
    {
        return builder().page(page)
                .pageSize(pageSize)
                .sort(Collections.emptyList())
                .build();
    }

    public static PagedRequest of(int page, int pageSize, List<String> sort)
    {
        return builder().page(page)
                .pageSize(pageSize)
                .sort(sort)
                .build();
    }

    public static class Builder
    {
        public PagedRequest build()
        {
            return new PagedRequest(
                    page,
                    pageSize>0 ? pageSize : 5,
                    sort!=null ? sort : Collections.emptyList()
            );
        }
    }
}
