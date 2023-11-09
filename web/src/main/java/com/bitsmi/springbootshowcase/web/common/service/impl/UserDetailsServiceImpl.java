package com.bitsmi.springbootshowcase.web.common.service.impl;

import com.bitsmi.springbootshowcase.web.common.service.dto.UserDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private Map<String, UserDetails> users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init()
    {
        this.users = Map.ofEntries(
            entry("john.doe", new UserDetails("john.doe", passwordEncoder.encode("test")))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
       return Optional.ofNullable(users.get(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
