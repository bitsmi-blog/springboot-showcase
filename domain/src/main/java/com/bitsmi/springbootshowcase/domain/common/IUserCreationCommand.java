package com.bitsmi.springbootshowcase.domain.common;

import com.bitsmi.springbootshowcase.domain.common.model.User;

public interface IUserCreationCommand
{
    User createAdminUser(String username, char[] password);
}
