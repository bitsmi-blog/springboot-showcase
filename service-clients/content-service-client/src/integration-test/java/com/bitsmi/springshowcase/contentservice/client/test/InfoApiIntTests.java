package com.bitsmi.springshowcase.contentservice.client.test;

import com.bitsmi.springshowcase.contentservice.client.SampleServiceClient;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
@Tag("IntegrationTest")
public class InfoApiIntTests
{
    private SampleServiceClient client;

    @Test
    @DisplayName("get serviceVersion operation should return a version string")
    public void serviceVersionTest1()
    {
        final String expectedServiceVersion = "SAMPLE-SERVICE-1.0.0";
        stubFor(get("/api/info/service-version")
                .willReturn(ok(expectedServiceVersion)));

        String actualServiceVersion = client.info().serviceVersion().get();

        assertThat(actualServiceVersion).isEqualTo(expectedServiceVersion);
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @BeforeEach
    public void setUp(WireMockRuntimeInfo wmRuntimeInfo)
    {
        client = SampleServiceClient.builder()
                .withBaseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .withDefaultValidation()
                .build();
    }
}
