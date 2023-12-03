package com.bitsmi.springbootshowcase.core.test.content;

import com.bitsmi.springbootshowcase.core.common.repository.impl.ICustomBaseRepositoryImpl;
import com.bitsmi.springbootshowcase.core.content.IItemManagementService;
import com.bitsmi.springbootshowcase.core.content.entity.ItemEntity;
import com.bitsmi.springbootshowcase.core.content.model.Item;
import com.bitsmi.springbootshowcase.core.content.model.ItemStatus;
import com.bitsmi.springbootshowcase.core.content.model.Tag;
import com.bitsmi.springbootshowcase.core.content.repository.IItemRepository;
import com.bitsmi.springbootshowcase.core.content.repository.ITagRepository;
import com.bitsmi.springbootshowcase.core.test.util.ServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceIntegrationTest
@TestPropertySource(properties = {
        "spring.liquibase.change-log=classpath:db/changelogs/core/test/content/item_management_service_tests.xml"
})
public class ItemManagementServiceIntTests
{
    @Autowired
    private IItemManagementService itemManagementService;
    @Autowired
    private ITagRepository tagRepository;

    @Test
    @DisplayName("Find item should return item data when it exists for the given ID")
    public void findItemByIdTest1()
    {
        LocalDateTime controlDate = LocalDateTime.of(LocalDate.of(2023, 1, 1), LocalTime.MIN);

        Optional<Item> optItem = itemManagementService.findItemById(1001L);

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
        Optional<Item> optItem = itemManagementService.findItemById(99999L);
        assertThat(optItem).isEmpty();
    }

    @Test
    @DisplayName("Add item tag should save item-tag relationship")
    public void addItemTagTest1()
    {
        Item item = itemManagementService.findItemById(1001L).orElseThrow();
        Tag tagToAdd = Tag.builder()
                .id(1005L)
                .build();
        assertThat(item.tags()).hasSize(3);

        Item savedItem = itemManagementService.addItemTag(item, tagToAdd);
        assertThat(savedItem.tags()).hasSize(4);

        Item retrievedItem = itemManagementService.findItemById(1001L).orElseThrow();
        assertThat(retrievedItem.tags()).hasSize(4);
    }

    /*---------------------------*
     * SETUP AND HELPERS
     *---------------------------*/
    @TestConfiguration
    @ComponentScan(basePackageClasses = IItemManagementService.class)
    @EnableJpaRepositories(basePackageClasses = {
            IItemRepository.class
        },
        repositoryBaseClass = ICustomBaseRepositoryImpl.class
    )
    @EntityScan(basePackageClasses = {
            // content
            ItemEntity.class
    })
    static class TestConfig
    {

    }
}
