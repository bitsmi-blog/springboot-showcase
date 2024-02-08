package com.bitsmi.springbootshowcase.web.common.service.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * TODO
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails
{
    public UserDetails()
    {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.emptyList();
    }

    @Override
    public String getPassword()
    {
        // test
        return "{bcrypt}$2a$10$I2NL2EwOcbl9PIhgPiF/XeVXQ.yfErH11UQemNW21LBk.iaIpa44.";
    }

    @Override
    public String getUsername()
    {
        return "admin";
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
        return true;
    }
}
