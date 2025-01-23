package com.bitsmi.springbootshowcase.clients.openfeign.server.test.user.controller;

import com.bitsmi.springbootshowcase.clients.openfeign.api.user.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.clients.openfeign.server.ServerModulePackage;
import com.bitsmi.springbootshowcase.utils.IgnoreOnComponentScan;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc

@Tag("IntegrationTest")
class UserApiControllerIntTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper jsonMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    @DisplayName("Get user details should return user details given a logged user")
    void getUserDetailsTest1() throws Exception {
        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username("john.doe")
                .completeName("John Doe")
                .build();

        this.mockMvc.perform(get("/api/user/details"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("Get user details should return forbidden status given a non logged user")
    void getAdminDetailsTest1() throws Exception {
        final String username = "admin";
        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username(username)
                .build();

        this.mockMvc.perform(get("/api/user/admin/details"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedResponse)));
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(
            basePackageClasses = { ServerModulePackage.class },
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreOnComponentScan.class)
    )
    static class TestConfig {

    }
}
