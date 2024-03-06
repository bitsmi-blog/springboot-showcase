package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import java.util.Optional;

public interface IExternalIdSupportRepository
{
    <T> Optional<T> findThroughExternalId(Class<T> resultType, String externalId);
}
