package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository;

import java.util.Optional;

public interface ExternalIdQuerySupportRepository
{
    <T> Optional<T> findByExternalId(Class<T> resultType, String externalId);
}
