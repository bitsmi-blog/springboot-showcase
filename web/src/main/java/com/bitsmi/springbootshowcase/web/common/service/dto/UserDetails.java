package com.bitsmi.springbootshowcase.web.common.service.dto;

import com.bitsmi.springbootshowcase.core.common.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails
{
    private final User user;

    public UserDetails(final User user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return user.groups().stream()
                .flatMap(group -> group.authorities().stream())
                .map(auth -> "ROLE_" + auth.name())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword()
    {
        return user.password();
    }

    @Override
    public String getUsername()
    {
        return user.username();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return user.active();
    }
}
