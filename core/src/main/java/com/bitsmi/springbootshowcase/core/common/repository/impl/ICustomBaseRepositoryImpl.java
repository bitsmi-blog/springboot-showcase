package com.bitsmi.springbootshowcase.core.common.repository.impl;

import com.bitsmi.springbootshowcase.core.common.repository.ICustomBaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public class ICustomBaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements ICustomBaseRepository<T, ID>
{
    private final JpaEntityInformationSupport<T, ?> entityInformation;
    private final EntityManager entityManager;

    public ICustomBaseRepositoryImpl(JpaEntityInformationSupport<T, ID> entityInformation, EntityManager entityManager)
    {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findThroughExternalId(String externalId)
    {
        String queryString = QueryUtils.getQueryString("SELECT e FROM %s e WHERE e.externalId = ?1",
                this.entityInformation.getEntityName());

        T result = entityManager.createQuery(queryString, this.entityInformation.getJavaType())
                .setParameter(1, externalId)
                .getSingleResult();

        return Optional.ofNullable(result);
    }
}
