package com.bitsmi.springbootshowcase.infrastructure.test.content;

import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.domain.content.model.DataType;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaField;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaRepositoryService;
import com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaFieldTestDataBuilder;
import com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaTestDataBuilder;
import com.bitsmi.springbootshowcase.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.infrastructure.testsupport.internal.ServiceIntegrationTest;
import com.bitsmi.springshowcase.contentservice.client.ContentServiceClient;
import com.bitsmi.springshowcase.contentservice.client.common.response.PagedResponse;
import com.bitsmi.springshowcase.contentservice.client.common.response.Pagination;
import com.bitsmi.springshowcase.contentservice.client.common.response.Sort;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaListOperation;
import com.bitsmi.springshowcase.contentservice.client.schema.SchemaSetApiBuilder;
import com.bitsmi.springshowcase.contentservice.client.schema.request.SchemaSetSelector;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test validations & cache implementations. The rest of use cases are tested in corresponding Unit Test
 * as both depend on Content Service Client mock
 */
@ServiceIntegrationTest
@TestPropertySource(
    locations = {
        "classpath:application-test.properties"
    },
    properties = {
        "spring.liquibase.change-log=classpath:db/changelogs/infrastructure/test/content/empty_database_main.xml"
    }
)
class ItemSchemaRepositoryServiceIntTests
{
    @MockBean
    private ContentServiceClient contentServiceClient;

    @Autowired
    private IItemSchemaRepositoryService itemSchemaRepositoryService;

    /*---------------------------*
     * VALIDATION TESTS
     *---------------------------*/
    @Test
    @DisplayName("createSchema should throw an ConstraintViolationException given incomplete input data")
    void createSchemaTest1()
    {
        final ItemSchema schema = ItemSchema.builder()
                .externalId(null)
                .name("Test schema")
                .fields(Set.of(
                        ItemSchemaFieldTestDataBuilder.builder().stringField().name(null).dataType(null).build()
                    )
                )
                .build();

        ConstraintViolationException e = catchThrowableOfType(() -> {
            itemSchemaRepositoryService.createItemSchema(schema);
        }, ConstraintViolationException.class);

        assertThat(e).isNotNull();
        // externalId, field[0].name; field[0].dataType
        assertThat(e.getConstraintViolations()).hasSize(3)
                .map(ConstraintViolation::getMessageTemplate)
                .allMatch("{jakarta.validation.constraints.NotNull.message}"::equals);;
    }

    @Test
    @DisplayName("updateSchema should throw an ConstraintViolationException given incomplete input data")
    void updateSchemaTest1()
    {
        final ItemSchema schema = ItemSchemaTestDataBuilder.builder()
                .emptySchema1()
                .externalId(null)
                .name("Modified schema")
                .fields(Set.of(
                        // Field1 removed
                        ItemSchemaField.builder().comments("Sample field comment").build(),
                        ItemSchemaField.builder().dataType(DataType.STRING).name("field3").build()
                    )
                )
                .build();

        ConstraintViolationException e = catchThrowableOfType(() -> {
            itemSchemaRepositoryService.updateItemSchema(schema);
        }, ConstraintViolationException.class);

        assertThat(e).isNotNull();
        // externalId, field[0].name; field[0].dataType
        assertThat(e.getConstraintViolations()).hasSize(3)
                .map(ConstraintViolation::getMessageTemplate)
                .allMatch("{jakarta.validation.constraints.NotNull.message}"::equals);
    }

    @Test
    @DisplayName("updateSchema should throw an ConstraintViolationException given missing update data (ID)")
    void updateSchemaTest2()
    {
        final ItemSchema schema = ItemSchemaTestDataBuilder.builder()
                .emptySchema1()
                .id(null)
                .name("Modified schema")
                .fields(Set.of(
                        // Field1 removed
                        ItemSchemaField.builder().dataType(DataType.NUMBER).name("field2").comments("Sample field comment").build(),
                        ItemSchemaField.builder().dataType(DataType.STRING).name("field3").build()
                    )
                )
                .build();

        ConstraintViolationException e = catchThrowableOfType(() -> {
            itemSchemaRepositoryService.updateItemSchema(schema);
        }, ConstraintViolationException.class);

        assertThat(e).isNotNull();
        assertThat(e.getConstraintViolations()).hasSize(1)
                .map(ConstraintViolation::getMessageTemplate)
                .containsExactly("ID not specified");
    }

    /*---------------------------*
     * CACHES
     *---------------------------*/
    @Test
    @DisplayName("findItemSchemaByExternalId should cache results given consecutive calls with same externalId")
    void cacheTest1()
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
                    PagedResponse.<com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema>builder()
                            .content(List.of(expectedSchema))
                            .pagination(Pagination.of(0, 10, Sort.UNSORTED))
                            .pageCount(1)
                            .totalCount(1)
                            .totalPages(1)
                            .build()
                );

        Optional<ItemSchema> optItemSchema1 = itemSchemaRepositoryService.findItemSchemaByExternalId(ItemSchemaTestDataBuilder.EXTERNAL_ID_SCHEMA1);
        Optional<ItemSchema> optItemSchema2 = itemSchemaRepositoryService.findItemSchemaByExternalId(ItemSchemaTestDataBuilder.EXTERNAL_ID_SCHEMA1);

        // Only 1 interaction
        verify(contentServiceClient).schemas(expectedSelector);

        assertThat(optItemSchema1).isPresent()
                .hasValueSatisfying(actualSchema -> assertSchema(actualSchema, expectedSchema));
        assertThat(optItemSchema2).isPresent()
                .hasValueSatisfying(actualSchema -> assertSchema(actualSchema, expectedSchema));
    }

    /*---------------------------*
     * CONFIG AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ InfrastructureModuleConfig.class })
    @IgnoreOnComponentScan
    static class TestConfig
    {
        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

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
}
