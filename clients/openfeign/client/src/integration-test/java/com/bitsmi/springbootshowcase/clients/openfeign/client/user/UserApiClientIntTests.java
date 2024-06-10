package com.bitsmi.springbootshowcase.clients.openfeign.client.user;

import com.bitsmi.springbootshowcase.clients.openfeign.api.user.response.UserDetailsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import wiremock.org.apache.commons.lang3.RandomUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootConfiguration
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class })
class UserApiClientIntTests
{
    @Autowired
    private UserApiClient sut;

    @Autowired
    private WireMockServer wireMockServer;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
        objectMapper = new ObjectMapper();
        wireMockServer.resetAll();
    }

    @Test
    @DisplayName("getDetails should return current user details")
    void getDetailsTest1() throws Exception
    {
        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username("john.doe")
                .build();

        wireMockServer.stubFor(WireMock.get("/api/user/details")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        UserDetailsResponse response = sut.getDetails();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("getAdminDetails should return current user details given an admin user")
    void getAdminDetailsTest1() throws Exception
    {
        final UserDetailsResponse expectedResponse = UserDetailsResponse.builder()
                .username("admin")
                .build();

        wireMockServer.stubFor(WireMock.get("/api/user/admin/details")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        UserDetailsResponse response = sut.getAdminDetails();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    /*---------------------------*
     * CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @EnableFeignClients(basePackageClasses = { UserApiClient.class })
    static class TestConfig
    {
        @Bean(initMethod = "start", destroyMethod = "stop")
        public WireMockServer mockServer(@Value("${web.api.client.port}") int port) {
            return new WireMockServer(port);
        }
    }

    @DynamicPropertySource
    static void registerWebApiClientProperties(DynamicPropertyRegistry registry)
    {
        final int port = RandomUtils.nextInt(8_000, 10_000);
        final String url = "http://localhost:" + port;

        registry.add("web.api.client.port", () -> port);
        registry.add("web.api.client.url", () -> url);
    }
}
