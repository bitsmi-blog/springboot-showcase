package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserQueriesDomainService;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service.dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserQueriesDomainService userQueriesDomainService;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return userQueriesDomainService.findUserByUsername(username)
                .map(UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
