package com.bitsmi.springbootshowcase.infrastructure.test.content;

import static org.assertj.core.api.Assertions.assertThat;

import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.infrastructure.content.repository.ITagRepository;
import com.bitsmi.springbootshowcase.infrastructure.testsupport.internal.ServiceIntegrationTest;
import com.bitsmi.springbootshowcase.domain.content.model.Item;
import com.bitsmi.springbootshowcase.domain.content.model.ItemStatus;
import com.bitsmi.springbootshowcase.domain.content.model.Tag;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemRepositoryService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@ServiceIntegrationTest
@TestPropertySource(
    locations = {
        "classpath:application-test.properties"
    },
    properties = {
        "spring.liquibase.change-log=classpath:db/changelogs/infrastructure/test/content/item_repository_service_tests_main.xml"
    }
)
public class ItemRepositoryServiceIntTests
{
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
