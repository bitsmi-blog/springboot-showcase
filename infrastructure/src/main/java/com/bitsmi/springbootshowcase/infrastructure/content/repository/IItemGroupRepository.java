package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import com.bitsmi.springbootshowcase.infrastructure.content.entity.ItemGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemGroupRepository extends JpaRepository<ItemGroupEntity, Long>
{

}
