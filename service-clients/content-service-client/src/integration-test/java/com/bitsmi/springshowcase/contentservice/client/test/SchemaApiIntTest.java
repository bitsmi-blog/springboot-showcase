package com.bitsmi.springshowcase.contentservice.client.test;

import com.bitsmi.springshowcase.contentservice.client.ContentServiceClient;
import com.bitsmi.springshowcase.contentservice.client.common.response.PaginatedResponse;
import com.bitsmi.springshowcase.contentservice.client.common.response.Pagination;
import com.bitsmi.springshowcase.contentservice.client.common.response.Sort;
import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.request.SchemaSetSelector;
import com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema;
import com.bitsmi.springshowcase.contentservice.client.testsupport.schema.request.ItemSchemaDataTestDataBuilder;
import com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response.ItemSchemaTestDataBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
@Tag("IntegrationTest")
public class SchemaApiIntTest
{
    private ContentServiceClient client;
    private ObjectMapper objectMapper;

    /*---------------------------*
     * LIST
     *---------------------------*/
    @Test
    @DisplayName("Schema list operation should return a schema list json")
    public void listSchemasTest1() throws JsonProcessingException
    {
        final PaginatedResponse<ItemSchema> expectedResponse = PaginatedResponse.<ItemSchema>builder()
                .content(List.of(
                        ItemSchemaTestDataBuilder.schema1(),
                        ItemSchemaTestDataBuilder.schema2()
                ))
                .pagination(
                        Pagination.of(0, 10, Sort.UNSORTED)
                )
                .totalPages(1)
                .pageCount(2)
                .totalCount(2)
                .build();

        stubFor(get(urlPathEqualTo("/api/schema"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("pageSize", equalTo("10"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        PaginatedResponse<ItemSchema> actualResponse = client.schemas()
                .list()
                .paginate(0, 10)
                .get();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Schema list operation should return a schema list json given a selector")
    public void listSchemasTest2() throws JsonProcessingException
    {
        final PaginatedResponse<ItemSchema> expectedResponse = PaginatedResponse.<ItemSchema>builder()
                .content(List.of(
                        ItemSchemaTestDataBuilder.schema1()
                ))
                .pagination(
                        Pagination.of(0, 10, Sort.UNSORTED)
                )
                .totalPages(1)
                .pageCount(1)
                .totalCount(1)
                .build();

        stubFor(get(urlPathEqualTo("/api/schema"))
                // Wiremock decodes query param value automatically
                .withQueryParam("selector", equalTo("id EQUALS 1001"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("pageSize", equalTo("10"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        PaginatedResponse<ItemSchema> actualResponse = client.schemas(SchemaSetSelector.id(ItemSchemaTestDataBuilder.ID_SCHEMA1))
                .list()
                .paginate(0, 10)
                .get();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    /*---------------------------*
     * CREATE
     *---------------------------*/
    @Test
    @DisplayName("Schema create operation should return created schema json")
    public void createSchemaTest1() throws JsonProcessingException
    {
        final ItemSchemaData expectedRequest = ItemSchemaDataTestDataBuilder.schema1();
        final String expectedRequestJson = objectMapper.writeValueAsString(expectedRequest);
        final ItemSchema expectedResponse = ItemSchemaTestDataBuilder.schema1();

        stubFor(post(urlPathEqualTo("/api/schema"))
                .withRequestBody(equalToJson(expectedRequestJson))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        System.out.println(expectedRequestJson);

        ItemSchema actualResponse = client.schema(expectedRequest)
                .create();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    /*---------------------------*
     * UPDATE
     *---------------------------*/
    @Test
    @DisplayName("Schema update operation should return updated schema json")
    public void updateSchemaTest1() throws JsonProcessingException
    {
        final ItemSchemaData expectedRequest = ItemSchemaDataTestDataBuilder.schema1();
        final String expectedRequestJson = objectMapper.writeValueAsString(expectedRequest);
        final ItemSchema expectedResponse = ItemSchemaTestDataBuilder.schema1();

        stubFor(put(urlPathEqualTo("/api/schema/1001"))
                .withRequestBody(equalToJson(expectedRequestJson))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        ItemSchema actualResponse = client.schema(1001L)
                .update(expectedRequest);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
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

        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
}
