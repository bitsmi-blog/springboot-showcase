package com.bitsmi.springbootshowcase.web.common.service.impl;

import com.bitsmi.springbootshowcase.domain.common.spi.IUserPersistenceService;
import com.bitsmi.springbootshowcase.web.common.service.dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    /* TODO User UserQueryService from Domain
     */
    @Autowired
    private IUserPersistenceService userPersistenceService;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return userPersistenceService.findUserByUsername(username)
                .map(UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
