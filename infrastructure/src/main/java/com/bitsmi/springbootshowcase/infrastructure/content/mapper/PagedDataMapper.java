package com.bitsmi.springbootshowcase.infrastructure.content.mapper;

import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.Direction;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.NullHandling;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PagedDataMapper
{
    public <T, R> PagedData<R> fromPage(Page<T> page, Function<T, R> elementMapper)
    {
        return PagedData.<R>builder()
                .content(page.getContent()
                        .stream()
                        .map(elementMapper)
                        .toList()
                )
                .pageCount(page.getNumberOfElements())
                .pagination(mapPagination(page.getPageable()))
                .totalCount(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    private Pagination mapPagination(Pageable pageable)
    {
        return Pagination.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                mapSort(pageable.getSort())
        );
    }

    private Sort mapSort(org.springframework.data.domain.Sort sort)
    {
        if(sort==null) {
            return Sort.UNSORTED;
        }

        List<Order> orders = sort.stream()
                .map(this::mapOrder)
                .toList();

        return Sort.by(orders);
    }

    private Order mapOrder(org.springframework.data.domain.Sort.Order order)
    {
        Direction direction = switch (order.getDirection()) {
            case ASC -> Direction.ASC;
            case DESC -> Direction.DESC;
        };
        NullHandling nullHandling = switch (order.getNullHandling()) {
            case NATIVE -> NullHandling.NATIVE;
            case NULLS_FIRST -> NullHandling.NULLS_FIRST;
            case NULLS_LAST -> NullHandling.NULLS_LAST;
        };

        return Order.builder()
                .direction(direction)
                .property(order.getProperty())
                .ignoreCase(order.isIgnoreCase())
                .nullHandling(nullHandling)
                .build();
    }
}
