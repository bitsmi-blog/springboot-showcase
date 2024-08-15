package com.bitsmi.springbootshowcase.sampleapps.domain.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;

import java.util.Set;

public interface UserDomainFactory
{
    User createUser(String username, char[] password, String completeName, Set<String> groupNames);
    User createAdminUser(String username, char[] password);

    User updatedUser(User userToUpdate, String username, String completeName, Set<String> groupNames);

    String encodePassword(char[] password);
}
