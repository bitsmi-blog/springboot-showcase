package com.bitsmi.springbootshowcase.infrastructure.content.mapper;

import com.bitsmi.springbootshowcase.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.Direction;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.NullHandling;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.Order;
import com.bitsmi.springshowcase.contentservice.client.common.response.PaginatedResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class ContentServiceClientPaginatedResponseMapper
{
    public <T, R> PaginatedData<R> fromPaginatedResponse(PaginatedResponse<T> page, Function<T, R> elementMapper)
    {
        return PaginatedData.<R>builder()
                .content(page.content()
                        .stream()
                        .map(elementMapper)
                        .toList()
                )
                .pageCount(page.pageCount())
                .pagination(mapPagination(page.pagination()))
                .totalCount(page.totalCount())
                .totalPages(page.totalPages())
                .build();
    }

    private Pagination mapPagination(com.bitsmi.springshowcase.contentservice.client.common.response.Pagination pagination)
    {
        return Pagination.of(
                pagination.pageNumber(),
                pagination.pageSize(),
                mapSort(pagination.sort())
        );
    }

    private Sort mapSort(com.bitsmi.springshowcase.contentservice.client.common.response.Sort sort)
    {
        if(sort==null) {
            return Sort.UNSORTED;
        }

        List<Order> orders = sort.orders()
                .stream()
                .map(this::mapOrder)
                .toList();

        return Sort.by(orders);
    }

    private Order mapOrder(com.bitsmi.springshowcase.contentservice.client.common.response.Sort.Order order)
    {
        Direction direction = switch (order.direction()) {
            case ASC -> Direction.ASC;
            case DESC -> Direction.DESC;
        };
        NullHandling nullHandling = switch (order.nullHandling()) {
            case NATIVE -> NullHandling.NATIVE;
            case NULLS_FIRST -> NullHandling.NULLS_FIRST;
            case NULLS_LAST -> NullHandling.NULLS_LAST;
        };

        return Order.builder()
                .direction(direction)
                .property(order.property())
                .ignoreCase(order.ignoreCase())
                .nullHandling(nullHandling)
                .build();
    }
}
