package com.bitsmi.springbootshowcase.api.client.openfeign.test;

import com.bitsmi.springbootshowcase.api.client.openfeign.content.IItemSchemaApiClient;
import com.bitsmi.springbootshowcase.api.common.request.PaginatedRequest;
import com.bitsmi.springbootshowcase.api.common.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.api.content.response.ItemSchema;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.lang3.RandomUtils;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootConfiguration
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class })
public class ItemSchemaApiClientTest
{
    @Autowired
    private IItemSchemaApiClient itemSchemaApiClient;

    @Autowired
    private WireMockServer wireMockServer;

    @Test
    @DisplayName("getSchemas should return paged data given page request")
    public void getSchemasTest1()
    {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/content/schema"))
                .withQueryParam("page", WireMock.equalTo("0"))
                .withQueryParam("pageSize", WireMock.equalTo("5"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                    "content":[{"id":1001,"externalId":"schema-1","name":"Dummy Schema 1","fields":[{"id":1002,"name":"field1.2","dataType":"NUMBER","comments":"Sample field comments","creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1001,"name":"field1.1","dataType":"STRING","comments":null,"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"}],"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1002,"externalId":"schema-2","name":"Dummy Schema 2","fields":[{"id":1003,"name":"field2.1","dataType":"STRING","comments":null,"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1004,"name":"field2.2","dataType":"NUMBER","comments":"Sample field comments","creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"}],"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1003,"externalId":"schema-3","name":"Dummy Schema 3","fields":[{"id":1005,"name":"field3.1","dataType":"STRING","comments":null,"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1006,"name":"field3.2","dataType":"NUMBER","comments":"Sample field comments","creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"}],"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1004,"externalId":"schema-4","name":"Dummy Schema 4","fields":[{"id":1008,"name":"field4.2","dataType":"NUMBER","comments":"Sample field comments","creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1007,"name":"field4.1","dataType":"STRING","comments":null,"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"}],"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1005,"externalId":"schema-5","name":"Dummy Schema 5","fields":[{"id":1010,"name":"field5.2","dataType":"NUMBER","comments":"Sample field comments","creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"},{"id":1009,"name":"field5.1","dataType":"STRING","comments":null,"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"}],"creationDate":"2023-01-01T00:00:00","lastUpdated":"2023-01-01T00:00:00"}],
                                    "pagination":{
                                        "pageNumber":0,
                                        "pageSize":5
                                    },
                                    "totalPages":3,
                                    "totalCount":11
                                }
                                """)
                )
        );

        PaginatedResponse<ItemSchema> response = itemSchemaApiClient.getSchemas(PaginatedRequest.of(0, 5));

        assertThat(response.content()).hasSize(5);
        assertThat(response.pagination().pageNumber()).isEqualTo(0);
        assertThat(response.pagination().pageSize()).isEqualTo(5);
        assertThat(response.totalPages()).isEqualTo(3);
        assertThat(response.totalCount()).isEqualTo(11);
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @BeforeEach
    public void setUp()
    {
        wireMockServer.resetAll();
    }

    @TestConfiguration
    @EnableFeignClients(basePackageClasses = { IItemSchemaApiClient.class })
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
