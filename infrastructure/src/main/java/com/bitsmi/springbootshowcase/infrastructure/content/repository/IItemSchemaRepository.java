package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import com.bitsmi.springbootshowcase.infrastructure.common.repository.ICustomBaseRepository;
import com.bitsmi.springbootshowcase.infrastructure.content.entity.ItemSchemaEntity;
import com.bitsmi.springbootshowcase.infrastructure.content.projection.IItemSchemaSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IItemSchemaRepository extends ICustomBaseRepository<ItemSchemaEntity, Long>, IExternalIdSupportRepository, ItemSchemaExtRepository
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

    Optional<ItemSchemaEntity> findByName(String name);

    default Optional<ItemSchemaEntity> findThroughExternalId(String externalId)
    {
        return this.findThroughExternalId(ItemSchemaEntity.class, externalId);
    }

    @Query("""
            SELECT s.externalId as externalId, s.name as name, count(f) as fieldsCount
            FROM ItemSchemaEntity s
                JOIN s.fields f
            WHERE s.externalId = :externalId
            """)
    Optional<IItemSchemaSummaryProjection> findSummaryProjectionByExternalId(@Param("externalId") String externalId);

    boolean existsByExternalId(String externalId);
}
