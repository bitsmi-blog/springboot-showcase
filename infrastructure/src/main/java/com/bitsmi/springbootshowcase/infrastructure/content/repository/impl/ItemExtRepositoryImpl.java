package com.bitsmi.springbootshowcase.infrastructure.content.repository.impl;

import com.bitsmi.springbootshowcase.infrastructure.content.entity.ItemStatus;
import com.bitsmi.springbootshowcase.infrastructure.content.projection.IItemSummaryProjection;
import com.bitsmi.springbootshowcase.infrastructure.content.projection.ItemSummaryProjectionImpl;
import com.bitsmi.springbootshowcase.infrastructure.content.repository.ItemExtRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemExtRepositoryImpl implements ItemExtRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<IItemSummaryProjection> findSummaryProjectionUsingQueryByName(final String name)
    {
        return entityManager.createQuery("""
                                SELECT i.name as name,
                                    i.status as status,
                                    i.schemaExternalId as schemaExternalId,
                                    count(f) as fieldsCount
                                FROM ItemEntity i
                                    JOIN i.fields f
                                WHERE i.name = :name
                                """,
                        Tuple.class)
                .setParameter(1, name)
                .unwrap(Query.class)
                .setResultListTransformer(this::collectResults)
                .getResultList()
                .stream()
                .findFirst();
    }

    private List<ItemSummaryProjectionImpl> collectResults(List<Tuple> resultList)
    {
        return resultList.stream()
                .collect(Collectors.groupingBy(tuple -> tuple.get("externalId")))
                .entrySet()
                .stream()
                .map(entry -> new ItemSummaryProjectionImpl(
                        (String) entry.getKey(),
                        ItemStatus.valueOf((String)entry.getValue().getFirst().get("status")),
                        (String) entry.getValue().get(0).get("schemaExternalId"),
                        Integer.valueOf(entry.getValue().size()).longValue()
                ))
                .toList();
    }
}
