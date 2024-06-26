package com.bitsmi.springbootshowcase.sampleapps.domain.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;

public interface UserDomainFactory
{
    User createAdminUser(String username, char[] password);
}
