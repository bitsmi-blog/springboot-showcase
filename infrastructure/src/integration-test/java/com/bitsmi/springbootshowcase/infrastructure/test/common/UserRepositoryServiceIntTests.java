package com.bitsmi.springbootshowcase.infrastructure.test.common;

import com.bitsmi.springbootshowcase.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.domain.common.model.Authority;
import com.bitsmi.springbootshowcase.domain.common.model.User;
import com.bitsmi.springbootshowcase.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserGroupRepositoryService;
import com.bitsmi.springbootshowcase.domain.common.spi.IUserRepositoryService;
import com.bitsmi.springbootshowcase.domain.common.util.IgnoreOnComponentScan;
import com.bitsmi.springbootshowcase.infrastructure.config.InfrastructureModuleConfig;
import com.bitsmi.springbootshowcase.infrastructure.testsupport.internal.ServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceIntegrationTest
@TestPropertySource(
    locations = {
        "classpath:application-test.properties"
    },
    properties = {
        "spring.liquibase.change-log=classpath:db/changelogs/infrastructure/test/common/user_repository_service_tests.xml"
    }
)
public class UserRepositoryServiceIntTests
{
    @Autowired
    private IUserRepositoryService userRepositoryService;
    @Autowired
    private IUserGroupRepositoryService userGroupRepositoryService;

    @Test
    @DisplayName("countAllUsers should return existing users count")
    public void countAllUsersTest1()
    {
        Long count = userRepositoryService.countAllUsers();

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("findUserSummaryByUsername should return user data when it exists for the given username")
    public void findUserByUsernameTest1()
    {
        LocalDateTime controlDate = LocalDateTime.of(LocalDate.of(2023, 1, 1), LocalTime.MIN);

        Optional<User> optUser = userRepositoryService.findUserByUsername("john.doe");

        assertThat(optUser).isNotEmpty().hasValueSatisfying(user -> {
            assertThat(user.id()).isEqualTo(1001);
            assertThat(user.username()).isEqualTo("john.doe");
            assertThat(user.password()).isEqualTo("{bcrypt}$2a$10$I2NL2EwOcbl9PIhgPiF/XeVXQ.yfErH11UQemNW21LBk.iaIpa44.");
            assertThat(user.completeName()).isEqualTo("John Doe");
            assertThat(user.active()).isTrue();
            assertThat(user.groups())
                    .hasSize(1)
                    .first()
                    .satisfies(userGroup -> {
                        assertThat(userGroup.name()).isEqualTo(UserConstants.USER_GROUP_USER);
                        assertThat(userGroup.authorities())
                                .extracting(Authority::name)
                                .containsExactlyInAnyOrder("user.permission1", "user.permission2");
                    });
            assertThat(user.creationDate()).isEqualTo(controlDate);
            assertThat(user.lastUpdated()).isEqualTo(controlDate);
        });
    }

    @Test
    @DisplayName("findUserSummaryByUsername should return user summary data when it exists for the given username")
    public void findUserSummaryByUsernameTest1()
    {
        Optional<UserSummary> optUserSummary = userRepositoryService.findUserSummaryByUsername("john.doe");

        assertThat(optUserSummary).isNotEmpty().hasValueSatisfying(user -> {
            assertThat(user.username()).isEqualTo("john.doe");
            assertThat(user.completeName()).isEqualTo("John Doe");
        });
    }

    @Test
    @DisplayName("createUser should create a new user given username, password and groups")
    public void createUserTest1()
    {
        final LocalDateTime controlDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        final User expectedUser = User.builder()
            .username("test.user")
            .password("test.password")
            .completeName("Test User")
            .active(Boolean.TRUE)
            .groups(Set.of(
                    userGroupRepositoryService.findUserGroupByName(UserConstants.USER_GROUP_USER).get(),
                    userGroupRepositoryService.findUserGroupByName(UserConstants.USER_GROUP_ADMIN).get()
                )
            )
            .build();

        Consumer<User> assertConsumer = userToAssert -> {
            assertThat(userToAssert.username()).isEqualTo(expectedUser.username());
            assertThat(userToAssert.password()).isEqualTo(expectedUser.password());
            assertThat(userToAssert.groups()).hasSize(2);
            assertThat(userToAssert.groups()).filteredOn(userGroups -> UserConstants.USER_GROUP_USER.equals(userGroups.name()))
                    .isNotEmpty()
                    .flatMap(UserGroup::authorities)
                    .extracting(Authority::name)
                    .containsExactlyInAnyOrder("user.permission1", "user.permission2");
            assertThat(userToAssert.groups()).filteredOn(userGroups -> UserConstants.USER_GROUP_ADMIN.equals(userGroups.name()))
                    .isNotEmpty()
                    .flatMap(UserGroup::authorities)
                    .extracting(Authority::name)
                    .containsExactlyInAnyOrder("admin.permission1", "admin.permission2");
            assertThat(userToAssert.active()).isTrue();
            assertThat(userToAssert.creationDate().truncatedTo(ChronoUnit.MINUTES)).isEqualTo(controlDate);
            assertThat(userToAssert.lastUpdated().truncatedTo(ChronoUnit.MINUTES)).isEqualTo(controlDate);
        };

        User actualUser = userRepositoryService.createUser(expectedUser);

        assertConsumer.accept(actualUser);

        Optional<User> optActualUser = userRepositoryService.findUserByUsername(expectedUser.username());

        assertThat(optActualUser).isPresent().hasValueSatisfying(assertConsumer);
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
