package com.bitsmi.springbootshowcase.core.content.repository;

import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IItemSchemaRepository extends JpaRepository<ItemSchemaEntity, Long>
{
    /**
     * name like 'namePrefix%'
     */
    Page<ItemSchemaEntity> findByNameStartsWithIgnoreCase(String namePrefix, Pageable pageable);
    /**
     * name like '%namePrefix'
     */
    Page<ItemSchemaEntity> findByNameEndsWithIgnoreCase(String nameSuffix, Pageable pageable);
    /**
     * name like '%name%'
     */
    Page<ItemSchemaEntity> findByNameContainsIgnoreCase(String name, Pageable pageable);
    /**
     * Same as previous but we have to put the wildcard in the expression where required to build a starts, ends or contains filter (E.G. expression = "%foo%")
     */
    Page<ItemSchemaEntity> findByNameLikeIgnoreCase(String expression, Pageable pageable);

    Optional<ItemSchemaEntity> findByExternalId(String externalId);

    Optional<ItemSchemaEntity> findByName(String name);

    boolean existsByExternalId(String externalId);
}
