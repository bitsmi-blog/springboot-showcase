package com.bitsmi.springbootshowcase.web.test.admin;

import com.bitsmi.springbootshowcase.web.IMainPackage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestPropertySource({
        "classpath:application.properties",
        "file:./application-test.properties"
})
@Tag("IntegrationTest")
public class AdminApiControllerIntTests
{
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper jsonMapper;

    private MockMvc mockMvc;

    @Test
    @DisplayName("getAdmin should return admin message when user has the required role")
    @WithUserDetails("admin")
    public void getAdminTest1() throws Exception
    {
        this.mockMvc.perform(get("/api/admin")
                        .with(testSecurityContext()))
                .andExpect(status().isOk())
                .andExpect(content().string("Response from Admin endpoint"));
    }

    @Test
    @DisplayName("getAdmin should return access denied error when user doesn't have the required role")
    @WithUserDetails("john.doe")
    public void getAdminTest2() throws Exception
    {
        this.mockMvc.perform(get("/api/admin")
                        .with(testSecurityContext()))
                .andExpect(status().isForbidden());
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(basePackageClasses = IMainPackage.class)
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
                    .authorities("ROLE_admin.authority1")
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
