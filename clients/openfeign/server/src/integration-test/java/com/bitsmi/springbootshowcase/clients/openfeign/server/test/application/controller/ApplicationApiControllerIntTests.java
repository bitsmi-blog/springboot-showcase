package com.bitsmi.springbootshowcase.clients.openfeign.server.test.application.controller;

import com.bitsmi.springbootshowcase.clients.openfeign.server.ServerModuleConfig;
import com.bitsmi.springbootshowcase.clients.openfeign.server.util.IgnoreOnComponentScan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
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
class ApplicationApiControllerIntTests
{
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup()
    {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    @DisplayName("Get Hello should return Hello message")
    void getHelloTest1() throws Exception
    {
        this.mockMvc.perform(get("/api/application/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from SpringBoot Showcase application"));
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({
            ServerModuleConfig.class
    })
    @IgnoreOnComponentScan
    static class TestConfig
    {

    }
}