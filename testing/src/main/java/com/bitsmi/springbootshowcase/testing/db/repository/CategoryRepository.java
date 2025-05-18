package com.bitsmi.springbootshowcase.testing.db.repository;

import com.bitsmi.springbootshowcase.testing.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
