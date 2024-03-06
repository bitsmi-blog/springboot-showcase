package com.bitsmi.springbootshowcase.infrastructure.test.content;

import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort;
import com.bitsmi.springbootshowcase.domain.common.dto.Sort.Order;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.domain.content.model.DataType;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaField;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaSummary;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaFieldTestDataBuilder;
import com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaTestDataBuilder;
import com.bitsmi.springbootshowcase.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.infrastructure.testsupport.internal.ServiceIntegrationTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@ServiceIntegrationTest
@TestPropertySource(properties = {
        "spring.liquibase.change-log=classpath:db/changelogs/infrastructure/test/content/item_schema_persistence_service_tests.xml"
})
public class ItemSchemaPersistenceServiceIntTests
{
    @Autowired
    private IItemSchemaPersistenceService itemSchemaPersistenceService;

    /*---------------------------*
     * FIND ALL
     *---------------------------*/
    @Test
    @DisplayName("findSchemaById should return all schemas data")
    public void findAllSchemasTest1()
    {
        final List<ItemSchema> schemas = itemSchemaPersistenceService.findAllItemSchemas();

        assertThat(schemas).hasSize(11).allSatisfy(schema -> {
            assertThat(schema.id()).isNotNull();
            assertThat(schema.externalId()).startsWith("schema-");
            assertThat(schema.name()).startsWith("Dummy Schema");
            assertThat(schema.creationDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.lastUpdated()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.fields()).hasSize(2);
        });
    }

    /*---------------------------*
     * FIND SCHEMAS BY NAME LIKE
     *---------------------------*/
    @Test
    @DisplayName("findSchemasByNameStartWith should return first page results")
    public void findSchemasByNameStartWithTest1()
    {
        // Case in-sensitive
        PagedData<ItemSchema> schemasPage = itemSchemaPersistenceService.findSchemasByNameStartWith(
                "dummy",
                Pagination.of(0, 5, Sort.by(Order.desc("externalId")))
        );

        assertThat(schemasPage.pageCount()).isEqualTo(5);
        assertThat(schemasPage.totalPages()).isEqualTo(3);
        assertThat(schemasPage.totalCount()).isEqualTo(11);
        assertThat(schemasPage.pagination().pageNumber()).isEqualTo(0);
        assertThat(schemasPage.pagination().pageSize()).isEqualTo(5);
        assertThat(schemasPage.isFirstPage()).isTrue();
        assertThat(schemasPage.isLastPage()).isFalse();
        assertThat(schemasPage.content()
                .stream()
                .map(ItemSchema::externalId)
                .toList())
                .containsExactly("schema-9", "schema-8", "schema-7", "schema-6", "schema-5");

    }

    @Test
    @DisplayName("findSchemasByNameStartWith should return last page results")
    public void findSchemasByNameStartWithTest2()
    {
        // Case in-sensitive
        PagedData<ItemSchema> schemasPage = itemSchemaPersistenceService.findSchemasByNameStartWith(
                "DUMMY",
                Pagination.of(2, 5, Sort.UNSORTED)
        );

        assertThat(schemasPage.pageCount()).isEqualTo(1);
        assertThat(schemasPage.totalPages()).isEqualTo(3);
        assertThat(schemasPage.totalCount()).isEqualTo(11);
        assertThat(schemasPage.pagination().pageNumber()).isEqualTo(2);
        assertThat(schemasPage.pagination().pageSize()).isEqualTo(5);
        assertThat(schemasPage.isFirstPage()).isFalse();
        assertThat(schemasPage.isLastPage()).isTrue();
    }

