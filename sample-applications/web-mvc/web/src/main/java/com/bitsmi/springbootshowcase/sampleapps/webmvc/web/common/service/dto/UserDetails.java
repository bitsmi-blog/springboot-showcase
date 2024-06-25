package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service.dto;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.Authority;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import org.apache.commons.collections4.SetUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
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
        Set<GrantedAuthority> roles = user.groups().stream()
                .map(group -> "ROLE_" + group.name())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        Set<GrantedAuthority> authorities = user.groups().stream()
                .flatMap(group -> group.authorities().stream())
                .map(Authority::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return SetUtils.union(roles, authorities);
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
