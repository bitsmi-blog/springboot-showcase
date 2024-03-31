package com.bitsmi.springshowcase.sampleservice.client.test;

import com.bitsmi.springshowcase.sampleservice.client.SampleServiceClient;
import com.bitsmi.springshowcase.sampleservice.client.common.response.PagedResponse;
import com.bitsmi.springshowcase.sampleservice.client.common.response.Pagination;
import com.bitsmi.springshowcase.sampleservice.client.common.response.Sort;
import com.bitsmi.springshowcase.sampleservice.client.content.request.ContentSelector;
import com.bitsmi.springshowcase.sampleservice.client.content.response.Content;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WireMockTest
@Tag("IntegrationTest")
public class ContentApiIntTests
{
    private SampleServiceClient client;
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("get contentList operation should return a content list json given a selector")
    public void contentListTest1() throws JsonProcessingException
    {
        final LocalDateTime now = LocalDateTime.now();
        final PagedResponse<Content> expectedResponse = PagedResponse.<Content>builder()
                .content(List.of(
                    Content.builder()
                        .id(1L)
                        .externalId("content_1")
                        .name("Content 1")
                        .creationDate(now)
                        .lastUpdated(now)
                        .build()
                ))
                .pagination(
                    Pagination.of(0, 10, Sort.UNSORTED)
                )
                .totalPages(1)
                .pageCount(1)
                .totalCount(1)
                .build();

        System.out.println(objectMapper.writeValueAsString(expectedResponse));

        stubFor(get(urlPathEqualTo("/api/content"))
                // Wiremock decodes query param value automatically
                .withQueryParam("selector", equalTo("id EQUALS 1"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("pageSize", equalTo("10"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        PagedResponse<Content> actualResponse = client.content(ContentSelector.id(1L))
                .list()
                .paged(0, 10)
                .get();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("get contentList operation should validate content selector")
    public void contentListTest2()
    {
        assertThatThrownBy(() -> {
                client.content(ContentSelector.id(null))
                    .list()
                    .get();
            })
            .asInstanceOf(InstanceOfAssertFactories.type(ConstraintViolationException.class))
            .extracting(ConstraintViolationException::getConstraintViolations)
            .satisfies(constraintViolations -> {
                assertThat(constraintViolations).hasSize(1);
            });
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

        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
}