    /*---------------------------*
     * FIND BY ID
     *---------------------------*/
    @Test
    @DisplayName("findSchemaById should return data given id when it exists")
    public void findSchemaByIdTest1()
    {
        final Optional<ItemSchema> optSchema = itemSchemaPersistenceService.findItemSchemaById(1001L);

        assertThat(optSchema).isPresent().hasValueSatisfying(schema -> {
            assertThat(schema.id()).isEqualTo(1001L);
            assertThat(schema.externalId()).isEqualTo("schema-1");
            assertThat(schema.name()).isEqualTo("Dummy Schema 1");
            assertThat(schema.creationDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.lastUpdated()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.fields()).hasSize(2);
        });
    }

    @Test
    @DisplayName("findSchemaById should return empty given id when it doesn't exists exists")
    public void findSchemaByIdTest2()
    {
        final Optional<ItemSchema> optSchema = itemSchemaPersistenceService.findItemSchemaById(9999L);

        assertThat(optSchema).isNotPresent();
    }

    /*---------------------------*
     * FIND SCHEMAS BY EXTERNAL ID
     *---------------------------*/
    @Test
    @DisplayName("findSchemaByExternalId should return first page results")
    public void findSchemaByExternalIdTest1()
    {
        // Case in-sensitive
        Optional<ItemSchema> optSchema = itemSchemaPersistenceService.findItemSchemaByExternalId("schema-1");

        assertThat(optSchema).isPresent().hasValueSatisfying(schema -> {
            assertThat(schema.id()).isEqualTo(1001L);
            assertThat(schema.externalId()).isEqualTo("schema-1");
            assertThat(schema.name()).isEqualTo("Dummy Schema 1");
            assertThat(schema.creationDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.lastUpdated()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.fields()).hasSize(2);
        });
    }

    /*---------------------------*
     * FIND SUMMARY
     *---------------------------*/
    @Test
    @DisplayName("findSchemaSummaryByExternalId should return data given id when it exists")
    public void findSchemaSummaryByExternalIdTest1()
    {
        final Optional<ItemSchemaSummary> optSchema = itemSchemaPersistenceService.findItemSchemaSummaryByExternalId("schema-1");

        assertThat(optSchema).isPresent().hasValueSatisfying(schema -> {
            assertThat(schema.externalId()).isEqualTo("schema-1");
            assertThat(schema.name()).isEqualTo("Dummy Schema 1");
            assertThat(schema.fieldsCount()).isEqualTo(2);
        });
    }

    @Test
    @DisplayName("findSchemaSummaryUsingQueryByExternalId should return data given id when it exists")
    public void findSchemaSummaryUsingQueryByExternalIdTest1()
    {
        final Optional<ItemSchemaSummary> optSchema = itemSchemaPersistenceService.findItemSchemaSummaryUsingQueryByExternalId("schema-1");

        assertThat(optSchema).isPresent().hasValueSatisfying(schema -> {
            assertThat(schema.externalId()).isEqualTo("schema-1");
            assertThat(schema.name()).isEqualTo("Dummy Schema 1");
            assertThat(schema.fieldsCount()).isEqualTo(2);
        });
    }

    /*---------------------------*
     * CREATE SCHEMA
     *---------------------------*/
    @Test
    @DisplayName("createSchema should create a new Schema")
    public void createSchemaTest1()
    {
        final ItemSchema schema = ItemSchema.builder()
                .externalId("test-schema")
                .name("Test schema")
                .fields(Set.of(ItemSchemaFieldTestDataBuilder.stringField(), ItemSchemaFieldTestDataBuilder.numericField()))
                .build();

        ItemSchema createdSchema = itemSchemaPersistenceService.createItemSchema(schema);

        assertItemSchema(createdSchema, schema);
    }

    @Test
    @DisplayName("createSchema should throw an ElementAlreadyExistsException when schema already exists")
    public void createSchemaTest2()
    {
        final ItemSchema schema = ItemSchemaTestDataBuilder.schema1();

        assertThatExceptionOfType(ElementAlreadyExistsException.class)
                .isThrownBy(() -> {
                    itemSchemaPersistenceService.createItemSchema(schema);
                });
    }

