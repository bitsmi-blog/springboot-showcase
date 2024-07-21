package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.user;

import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.config.WebModuleConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.ApplicationModuleMockConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.DomainModuleMockConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.UserDetailsTestConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.internal.ControllerIntegrationTest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
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
class UserApiControllerIntTests
{
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper jsonMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup()
    {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    @DisplayName("Get user details should return user details given a logged user")
    void getUserDetailsTest1() throws Exception
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
    @DisplayName("Get user details should return forbidden status given a non logged user")
    @WithAnonymousUser
    void getUserDetailsTest2() throws Exception
    {
        this.mockMvc.perform(get("/api/user/details")
                    .with(testSecurityContext()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("getAdminDetails should return admin message given a user user with admin role")
    @WithUserDetails("admin")
    void getAdminDetailsTest1() throws Exception
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
    @DisplayName("getAdminDetails should return access denied error given a non admin user")
    @WithUserDetails("john.doe")
    void getAdminDetailsTest2() throws Exception
    {
        this.mockMvc.perform(get("/api/user/admin/details")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());
    }

    /*---------------------------*
     * TEST CONFIG AND HELPERS
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
}
