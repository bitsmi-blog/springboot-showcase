package com.bitsmi.springshowcase.sampleservice.client.test;

import com.bitsmi.springshowcase.sampleservice.client.SampleServiceClient;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WireMockTest
@Tag("IntegrationTest")
public class InfoApiIntTests
{
    @Test
    @DisplayName("get serviceVersion operation should return a version string")
    public void serviceVersionTest1(WireMockRuntimeInfo wmRuntimeInfo)
    {
        final String expectedServiceVersion = "SAMPLE-SERVICE-1.0.0";
        stubFor(get("/api/info/service-version")
                .willReturn(ok(expectedServiceVersion)));

        SampleServiceClient client = new SampleServiceClient(wmRuntimeInfo.getHttpBaseUrl());
        String actualServiceVersion = client.info().serviceVersion().get();

        assertThat(actualServiceVersion).isEqualTo(expectedServiceVersion);
    }

    @Test
    @DisplayName("get serviceVersion operation should throw an HttpServerErrorException given a 5XX error")
    public void serviceVersionTest2(WireMockRuntimeInfo wmRuntimeInfo)
    {
        stubFor(get("/api/info/service-version")
                .willReturn(aResponse().withStatus(502)));

        SampleServiceClient client = new SampleServiceClient(wmRuntimeInfo.getHttpBaseUrl());

        assertThatThrownBy(() -> {
                client.info().serviceVersion().get();
            })
            .asInstanceOf(InstanceOfAssertFactories.type(HttpServerErrorException.class))
            .extracting(HttpServerErrorException::getStatusCode)
            .isEqualTo(HttpStatus.BAD_GATEWAY);
    }

    @Test
    @DisplayName("get serviceVersion operation should throw an HttpClientErrorException given a 4XX error")
    public void serviceVersionTest3(WireMockRuntimeInfo wmRuntimeInfo)
    {
        stubFor(get("/api/info/service-version")
                .willReturn(aResponse().withStatus(404)));

        SampleServiceClient client = new SampleServiceClient(wmRuntimeInfo.getHttpBaseUrl());

        assertThatThrownBy(() -> {
            client.info().serviceVersion().get();
        })
                .asInstanceOf(InstanceOfAssertFactories.type(HttpClientErrorException.class))
                .extracting(HttpClientErrorException::getStatusCode)
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
