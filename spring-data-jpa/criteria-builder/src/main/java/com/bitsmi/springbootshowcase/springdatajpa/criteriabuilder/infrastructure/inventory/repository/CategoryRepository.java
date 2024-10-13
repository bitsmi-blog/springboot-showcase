package com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.repository;

import com.bitsmi.springbootshowcase.springdatajpa.criteriabuilder.infrastructure.inventory.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
