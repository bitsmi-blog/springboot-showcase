package com.bitsmi.springbootshowcase.infrastructure.test.content.impl;

import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaField;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaRepositoryService;
import com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaTestDataBuilder;
import com.bitsmi.springbootshowcase.infrastructure.content.impl.ItemSchemaRepositoryServiceImpl;
import com.bitsmi.springbootshowcase.infrastructure.content.mapper.ContentServiceClientPaginatedResponseMapper;
import com.bitsmi.springbootshowcase.infrastructure.content.mapper.ItemSchemaFieldMapperImpl;
import com.bitsmi.springbootshowcase.infrastructure.content.mapper.ItemSchemaMapperImpl;
import com.bitsmi.springshowcase.contentservice.client.ContentServiceClient;
import com.bitsmi.springshowcase.contentservice.client.common.exception.ClientErrorServiceException;
import com.bitsmi.springshowcase.contentservice.client.common.response.PaginatedResponse;
import com.bitsmi.springshowcase.contentservice.client.common.response.Pagination;
import com.bitsmi.springshowcase.contentservice.client.common.response.Sort;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaCreationApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaElementApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaListOperation;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaSetApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaData;
import com.bitsmi.springshowcase.contentservice.client.schema.request.ItemSchemaFieldData;
import com.bitsmi.springshowcase.contentservice.client.schema.request.SchemaSetSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemSchemaRepositoryServiceImplTests
{
    @Mock
    private ContentServiceClient contentServiceClient;

    private IItemSchemaRepositoryService itemSchemaRepositoryService;

    @BeforeEach
    void setUp()
    {
        itemSchemaRepositoryService = new ItemSchemaRepositoryServiceImpl(
                contentServiceClient,
                new ItemSchemaMapperImpl(new ItemSchemaFieldMapperImpl()),
                new ContentServiceClientPaginatedResponseMapper()
        );
    }

    /*---------------------------*
     * FIND ALL
     *---------------------------*/
    @Test
    @DisplayName("findSchemaById should return all schemas data")
    void findAllSchemasTest1()
    {
        List<com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema> expectedSchemas = List.of(
                com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response.ItemSchemaTestDataBuilder.schema1(),
                com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response.ItemSchemaTestDataBuilder.schema2()
        );

        SchemaSetApiBuilder schemaSetApiBuilder = mock(SchemaSetApiBuilder.class);
        SchemaListOperation schemaListOperation = mock(SchemaListOperation.class);
        when(contentServiceClient.schemas())
                .thenReturn(schemaSetApiBuilder);
        when(schemaSetApiBuilder.list())
                .thenReturn(schemaListOperation);
        when(schemaListOperation.get())
                .thenReturn(
                    PaginatedResponse.<com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema>builder()
                        .content(expectedSchemas)
                        .pagination(Pagination.of(0, 10, Sort.UNSORTED))
                        .pageCount(2)
                        .totalCount(2)
                        .totalPages(1)
                        .build()
                );

        final List<ItemSchema> schemas = itemSchemaRepositoryService.findAllItemSchemas();

        assertThat(schemas).hasSize(2);
        assertThat(schemas)
                .filteredOn(schema -> Objects.equals(schema.id(), ItemSchemaTestDataBuilder.ID_SCHEMA1))
                .first()
                .satisfies(actualSchema -> {
                    assertSchema(actualSchema, expectedSchemas.getFirst());
                });
    }

    /*---------------------------*
     * FIND SCHEMAS BY EXTERNAL ID
     *---------------------------*/
    @Test
    @DisplayName("findSchemaByExternalId should return first page results")
    void findSchemaByExternalIdTest1()
    {
        com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema expectedSchema =
                com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response.ItemSchemaTestDataBuilder.schema1();
        SchemaSetSelector expectedSelector = SchemaSetSelector.externalId(ItemSchemaTestDataBuilder.EXTERNAL_ID_SCHEMA1);

        SchemaSetApiBuilder schemaSetApiBuilder = mock(SchemaSetApiBuilder.class);
        SchemaListOperation schemaListOperation = mock(SchemaListOperation.class);
        when(contentServiceClient.schemas(expectedSelector))
                .thenReturn(schemaSetApiBuilder);
        when(schemaSetApiBuilder.list())
                .thenReturn(schemaListOperation);
        when(schemaListOperation.get())
                .thenReturn(
                        PaginatedResponse.<com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema>builder()
                                .content(List.of(expectedSchema))
                                .pagination(Pagination.of(0, 10, Sort.UNSORTED))
                                .pageCount(1)
                                .totalCount(1)
                                .totalPages(1)
                                .build()
                );

        Optional<ItemSchema> optSchema = itemSchemaRepositoryService.findItemSchemaByExternalId(ItemSchemaTestDataBuilder.EXTERNAL_ID_SCHEMA1);

        assertThat(optSchema).isPresent().hasValueSatisfying(actualSchema -> {
            assertSchema(actualSchema, expectedSchema);
        });
    }

    /*---------------------------*
     * CREATE SCHEMA
     *---------------------------*/
    @Test
    @DisplayName("createSchema should create a new Schema")
    public void createSchemaTest1()
    {
        final ItemSchema expectedSchema = com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaTestDataBuilder
                .schema1();
        final com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema expectedSchemaResponse =
                com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response.ItemSchemaTestDataBuilder.schema1();

        SchemaCreationApiBuilder schemaCreationApiBuilder = mock(SchemaCreationApiBuilder.class);
        when(contentServiceClient.schema(any(ItemSchemaData.class)))
                .thenReturn(schemaCreationApiBuilder);
        when(schemaCreationApiBuilder.create())
                .thenReturn(expectedSchemaResponse);

        ItemSchema actualSchema = itemSchemaRepositoryService.createItemSchema(expectedSchema);

        assertSchema(actualSchema, expectedSchemaResponse);
    }

    @Test
    @DisplayName("createSchema should throw an ElementAlreadyExistsException when schema already exists")
    public void createSchemaTest2()
    {
        final ItemSchema schema = com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaTestDataBuilder.schema1();

        SchemaCreationApiBuilder schemaCreationApiBuilder = mock(SchemaCreationApiBuilder.class);
        when(contentServiceClient.schema(any(ItemSchemaData.class)))
                .thenReturn(schemaCreationApiBuilder);
        when(schemaCreationApiBuilder.create())
                .thenThrow(new ClientErrorServiceException("409", "A message"));

        assertThatExceptionOfType(ElementAlreadyExistsException.class)
                .isThrownBy(() -> {
                    itemSchemaRepositoryService.createItemSchema(schema);
                });
    }

    /*---------------------------*
     * UPDATE SCHEMA
     *---------------------------*/
    @Test
    @DisplayName("updateSchema should update an existing Schema")
    public void updateSchemaTest1()
    {
        final ItemSchema expectedSchema = com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaTestDataBuilder
                .schema1();
        final com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema expectedSchemaResponse =
                com.bitsmi.springshowcase.contentservice.client.testsupport.schema.response.ItemSchemaTestDataBuilder.schema1();

        SchemaElementApiBuilder schemaElementApiBuilder = mock(SchemaElementApiBuilder.class);
        when(contentServiceClient.schema(ItemSchemaTestDataBuilder.ID_SCHEMA1))
                .thenReturn(schemaElementApiBuilder);
        when(schemaElementApiBuilder.update(any(ItemSchemaData.class)))
                .thenReturn(expectedSchemaResponse);

        ItemSchema actualSchema = itemSchemaRepositoryService.updateItemSchema(expectedSchema);

        ArgumentCaptor<ItemSchemaData> schemaDataArgumentCaptor = ArgumentCaptor.forClass(ItemSchemaData.class);
        verify(schemaElementApiBuilder).update(schemaDataArgumentCaptor.capture());

        assertSchemaData(schemaDataArgumentCaptor.getValue(), expectedSchema);
        assertSchema(actualSchema, expectedSchemaResponse);
    }

    @Test
    @DisplayName("updateSchema should throw an ElementNotFoundException when schema doesn't exists")
    public void updateSchemaTest2()
    {
        final Long expectedId = 9999L;
        final ItemSchema schema = ItemSchemaTestDataBuilder.builder()
                .emptySchema1()
                .defaultFields()
                .id(expectedId)
                .build();

        SchemaElementApiBuilder schemaElementApiBuilder = mock(SchemaElementApiBuilder.class);
        when(contentServiceClient.schema(expectedId))
                .thenReturn(schemaElementApiBuilder);
        when(schemaElementApiBuilder.update(any(ItemSchemaData.class)))
                .thenThrow(new ClientErrorServiceException("404", "A message"));

        assertThatExceptionOfType(ElementNotFoundException.class)
                .isThrownBy(() -> {
                    // Non existing ID = 9999L
                    itemSchemaRepositoryService.updateItemSchema(schema);
                });
    }

    /*---------------------------*
     * HELPERS
     *---------------------------*/
    private void assertSchema(ItemSchema actual, com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema expected)
    {
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.externalId()).isEqualTo(expected.externalId());
        assertThat(actual.name()).isEqualTo(expected.name());
        assertThat(actual.creationDate()).isEqualTo(expected.creationDate());
        assertThat(actual.lastUpdated()).isEqualTo(expected.lastUpdated());

        Map<String, com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchemaField> idxExpectedFields = expected.fields().stream()
                .collect(Collectors.toMap(com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchemaField::name, Function.identity()));
        assertThat(actual.fields())
                .hasSize(expected.fields().size())
                .allSatisfy(actualField -> {
                    var expectedField = idxExpectedFields.get(actualField.name());
                    assertField(actualField, expectedField);
                });
    }

    private void assertField(ItemSchemaField actual, com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchemaField expected)
    {
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.name()).isEqualTo(expected.name());
        assertThat(actual.dataType().name()).isEqualTo(expected.dataType().name());
        assertThat(actual.comments()).isEqualTo(expected.comments());
        assertThat(actual.creationDate()).isEqualTo(expected.creationDate());
        assertThat(actual.lastUpdated()).isEqualTo(expected.lastUpdated());
    }

    private void assertSchemaData(ItemSchemaData actual, ItemSchema expected)
    {
        assertThat(actual.externalId()).isEqualTo(expected.externalId());
        assertThat(actual.name()).isEqualTo(expected.name());

        Map<String, ItemSchemaField> idxExpectedFields = expected.fields().stream()
                .collect(Collectors.toMap(ItemSchemaField::name, Function.identity()));
        assertThat(actual.fields())
                .hasSize(expected.fields().size())
                .allSatisfy(actualField -> {
                    var expectedField = idxExpectedFields.get(actualField.name());
                    assertFieldData(actualField, expectedField);
                });
    }

    private void assertFieldData(ItemSchemaFieldData actual, ItemSchemaField expected)
    {
        assertThat(actual.name()).isEqualTo(expected.name());
        assertThat(actual.dataType().name()).isEqualTo(expected.dataType().name());
        assertThat(actual.comments()).isEqualTo(expected.comments());
    }
}
