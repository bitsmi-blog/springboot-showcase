package com.bitsmi.springbootshowcase.infrastructure.content.repository.impl;

import com.bitsmi.springbootshowcase.infrastructure.content.repository.IExternalIdSupportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import java.util.Optional;

public class IExternalIdSupportRepositoryImpl implements IExternalIdSupportRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> Optional<T> findThroughExternalId(Class<T> resultType, String externalId)
    {
        JpaEntityInformation<T, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(resultType, entityManager);

        String queryString = QueryUtils.getQueryString("SELECT e FROM %s e WHERE e.externalId = ?1",
                entityInformation.getEntityName());

        T result = entityManager.createQuery(queryString, resultType)
                .setParameter(1, externalId)
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}
