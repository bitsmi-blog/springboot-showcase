package test.inventory;

import com.bitsmi.springbootshowcase.clients.fluentclient.ServiceClient;
import com.bitsmi.springbootshowcase.clients.fluentclient.common.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategoryData;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request.CategorySetSelector;
import com.bitsmi.springbootshowcase.clients.fluentclient.inventory.response.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ManualTest")
public class InventoryApiWiremockDockerTests
{
    private static final String HOST = "http://localhost:8100";

    private ServiceClient client;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
        client = ServiceClient.builder()
                .withBaseUrl(HOST)
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
    void listCategoriesTest1() throws JsonProcessingException
    {
        final String expectedResponseString = """
                {
                    "data": [
                        {
                            "id": 1001,
                            "externalId": "category-1",
                            "name": "Test category 1",
                            "creationDate": "2024-01-02T10:20:00.000",
                            "lastUpdated": "2024-01-02T10:20:00.000"
                        }, {
                            "id": 1002,
                            "externalId": "category-2",
                            "name": "Test category 2",
                            "creationDate": "2024-01-02T10:20:00.000",
                            "lastUpdated": "2024-01-02T10:20:00.000"
                        }
                    ],
                    "pagination": {
                        "pageNumber": 0,
                        "pageSize": 10,
                        "sort": {
                            "orders": []
                        }
                    },
                    "pageCount": 2,
                    "totalPages": 1,
                    "totalCount": 2
                }
                """;
        PaginatedResponse<Category> expectedResponse = objectMapper.readValue(expectedResponseString, new TypeReference<PaginatedResponse<Category>>() { });

        PaginatedResponse<Category> actualResponse = client.categories()
                .list()
                .paginate(0, 10)
                .get();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Category list operation should return a Category list json given a selector")
    public void listCategoriesTest2() throws JsonProcessingException
    {
        final String expectedResponseString = """
                {
                    "data": [
                        {
                            "id": 1001,
                            "externalId": "category-1",
                            "name": "Test category 1",
                            "creationDate": "2024-01-02T10:20:00.000",
                            "lastUpdated": "2024-01-02T10:20:00.000"
                        }
                    ],
                    "pagination": {
                        "pageNumber": 0,
                        "pageSize": 10,
                        "sort": {
                            "orders": []
                        }
                    },
                    "pageCount": 1,
                    "totalPages": 1,
                    "totalCount": 1
                }
                """;
        PaginatedResponse<Category> expectedResponse = objectMapper.readValue(expectedResponseString, new TypeReference<PaginatedResponse<Category>>() { });

        PaginatedResponse<Category> actualResponse = client.categories(CategorySetSelector.id(1001L))
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
    public void createCategoryTest1() throws JsonProcessingException
    {
        final CategoryData givenCategoryData = CategoryData.builder()
                .externalId("category-1")
                .name("Test category 1")
                .build();
        final String expectedResponseString = """
                {
                    "id": 1001,
                    "externalId": "category-1",
                    "name": "Test category 1",
                    "creationDate": "2024-01-02T10:20:00.000",
                    "lastUpdated": "2024-01-02T10:20:00.000"
                }
                """;
        Category expectedResponse = objectMapper.readValue(expectedResponseString, Category.class);

        Category actualResponse = client.category(givenCategoryData)
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
    public void updateCategoryTest1() throws JsonProcessingException
    {
        final CategoryData givenCategoryData = CategoryData.builder()
                .externalId("category-1")
                .name("Modified test category 1")
                .build();
        final String expectedResponseString = """
                {
                    "id": 1001,
                    "externalId": "category-1",
                    "name": "Modified test category 1",
                    "creationDate": "2024-01-02T10:20:00.000",
                    "lastUpdated": "2024-02-01T11:00:00.000"
                }
                """;
        Category expectedResponse = objectMapper.readValue(expectedResponseString, Category.class);

        Category actualResponse = client.category(1001L)
                .update(givenCategoryData);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }
}
