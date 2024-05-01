package com.bitsmi.springshowcase.contentservice.client.test;

import com.bitsmi.springshowcase.contentservice.client.ContentServiceClient;
import com.bitsmi.springshowcase.contentservice.client.common.exception.ClientErrorServiceException;
import com.bitsmi.springshowcase.contentservice.client.common.exception.ServerErrorServiceException;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WireMockTest
@Tag("IntegrationTest")
public class ContentServiceClientIntTests
{
    private ContentServiceClient client;

    @Test
    @DisplayName("client should throw an HttpServerErrorException given a 5XX error")
    public void httpServerErrorTest1()
    {
        stubFor(get("/api/info/service-version")
                .willReturn(aResponse()
                        .withStatus(502)
                        .withBody("Bad gateway")
                )
        );

        assertThatThrownBy(() -> {
            client.info().serviceVersion().get();
        })
                .asInstanceOf(InstanceOfAssertFactories.type(ServerErrorServiceException.class))
                .extracting(ServerErrorServiceException::getErrorCode, ServerErrorServiceException::getMessage)
                .containsExactly(Integer.toString(HttpStatus.BAD_GATEWAY.value()), "Bad gateway");
    }

    @Test
    @DisplayName("client should throw an HttpClientErrorException given a 4XX error")
    public void httpClientErrorTest1()
    {
        stubFor(get("/api/info/service-version")
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("Resource not found")
                )
        );

        assertThatThrownBy(() -> {
            client.info().serviceVersion().get();
        })
                .asInstanceOf(InstanceOfAssertFactories.type(ClientErrorServiceException.class))
                .extracting(ClientErrorServiceException::getErrorCode, ClientErrorServiceException::getMessage)
                .containsExactly(Integer.toString(HttpStatus.NOT_FOUND.value()), "Resource not found");
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @BeforeEach
    public void setUp(WireMockRuntimeInfo wmRuntimeInfo)
    {
        client = ContentServiceClient.builder()
                .withBaseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .withDefaultValidation()
                .build();
    }
}
