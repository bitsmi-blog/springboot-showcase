package com.bitsmi.springbootshowcase.sample.domain.common;

import com.bitsmi.springbootshowcase.sample.domain.common.model.User;

public interface IUserDomainCommandService
{
    User createAdminUser(String username, char[] password);
}
