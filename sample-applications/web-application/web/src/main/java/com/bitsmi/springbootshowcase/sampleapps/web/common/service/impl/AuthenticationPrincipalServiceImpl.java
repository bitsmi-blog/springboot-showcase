package com.bitsmi.springbootshowcase.sampleapps.web.common.service.impl;

import com.bitsmi.springbootshowcase.sampleapps.web.common.service.IAuthenticationPrincipalService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPrincipalServiceImpl implements IAuthenticationPrincipalService
{
    @Override
    public Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetails getAuthenticationPrincipal()
    {
        return (UserDetails) getAuthentication().getPrincipal();
    }
}
