package com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.queries.infrastructure.inventory.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class ProductSpecificationFactory {

    public static Specification<ProductEntity> hasExternalId(String externalId) {
        return (category, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(category.get("externalId"), externalId);
    }

    public static Specification<ProductEntity> nameContains(String title) {
        return (category, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(category.get("name"), MessageFormat.format("%{0}%", title));
    }

    public static Specification<ProductEntity> createdOnOrBefore(LocalDateTime dateTime) {
        return (category, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(category.get("creationDate"), dateTime);
    }
}
