package com.bitsmi.springbootshowcase.clients.fluentclient.test.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.ServiceClient;
import com.bitsmi.springbootshowcase.clients.fluentclient.common.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.clients.fluentclient.common.response.Pagination;
import com.bitsmi.springbootshowcase.clients.fluentclient.common.response.Sort;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategorySetSelector;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.NewCategoryRequest;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.UpdateCategoryRequest;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.request.NewCategoryRequestObjectMother;
import com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.request.UpdateCategoryRequestObjectMother;
import com.bitsmi.springbootshowcase.clients.fluentclient.testsupport.inventory.response.CategoryObjectMother;
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
class InventoryApiIntTest {

    private static final String CATEGORIES_ENDPOINT = "/api/categories";

    private ServiceClient client;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        client = ServiceClient.builder()
                .withBaseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .withDefaultValidation()
                .build();

        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    /*---------------------------*
     * LIST
     *---------------------------*/
    @Test
    @DisplayName("Category list operation should return a Category list json")
    void listCategoriesTest1() throws JsonProcessingException {
        final PaginatedResponse<Category> expectedResponse = PaginatedResponse.<Category>builder()
                .data(List.of(
                        CategoryObjectMother.category1(),
                        CategoryObjectMother.category1()
                ))
                .pagination(
                        Pagination.of(0, 10, Sort.UNSORTED)
                )
                .totalPages(1)
                .pageCount(2)
                .totalCount(2)
                .build();

        stubFor(get(urlPathEqualTo(CATEGORIES_ENDPOINT))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("pageSize", equalTo("10"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        PaginatedResponse<Category> actualResponse = client.inventoryApi()
                .categories()
                .list()
                .paginate(0, 10)
                .get();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Category list operation should return a Category list json given a selector")
    void listCategoriesTest2() throws JsonProcessingException {
        final PaginatedResponse<Category> expectedResponse = PaginatedResponse.<Category>builder()
                .data(List.of(
                        CategoryObjectMother.category1()
                ))
                .pagination(
                        Pagination.of(0, 10, Sort.UNSORTED)
                )
                .totalPages(1)
                .pageCount(1)
                .totalCount(1)
                .build();

        stubFor(get(urlPathEqualTo(CATEGORIES_ENDPOINT))
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

        PaginatedResponse<Category> actualResponse = client.inventoryApi()
                .categories(CategorySetSelector.id(CategoryObjectMother.ID_CATEGORY1))
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
    @DisplayName("Category create operation should return created Category json")
    void createCategoryTest1() throws JsonProcessingException {
        final NewCategoryRequest providedRequest = NewCategoryRequestObjectMother.aCategory1CreateRequest();
        final String providedRequestJson = objectMapper.writeValueAsString(providedRequest);
        final Category expectedResponse = CategoryObjectMother.category1();

        stubFor(post(urlPathEqualTo(CATEGORIES_ENDPOINT))
                .withRequestBody(equalToJson(providedRequestJson))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        System.out.println(providedRequestJson);

        Category actualResponse = client.inventoryApi()
                .newCategory(providedRequest.data())
                .create();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    /*---------------------------*
     * UPDATE
     *---------------------------*/
    @Test
    @DisplayName("Category update operation should return updated Category json")
    void updateCategoryTest1() throws JsonProcessingException {
        final UpdateCategoryRequest providedRequest = UpdateCategoryRequestObjectMother.aCategory1UpdateRequest();
        final String providedRequestJson = objectMapper.writeValueAsString(providedRequest);
        final Category expectedResponse = CategoryObjectMother.category1();

        stubFor(put(urlPathEqualTo(CATEGORIES_ENDPOINT + "/1001"))
                .withRequestBody(equalToJson(providedRequestJson))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(expectedResponse))
                )
        );

        Category actualResponse = client.inventoryApi()
                .category(1001L)
                .update(providedRequest.data());

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }
}
