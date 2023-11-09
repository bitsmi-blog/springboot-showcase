package com.bitsmi.springbootshowcase.web.test.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bitsmi.springbootshowcase.web.IMainPackage;
import com.bitsmi.springbootshowcase.web.user.controller.dto.response.UserDetailsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
public class JwtAuthTests
{
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper jsonMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private MockMvc mockMvc;

    /*---------------------------*
     * GET SCHEMAS
     *---------------------------*/
    @Test
    @DisplayName("Auth should return JWT token given successful login")
    public void authTest1() throws Exception
    {
        final String expectedUsername = "john.doe";
        final String expectedPassword = "foobar";
        final String actualToken = this.mockMvc.perform(post("/auth")
                        .header("Authorization", "Basic " + new String(Base64.encodeBase64((expectedUsername + ":" + expectedPassword).getBytes(StandardCharsets.UTF_8), false))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        final String actualUsername = JWT.require(Algorithm.HMAC512(jwtSecret))
                .build()
                .verify(actualToken)
                .getSubject();

        assertThat(actualUsername).isEqualTo(expectedUsername);
    }

    @Test
    @DisplayName("Auth should return Unauthorized error code given wrong credentials")
    public void authTest2() throws Exception
    {
        this.mockMvc.perform(post("/auth")
                        .header("Authorization", "Basic " + new String(Base64.encodeBase64("john.doe:not_valid".getBytes(StandardCharsets.UTF_8), false))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Filter should authorize a request given a valid JWT token")
    public void jwtTokenTest1() throws Exception
    {
        final String expectedUsername = "john.doe";
        final String token = JWT.create()
                .withSubject(expectedUsername)
                .withExpiresAt(Instant.now().plus(900_000, ChronoUnit.MILLIS))
                .sign(Algorithm.HMAC512(jwtSecret));

        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username(expectedUsername)
                .build();

        this.mockMvc.perform(get("/api/user/details")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("Filter should deny a request given an invalid JWT token")
    public void jwtTokenTest2() throws Exception
    {
        final String expectedUsername = "john.doe";
        final String token = JWT.create()
                .withSubject(expectedUsername)
                .withExpiresAt(Instant.now().plus(900_000, ChronoUnit.MILLIS))
                .sign(Algorithm.HMAC512("NOT_VALID_SECRET"));

        this.mockMvc.perform(get("/api/user/details")
                        .header("Authorization", "Bearer " + token))
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
                    .roles("USER")
                    .build());
            manager.createUser(User.withUsername("admin")
                    .password(encoder.encode("barfoo"))
                    .roles("USER", "ADMIN")
                    .build());

            return manager;
        }
    }

    @BeforeEach
    public void setup()
    {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }
}
