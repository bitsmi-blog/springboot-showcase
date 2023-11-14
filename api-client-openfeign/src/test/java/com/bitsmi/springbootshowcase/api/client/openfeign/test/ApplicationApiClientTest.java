package com.bitsmi.springbootshowcase.api.client.openfeign.test;

import com.bitsmi.springbootshowcase.api.client.openfeign.IApiClientPackage;
import com.bitsmi.springbootshowcase.api.client.openfeign.application.IApplicationApiClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootConfiguration
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class })
@TestPropertySource(properties = {
        "web.api.client.url = http://localhost:8080"
})
public class ApplicationApiClientTest
{
    @Autowired
    private IApplicationApiClient applicationApiClient;

    @Autowired
    private WireMockServer wireMockServer;

    @Test
    @DisplayName("getHelloTest should return greetings from server")
    public void getHelloTest1()
    {
        wireMockServer.stubFor(WireMock.get("/api/application/hello")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.TEXT_PLAIN_VALUE)
                        .withBody("Hello from SpringBoot Showcase application")
                )
        );

        String message = applicationApiClient.getHello();
        assertThat(message).isEqualTo("Hello from SpringBoot Showcase application");
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @EnableFeignClients(basePackageClasses = { IApiClientPackage.class })
    static class TestConfig
    {
        @Bean(initMethod = "start", destroyMethod = "stop")
        public WireMockServer mockServer() {
            return new WireMockServer(8080);
        }
    }
}
