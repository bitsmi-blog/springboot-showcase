package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.impl;

import com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository.CustomBaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public class CustomBaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomBaseRepository<T, ID>
{
    private final JpaEntityInformationSupport<T, ?> entityInformation;
    private final EntityManager entityManager;

    public CustomBaseRepositoryImpl(JpaEntityInformationSupport<T, ID> entityInformation, EntityManager entityManager)
    {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findByField(String fieldName, Object value)
    {
        String queryString = String.format("SELECT e FROM %s e WHERE e.%s = ?1",
                this.entityInformation.getEntityName(),
                fieldName);

        List<T> results = entityManager.createQuery(queryString, this.entityInformation.getJavaType())
                .setParameter(1, value)
                .getResultList();

        return results;
    }

    @Override
    public Optional<T> findUniqueByField(String fieldName, Object value)
    {
        String queryString = String.format("SELECT e FROM %s e WHERE e.%s = ?1",
                this.entityInformation.getEntityName(),
                fieldName);

        T result = entityManager.createQuery(queryString, this.entityInformation.getJavaType())
                .setParameter(1, value)
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}
