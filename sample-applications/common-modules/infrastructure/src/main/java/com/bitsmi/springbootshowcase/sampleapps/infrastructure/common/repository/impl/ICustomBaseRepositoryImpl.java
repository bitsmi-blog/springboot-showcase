package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.impl;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.ICustomBaseRepository;
import jakarta.persistence.EntityManager;
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
    public Optional<T> findByField(String fieldName, Object value)
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
