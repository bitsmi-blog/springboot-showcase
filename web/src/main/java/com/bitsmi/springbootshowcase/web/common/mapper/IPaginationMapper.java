package com.bitsmi.springbootshowcase.web.common.mapper;

import com.bitsmi.springbootshowcase.api.common.request.PaginatedRequest;
import com.bitsmi.springbootshowcase.api.common.response.Pagination;
import com.bitsmi.springbootshowcase.api.common.response.Sort;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.Direction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper(componentModel = "spring",
        implementationName = "PaginationMapperImpl"
)
public interface IPaginationMapper
{
    @Mapping(target = "pageNumber", source = "page")
    @Mapping(target = "sort", qualifiedByName = "MapRequestSortToDomainSort")
    com.bitsmi.springbootshowcase.domain.common.dto.Pagination fromRequest(PaginatedRequest request);

    @Named("MapRequestSortToDomainSort")
    default com.bitsmi.springbootshowcase.domain.common.dto.Sort mapRequestSortToDomainSort(List<String> sort)
    {
        if(CollectionUtils.isEmpty(sort)) {
            return com.bitsmi.springbootshowcase.domain.common.dto.Sort.UNSORTED;
        }

        List<com.bitsmi.springbootshowcase.domain.common.dto.Sort.Order> orders = sort.stream()
                .map(this::parseDomainSortOrder)
                .toList();

        return com.bitsmi.springbootshowcase.domain.common.dto.Sort.by(orders);
    }

    private com.bitsmi.springbootshowcase.domain.common.dto.Sort.Order parseDomainSortOrder(String sort)
    {
        String[] sortParts = sort.split(",");
        if(sortParts.length == 0) {
            throw new IllegalArgumentException("Invalid sort statement (%s)".formatted(sort));
        }

        Direction direction = Direction.ASC;
        if(sortParts.length > 1) {
            direction = Direction.valueOf(sortParts[1]);
        }

        return com.bitsmi.springbootshowcase.domain.common.dto.Sort.Order.builder()
                .property(sortParts[0])
                .direction(direction)
                .build();
    }

    @Mapping(target = "sort", defaultExpression = "java(com.bitsmi.springbootshowcase.api.common.response.Sort.UNSORTED)")
    Pagination fromDomain(com.bitsmi.springbootshowcase.domain.common.dto.Pagination domain);

    Sort fromDomain(com.bitsmi.springbootshowcase.domain.common.dto.Sort domain);
}
