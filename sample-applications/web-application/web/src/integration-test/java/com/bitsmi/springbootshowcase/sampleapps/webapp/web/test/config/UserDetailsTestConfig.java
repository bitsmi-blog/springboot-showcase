package com.bitsmi.springbootshowcase.sampleapps.webapp.web.test.config;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsTestConfig
{
    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder encoder)
    {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("john.doe")
                .password(encoder.encode("password.john.doe"))
                .roles(UserConstants.USER_GROUP_USER)
                .authorities(UserConstants.USER_PERMISSION_USER_PERMISSION1)
                .build());
        manager.createUser(User.withUsername("admin")
                .password(encoder.encode("password.admin"))
                .roles(UserConstants.USER_GROUP_ADMIN, UserConstants.USER_GROUP_USER)
                .authorities(
                        UserConstants.USER_PERMISSION_ADMIN_PERMISSION1,
                        UserConstants.USER_PERMISSION_USER_PERMISSION1
                )
                .build());

        return manager;
    }
}
