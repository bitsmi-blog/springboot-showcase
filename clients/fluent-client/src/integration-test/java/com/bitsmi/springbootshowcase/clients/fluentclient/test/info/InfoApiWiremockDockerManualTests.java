package com.bitsmi.springbootshowcase.clients.fluentclient.test.info;

import com.bitsmi.springbootshowcase.clients.fluentclient.ServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ManualTest")
public class InfoApiWiremockDockerManualTests
{
    private static final String HOST = "http://localhost:8100";
    private ServiceClient client;

    @BeforeEach
    void setUp()
    {
        client = ServiceClient.builder()
                .withBaseUrl(HOST)
                .withDefaultValidation()
                .build();
    }

    @Test
    @DisplayName("get serviceVersion operation should return a version string")
    public void serviceVersionTest1()
    {
        final String expectedServiceVersion = "SAMPLE-SERVICE-1.0.0";

        String actualServiceVersion = client.infoApi().serviceVersion().get();

        assertThat(actualServiceVersion).isEqualTo(expectedServiceVersion);
    }
}
