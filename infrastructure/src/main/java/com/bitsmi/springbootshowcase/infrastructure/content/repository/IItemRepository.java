package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import com.bitsmi.springbootshowcase.infrastructure.common.repository.ICustomBaseRepository;
import com.bitsmi.springbootshowcase.infrastructure.content.entity.ItemEntity;
import com.bitsmi.springbootshowcase.infrastructure.content.projection.IItemSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IItemRepository extends ICustomBaseRepository<ItemEntity, Long>, ItemExtRepository, INameQuerySupportRepository
{
    /**
     * name like 'namePrefix%'
     */
    Page<ItemEntity> findByNameStartsWithIgnoreCase(String namePrefix, Pageable pageable);
    /**
     * name like '%namePrefix'
     */
    Page<ItemEntity> findByNameEndsWithIgnoreCase(String nameSuffix, Pageable pageable);
    /**
     * name like '%name%'
     */
    Page<ItemEntity> findByNameContainsIgnoreCase(String name, Pageable pageable);
    /**
     * Same as previous but we have to put the wildcard in the expression where required to build a starts, ends or contains filter (E.G. expression = "%foo%")
     */
    Page<ItemEntity> findByNameLikeIgnoreCase(String expression, Pageable pageable);

    Optional<ItemEntity> findByName(String name);

    boolean existsByName(String name);

    /**
     * Using "Through" variant to not collide with standard "By"
     */
    default Optional<ItemEntity> findThroughName(String name)
    {
        return this.findThroughName(ItemEntity.class, name);
    }

    @Query("""
            SELECT i.name as name,
                i.status as status,
                i.schemaExternalId as schemaExternalId,
                count(f) as fieldsCount
            FROM ItemEntity i
                JOIN i.fields f
            WHERE i.name = :name
            """)
    Optional<IItemSummaryProjection> findSummaryProjectionByName(@Param("name") String name);
}
