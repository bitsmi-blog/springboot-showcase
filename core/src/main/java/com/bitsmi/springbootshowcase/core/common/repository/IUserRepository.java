package com.bitsmi.springbootshowcase.core.common.repository;

import com.bitsmi.springbootshowcase.core.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
}
