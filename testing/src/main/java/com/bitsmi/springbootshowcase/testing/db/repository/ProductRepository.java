package com.bitsmi.springbootshowcase.testing.db.repository;

import com.bitsmi.springbootshowcase.testing.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
