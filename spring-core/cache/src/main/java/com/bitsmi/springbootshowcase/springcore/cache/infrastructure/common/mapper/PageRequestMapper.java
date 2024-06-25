package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.Sort;
import com.bitsmi.springbootshowcase.springcore.cache.domain.common.dto.Sort.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageRequestMapper
{
    public PageRequest fromPagination(Pagination pagination)
    {
        return PageRequest.of(
                pagination.pageNumber(),
                pagination.pageSize(),
                mapSort(pagination.sort())
        );
    }

    private org.springframework.data.domain.Sort mapSort(Sort sort)
    {
        if(sort==null) {
            return org.springframework.data.domain.Sort.unsorted();
        }

        List<org.springframework.data.domain.Sort.Order> orders = sort.orders().stream()
                .map(this::mapOrder)
                .toList();

        return org.springframework.data.domain.Sort.by(orders);
    }

    private org.springframework.data.domain.Sort.Order mapOrder(Order order)
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

        return new org.springframework.data.domain.Sort.Order(
                direction,
                order.property(),
                order.ignoreCase(),
                nullHandling
        );
    }
}
