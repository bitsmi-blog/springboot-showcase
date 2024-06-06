package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository.impl;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.ProductStockProjection;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.projection.impl.ProductStockProjectionImpl;
import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.inventory.repository.ProductStockProjectionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductStockProjectionRepositoryImpl implements ProductStockProjectionRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<ProductStockProjection> findStockProjectionByExternalId(String externalId)
    {
        /* This query can be simplified grouping and summing stock quantities by product,
         * but we are using like this in order to demonstrate the collection process done in `collectResults` method
         */
        return entityManager.createQuery("""
                                SELECT p.name as name,
                                    p.externalId as externalId,
                                    c.name as categoryName,
                                    t.externalId as storeExternalId,
                                    t.name as storeName,
                                    s.quantity as storeStock
                                FROM ProductEntity p
                                    JOIN p.category c
                                    JOIN StockEntity s ON s.product = p
                                    JOIN s.store t
                                WHERE p.externalId = :externalId
                                """,
                        Tuple.class)
                .setParameter("externalId", externalId)
                .unwrap(Query.class)
                .setResultListTransformer(this::collectResults)
                .getResultList()
                .stream()
                .findFirst();
    }

    private List<ProductStockProjectionImpl> collectResults(List<Tuple> resultList)
    {
        return resultList.stream()
                .collect(Collectors.groupingBy(tuple -> tuple.get("externalId")))
                .entrySet()
                .stream()
                .map(entry -> new ProductStockProjectionImpl(
                        (String) entry.getValue().get(0).get("externalId"),
                        (String) entry.getValue().get(0).get("name"),
                        entry.getValue()
                                .stream()
                                .collect(Collectors.summingInt(v -> (Integer) v.get("storeStock")))
                    )
                )
                .toList();
    }
}
