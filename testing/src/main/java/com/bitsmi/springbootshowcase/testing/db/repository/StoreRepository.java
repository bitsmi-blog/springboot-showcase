package com.bitsmi.springbootshowcase.testing.db.repository;

import com.bitsmi.springbootshowcase.testing.db.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

}
