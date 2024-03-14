package com.bitsmi.springbootshowcase.web.test.user;

import com.bitsmi.springbootshowcase.api.user.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.web.config.WebModuleConfig;
import com.bitsmi.springbootshowcase.web.test.config.ApplicationModuleMockConfig;
import com.bitsmi.springbootshowcase.web.test.config.DomainModuleMockConfig;
import com.bitsmi.springbootshowcase.web.test.config.UserDetailsTestConfig;
import com.bitsmi.springbootshowcase.web.testsupport.internal.ControllerIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerIntegrationTest
@WithUserDetails("john.doe")
public class UserApiControllerIntTests
{
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper jsonMapper;

    private MockMvc mockMvc;

    @Test
    @DisplayName("Get user details should return user details when user is logged")
    public void getUserDetailsTest1() throws Exception
    {
        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username("john.doe")
                .completeName("John Doe")
                .build();

        this.mockMvc.perform(get("/api/user/details")
                    .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("Get user details should return forbidden status when user is not logged")
    @WithAnonymousUser
    public void getUserDetailsTest2() throws Exception
    {
        this.mockMvc.perform(get("/api/user/details")
                    .with(testSecurityContext()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("getAdminDetails should return admin message when user has the required role")
    @WithUserDetails("admin")
    public void getAdminDetailsTest1() throws Exception
    {
        final String username = "admin";
        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username(username)
                .build();

        this.mockMvc.perform(get("/api/user/admin/details")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("getAdminDetails should return access denied error when user doesn't have the required role")
    @WithUserDetails("john.doe")
    public void getAdminDetailsTest2() throws Exception
    {
        this.mockMvc.perform(get("/api/user/admin/details")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({
            WebModuleConfig.class,
            ApplicationModuleMockConfig.class,
            DomainModuleMockConfig.class,
            UserDetailsTestConfig.class
    })
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }

    @BeforeEach
    public void setup()
    {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilter(springSecurityFilterChain)
                .build();
    }
}
