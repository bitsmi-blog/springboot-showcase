package com.bitsmi.springbootshowcase.web.common.service.impl;

import com.bitsmi.springbootshowcase.web.common.service.dto.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return new UserDetails();
    }
}