    @Test
    @DisplayName("createSchema should throw an ConstraintViolationException given incomplete input data")
    public void createSchemaTest3()
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
            itemSchemaPersistenceService.createItemSchema(schema);
        }, ConstraintViolationException.class);

        assertThat(e).isNotNull();
        // externalId, field[0].name; field[0].dataType
        assertThat(e.getConstraintViolations()).hasSize(3)
                .map(ConstraintViolation::getMessageTemplate)
                .allMatch("{jakarta.validation.constraints.NotNull.message}"::equals);;
    }

    /*---------------------------*
     * UPDATE SCHEMA
     *---------------------------*/
    @Test
    @DisplayName("updateSchema should update an existing Schema")
    public void updateSchemaTest1()
    {
        final ItemSchema schema = ItemSchemaTestDataBuilder.builder()
                .emptySchema1()
                .fields(Set.of(
                                // Field1 removed
                                ItemSchemaField.builder().dataType(DataType.NUMBER).name("field2").comments("Sample field comment").build(),
                                ItemSchemaField.builder().dataType(DataType.STRING).name("field3").build()
                        )
                )
                .build();


        ItemSchema createdSchema = itemSchemaPersistenceService.updateItemSchema(schema);

        assertItemSchema(createdSchema, schema);
    }

    @Test
    @DisplayName("updateSchema should throw an ElementNotFoundException when schema doesn't exists")
    public void updateSchemaTest2()
    {
        final ItemSchema schema = ItemSchemaTestDataBuilder.builder()
                .emptySchema1()
                .defaultFields()
                .id(9999L)
                .build();

        assertThatExceptionOfType(ElementNotFoundException.class)
                .isThrownBy(() -> {
                    // Non existing ID = 9999L
                    itemSchemaPersistenceService.updateItemSchema(schema);
                });
    }

    @Test
    @DisplayName("updateSchema should throw an ConstraintViolationException given incomplete input data")
    public void updateSchemaTest3()
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
            itemSchemaPersistenceService.updateItemSchema(schema);
        }, ConstraintViolationException.class);

        assertThat(e).isNotNull();
        // externalId, field[0].name; field[0].dataType
        assertThat(e.getConstraintViolations()).hasSize(3)
                .map(ConstraintViolation::getMessageTemplate)
                .allMatch("{jakarta.validation.constraints.NotNull.message}"::equals);
    }

    @Test
    @DisplayName("updateSchema should throw an ConstraintViolationException given missing update data")
    public void updateSchemaTest4()
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
            itemSchemaPersistenceService.updateItemSchema(schema);
        }, ConstraintViolationException.class);

        assertThat(e).isNotNull();
        assertThat(e.getConstraintViolations()).hasSize(1)
                .map(ConstraintViolation::getMessageTemplate)
                .containsExactly("ID not specified");
    }

    /*---------------------------*
     * SETUP AND HELPERS
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

    private void assertItemSchema(ItemSchema current, ItemSchema expected)
    {
        assertThat(current.id()).isNotNull();
        assertThat(current.creationDate()).isNotNull();
        assertThat(current.lastUpdated()).isNotNull();

        assertThat(current).extracting(
                        ItemSchema::name,
                        ItemSchema::externalId)
                .containsExactly(
                        expected.name(),
                        expected.externalId()
                );

        assertThat(current.fields()).hasSize(expected.fields().size());
        for(ItemSchemaField expectedField:expected.fields()) {
            assertThat(current.fields()).filteredOn(field -> expectedField.name().equals(field.name()))
                    .allSatisfy(currentField -> {
                        assertThat(currentField.id()).isNotNull();
                        assertThat(currentField.name()).isEqualTo(expectedField.name());
                        assertThat(currentField.dataType().name()).isEqualTo(expectedField.dataType().name());
                        assertThat(currentField.comments()).isEqualTo(expectedField.comments());
                        assertThat(currentField.creationDate()).isNotNull();
                        assertThat(currentField.lastUpdated()).isNotNull();
                    });
        }
    }
}
