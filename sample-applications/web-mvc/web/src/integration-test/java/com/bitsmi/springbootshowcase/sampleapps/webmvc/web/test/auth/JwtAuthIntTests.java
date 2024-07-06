package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.config.WebModuleConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.ApplicationModuleMockConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.DomainModuleMockConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.config.UserDetailsTestConfig;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.internal.ControllerIntegrationTest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.UserDetailsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.FilterChainProxy;
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

@ControllerIntegrationTest
class JwtAuthIntTests
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

    @BeforeEach
    void setup()
    {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    @DisplayName("Auth should return JWT token when login is successful")
    void authTest1() throws Exception
    {
        final String expectedUsername = "john.doe";
        final String expectedPassword = "password.john.doe";
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
    @DisplayName("Auth should return Unauthorized error code when wrong credentials")
    void authTest2() throws Exception
    {
        this.mockMvc.perform(post("/auth")
                        .header("Authorization", "Basic " + new String(Base64.encodeBase64("john.doe:not_valid".getBytes(StandardCharsets.UTF_8), false))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Filter should authorize a request when a valid JWT token is provided")
    void jwtTokenTest1() throws Exception
    {
        final String expectedUsername = "john.doe";
        final String token = JWT.create()
                .withSubject(expectedUsername)
                .withExpiresAt(Instant.now().plus(900_000, ChronoUnit.MILLIS))
                .sign(Algorithm.HMAC512(jwtSecret));

        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username(expectedUsername)
                .completeName("John Doe")
                .build();

        this.mockMvc.perform(get("/api/user/details")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("Filter should deny a request when an invalid JWT token is provided")
    void jwtTokenTest2() throws Exception
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
     * TEST CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration()
    @Import({
            WebModuleConfig.class,
            ApplicationModuleMockConfig.class,
            DomainModuleMockConfig.class,
            UserDetailsTestConfig.class
    })
    static class TestConfig
    {

    }
}
