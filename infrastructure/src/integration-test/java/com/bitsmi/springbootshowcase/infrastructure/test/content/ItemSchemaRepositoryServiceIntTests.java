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

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

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
     * CREATE SCHEMA
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

    /*---------------------------*
     * UPDATE SCHEMA
     *---------------------------*/
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
}
