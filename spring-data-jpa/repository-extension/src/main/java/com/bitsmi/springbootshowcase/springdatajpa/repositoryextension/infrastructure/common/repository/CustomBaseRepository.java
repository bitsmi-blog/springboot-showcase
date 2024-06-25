package com.bitsmi.springbootshowcase.springdatajpa.repositoryextension.infrastructure.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomBaseRepository<T, ID> extends JpaRepository<T, ID>
{
    List<T> findByField(String fieldName, Object value);

    Optional<T> findUniqueByField(String fieldName, Object value);
}
