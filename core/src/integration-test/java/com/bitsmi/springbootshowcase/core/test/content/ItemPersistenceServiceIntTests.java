package com.bitsmi.springbootshowcase.core.test.content;

import static org.assertj.core.api.Assertions.assertThat;

import com.bitsmi.springbootshowcase.core.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.core.config.CoreConfig;
import com.bitsmi.springbootshowcase.core.content.repository.ITagRepository;
import com.bitsmi.springbootshowcase.core.testutil.ServiceIntegrationTest;
import com.bitsmi.springbootshowcase.domain.content.model.Item;
import com.bitsmi.springbootshowcase.domain.content.model.ItemStatus;
import com.bitsmi.springbootshowcase.domain.content.model.Tag;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemPersistenceService;
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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@ServiceIntegrationTest
@TestPropertySource(properties = {
        "spring.liquibase.change-log=classpath:db/changelogs/core/test/content/item_persistence_service_tests.xml"
})
public class ItemPersistenceServiceIntTests
{
    @Autowired
    private IItemPersistenceService itemPersistenceService;
    @Autowired
    private ITagRepository tagRepository;

    @Test
    @DisplayName("Find item should return item data when it exists for the given ID")
    public void findItemByIdTest1()
    {
        LocalDateTime controlDate = LocalDateTime.of(LocalDate.of(2023, 1, 1), LocalTime.MIN);

        Optional<Item> optItem = itemPersistenceService.findItemById(1001L);

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
        Optional<Item> optItem = itemPersistenceService.findItemById(99999L);
        assertThat(optItem).isEmpty();
    }

    @Test
    @DisplayName("Add item tag should save item-tag relationship")
    public void addItemTagTest1()
    {
        Item item = itemPersistenceService.findItemById(1001L).orElseThrow();
        Tag tagToAdd = Tag.builder()
                .id(1005L)
                .build();
        assertThat(item.tags()).hasSize(3);

        Item savedItem = itemPersistenceService.addItemTag(item, tagToAdd);
        assertThat(savedItem.tags()).hasSize(4);

        Item retrievedItem = itemPersistenceService.findItemById(1001L).orElseThrow();
        assertThat(retrievedItem.tags()).hasSize(4);
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @Import({ CoreConfig.class })
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
