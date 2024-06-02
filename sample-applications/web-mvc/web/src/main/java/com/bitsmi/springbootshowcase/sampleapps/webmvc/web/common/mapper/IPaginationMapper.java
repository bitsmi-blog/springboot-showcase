package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.mapper;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort.Direction;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.request.PaginatedRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response.Sort;
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
    com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination fromRequest(PaginatedRequest request);

    @Named("MapRequestSortToDomainSort")
    default com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort mapRequestSortToDomainSort(List<String> sort)
    {
        if(CollectionUtils.isEmpty(sort)) {
            return com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort.UNSORTED;
        }

        List<com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort.Order> orders = sort.stream()
                .map(this::parseDomainSortOrder)
                .toList();

        return com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort.by(orders);
    }

    private com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort.Order parseDomainSortOrder(String sort)
    {
        String[] sortParts = sort.split(",");
        if(sortParts.length == 0) {
            throw new IllegalArgumentException("Invalid sort statement (%s)".formatted(sort));
        }

        Direction direction = Direction.ASC;
        if(sortParts.length > 1) {
            direction = Direction.valueOf(sortParts[1]);
        }

        return com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort.Order.builder()
                .property(sortParts[0])
                .direction(direction)
                .build();
    }

    @Mapping(target = "sort", defaultExpression = "java(com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response.Sort.UNSORTED)")
    Pagination fromDomain(com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination domain);

    Sort fromDomain(com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort domain);
}
