package com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public interface ProductSpecificationFactory {

    static Specification<ProductEntity> hasExternalId(String externalId) {
        return (category, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(category.get("externalId"), externalId);
    }

    static Specification<ProductEntity> nameContains(String title) {
        return (category, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(category.get("name"), MessageFormat.format("%{0}%", title));
    }

    static Specification<ProductEntity> createdOnOrBefore(LocalDateTime dateTime) {
        return (category, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(category.get("creationDate"), dateTime);
    }
}
