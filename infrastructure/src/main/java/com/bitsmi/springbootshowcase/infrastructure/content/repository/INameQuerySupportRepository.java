package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import java.util.Optional;


public interface INameQuerySupportRepository
{
    <T> Optional<T> findThroughName(Class<T> resultType, String externalId);
}
