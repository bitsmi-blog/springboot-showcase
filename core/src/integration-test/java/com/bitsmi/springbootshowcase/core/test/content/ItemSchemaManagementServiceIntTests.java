package com.bitsmi.springbootshowcase.core.test.content;

import com.bitsmi.springbootshowcase.core.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.core.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.core.content.IItemSchemaManagementService;
import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import com.bitsmi.springbootshowcase.core.content.model.DataType;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchemaField;
import com.bitsmi.springbootshowcase.core.content.repository.IItemSchemaRepository;
import com.bitsmi.springbootshowcase.core.test.content.support.ItemSchemaFieldTestDataBuilder;
import com.bitsmi.springbootshowcase.core.test.content.support.ItemSchemaTestDataBuilder;
import com.bitsmi.springbootshowcase.core.test.util.ServiceIntegrationTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
        "spring.liquibase.change-log=classpath:db/changelogs/core/test/content/item_schema_management_service_tests.xml"
})
@Tag("IntegrationTest")
public class ItemSchemaManagementServiceIntTests
{
    @Autowired
    private IItemSchemaManagementService itemSchemaManagementService;

    /*---------------------------*
     * FIND ALL
     *---------------------------*/
    @Test
    @DisplayName("findSchemaById should return all schemas data")
    public void findAllSchemasTest1()
    {
        final List<ItemSchema> schemas = itemSchemaManagementService.findAllSchemas();

        assertThat(schemas).hasSize(1).allSatisfy(schema -> {
            assertThat(schema.id()).isEqualTo(1001L);
            assertThat(schema.externalId()).isEqualTo("schema-1");
            assertThat(schema.name()).isEqualTo("Dummy Schema 1");
            assertThat(schema.creationDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.lastUpdated()).isEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0));
            assertThat(schema.fields()).hasSize(2);
        });
    }

    /*---------------------------*
     * FIND BY ID
     *---------------------------*/
    @Test
    @DisplayName("findSchemaById should return data given id when it exists")
    public void findSchemaByIdTest1()
    {
        final Optional<ItemSchema> optSchema = itemSchemaManagementService.findSchemaById(1001L);

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
        final Optional<ItemSchema> optSchema = itemSchemaManagementService.findSchemaById(9999L);

        assertThat(optSchema).isNotPresent();
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

        ItemSchema createdSchema = itemSchemaManagementService.createSchema(schema);

        assertItemSchema(createdSchema, schema);
    }

    @Test
    @DisplayName("createSchema should throw an ElementAlreadyExistsException when schema already exists")
    public void createSchemaTest2()
    {
        final ItemSchema schema = ItemSchemaTestDataBuilder.schema1();

        assertThatExceptionOfType(ElementAlreadyExistsException.class)
                .isThrownBy(() -> {
                    itemSchemaManagementService.createSchema(schema);
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
                    itemSchemaManagementService.createSchema(schema);
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


        ItemSchema createdSchema = itemSchemaManagementService.updateSchema(schema);

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
                    itemSchemaManagementService.updateSchema(schema);
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
            itemSchemaManagementService.updateSchema(schema);
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
            itemSchemaManagementService.updateSchema(schema);
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
    @Import(ValidationAutoConfiguration.class)
    @ComponentScan(basePackageClasses = IItemSchemaManagementService.class)
    @EnableJpaRepositories(basePackageClasses = {
            IItemSchemaRepository.class
    })
    @EntityScan(basePackageClasses = {
            // content
            ItemSchemaEntity.class
    })
    static class TestConfig
    {

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
