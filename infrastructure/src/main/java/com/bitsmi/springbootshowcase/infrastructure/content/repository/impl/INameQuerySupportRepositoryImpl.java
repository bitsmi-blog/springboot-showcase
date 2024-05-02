package com.bitsmi.springbootshowcase.infrastructure.content.repository.impl;

import com.bitsmi.springbootshowcase.infrastructure.content.repository.INameQuerySupportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import java.util.Optional;

public class INameQuerySupportRepositoryImpl implements INameQuerySupportRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> Optional<T> findThroughName(Class<T> resultType, String name)
    {
        JpaEntityInformation<T, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(resultType, entityManager);

        String queryString = QueryUtils.getQueryString("SELECT e FROM %s e WHERE e.name = ?1",
                entityInformation.getEntityName());

        T result = entityManager.createQuery(queryString, resultType)
                .setParameter(1, name)
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}
