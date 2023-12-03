package com.bitsmi.springbootshowcase.core.content.repository.impl;

import com.bitsmi.springbootshowcase.core.content.projection.IItemSchemaSummaryProjection;
import com.bitsmi.springbootshowcase.core.content.projection.ItemSchemaSummaryProjectionImpl;
import com.bitsmi.springbootshowcase.core.content.repository.ItemSchemaExtRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemSchemaExtRepositoryImpl implements ItemSchemaExtRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<IItemSchemaSummaryProjection> findSummaryProjectionUsingQueryByExternalId(final String externalId)
    {
        return entityManager.createQuery("""
                                SELECT s.externalId as externalId, s.name as name, f.name as fieldName
                                FROM ItemSchemaEntity s
                                    JOIN s.fields f
                                WHERE s.externalId = ?1
                                """,
                            Tuple.class)
                        .setParameter(1, externalId)
                        .unwrap(Query.class)
                        .setResultListTransformer(this::collectResults)
                        .getResultList()
                        .stream()
                        .findFirst();
    }

    private List<ItemSchemaSummaryProjectionImpl> collectResults(List<Tuple> resultList)
    {
        return resultList.stream()
                .collect(Collectors.groupingBy(tuple -> tuple.get("externalId")))
                .entrySet()
                .stream()
                .map(entry -> new ItemSchemaSummaryProjectionImpl((String) entry.getKey(),
                        (String) entry.getValue().get(0).get("name"),
                        Integer.valueOf(entry.getValue().size()).longValue()))
                .toList();
    }
}
