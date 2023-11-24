package com.bitsmi.springbootshowcase.core.content.repository;

import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IItemSchemaRepository extends JpaRepository<ItemSchemaEntity, Long>
{
    Optional<ItemSchemaEntity> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);
}
