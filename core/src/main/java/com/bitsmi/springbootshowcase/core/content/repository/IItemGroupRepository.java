package com.bitsmi.springbootshowcase.core.content.repository;

import com.bitsmi.springbootshowcase.core.content.entity.ItemGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemGroupRepository extends JpaRepository<ItemGroupEntity, Long>
{

}
