package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainQueryService;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service.dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserDomainQueryService userDomainQueryService;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return userDomainQueryService.findUserByUsername(username)
                .map(UserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
