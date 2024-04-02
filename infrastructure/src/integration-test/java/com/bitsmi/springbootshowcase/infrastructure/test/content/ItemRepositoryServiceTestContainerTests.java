package com.bitsmi.springbootshowcase.infrastructure.test.content;

import static org.assertj.core.api.Assertions.assertThat;

import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemRepositoryService;
import com.bitsmi.springbootshowcase.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.infrastructure.content.repository.ITagRepository;
import com.bitsmi.springbootshowcase.domain.content.model.Item;
import com.bitsmi.springbootshowcase.domain.content.model.ItemStatus;
import com.bitsmi.springbootshowcase.domain.content.model.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
// Test-managed transaction should be rolled back after the test method has completed.
@Rollback
@EnableAutoConfiguration
@AutoConfigureCache
@AutoConfigureDataJpa
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.liquibase.change-log=classpath:/db/changelogs/infrastructure/test/content/item_repository_service_tests.xml",
        "spring.datasource.url = jdbc:tc:postgresql:16.0:///test-database",
        "spring.datasource.driver-class-name = org.testcontainers.jdbc.ContainerDatabaseDriver"
})
@org.junit.jupiter.api.Tag("IntegrationTest")
public class ItemRepositoryServiceTestContainerTests
{
    @Container
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag("16.0"));

    @Autowired
    private IItemRepositoryService itemRepositoryService;
    @Autowired
    private ITagRepository tagRepository;

    @Test
    @DisplayName("Find item should return item data when it exists for the given ID")
    public void findItemByIdTest1()
    {
        LocalDateTime controlDate = LocalDateTime.of(LocalDate.of(2023, 1, 1), LocalTime.MIN);

        Optional<Item> optItem = itemRepositoryService.findItemById(1001L);

        assertThat(optItem).isNotEmpty().hasValueSatisfying(item -> {
            assertThat(item.name()).isEqualTo("Ready Item 1");
            assertThat(item.status()).isEqualTo(ItemStatus.READY);
            assertThat(item.tags()).hasSize(3);
            assertThat(item.creationDate()).isEqualTo(controlDate);
            assertThat(item.lastUpdated()).isEqualTo(controlDate);
        });
    }

    @Test
    @DisplayName("Find item should return empty when data doesn't exists for the given ID")
    public void findItemByExternalIdTest2()
    {
        Optional<Item> optItem = itemRepositoryService.findItemById(99999L);
        assertThat(optItem).isEmpty();
    }

    @Test
    @DisplayName("Add item tag should save item-tag relationship")
    public void addItemTagTest1()
    {
        Item item = itemRepositoryService.findItemById(1001L).orElseThrow();
        Tag tagToAdd = Tag.builder()
                .id(1005L)
                .build();
        assertThat(item.tags()).hasSize(3);

        Item savedItem = itemRepositoryService.addItemTag(item, tagToAdd);
        assertThat(savedItem.tags()).hasSize(4);

        Item retrievedItem = itemRepositoryService.findItemById(1001L).orElseThrow();
        assertThat(retrievedItem.tags()).hasSize(4);
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
