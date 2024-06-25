package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationPrincipalService
{
    Authentication getAuthentication();
    UserDetails getAuthenticationPrincipal();
}
