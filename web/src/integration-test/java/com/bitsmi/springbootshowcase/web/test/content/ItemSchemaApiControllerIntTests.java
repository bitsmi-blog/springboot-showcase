package com.bitsmi.springbootshowcase.web.test.content;

import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.web.config.WebModuleConfig;
import com.bitsmi.springbootshowcase.web.test.config.ApplicationModuleMockConfig;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerIntegrationTest
@WithUserDetails("john.doe")
public class ItemSchemaApiControllerIntTests
{
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper jsonMapper;

    private MockMvc mockMvc;

    @Test
    @DisplayName("Get item schemas should return paginated data when user is logged")
    public void getItemSchemasTest1() throws Exception
    {
        this.mockMvc.perform(get("/api/content/schema")
                        .queryParam("page", "0")
                        .queryParam("size", "5")
                        .with(testSecurityContext()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("content").isArray(),
                        jsonPath("pagination.pageNumber").value(0),
                        jsonPath("pagination.pageSize").value(5),
                        jsonPath("pageCount").value(2),
                        jsonPath("totalCount").value(2),
                        jsonPath("totalPages").value(1)
                );
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ WebModuleConfig.class, ApplicationModuleMockConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig
    {
        @Bean
        @Primary
        public UserDetailsService userDetailsService(PasswordEncoder encoder)
        {
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(User.withUsername("john.doe")
                    .password(encoder.encode("foobar"))
                    .build());
            manager.createUser(User.withUsername("admin")
                    .password(encoder.encode("barfoo"))
                    .roles("admin.authority1")
                    .build());

            return manager;
        }
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
