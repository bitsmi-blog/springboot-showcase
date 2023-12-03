package com.bitsmi.springbootshowcase.core.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface ICustomBaseRepository<T, ID> extends JpaRepository<T, ID>
{
    Optional<T> findByField(String fieldName, Object value);
}
