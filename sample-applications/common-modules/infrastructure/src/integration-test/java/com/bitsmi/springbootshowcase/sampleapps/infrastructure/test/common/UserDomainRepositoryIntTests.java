package com.bitsmi.springbootshowcase.sampleapps.infrastructure.test.common;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.Authority;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserGroup;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserGroupObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.entity.UserEntity;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.repository.UserRepository;
import com.bitsmi.springbootshowcase.sampleapps.infrastructure.testsupport.internal.ServiceIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceIntegrationTest
@TestPropertySource(
        properties = {
                "spring.datasource.url = jdbc:tc:postgresql:16.0:///test-database",
                "spring.liquibase.change-log=classpath:db/changelogs/infrastructure/test/common/user_domain_repository_tests.xml",
                "spring.datasource.driver-class-name = org.testcontainers.jdbc.ContainerDatabaseDriver"
        }
)
class UserDomainRepositoryIntTests
{
    // Singleton container. Don't start at every test using @Container annotation
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag("16.0"));

    @Autowired
    private UserDomainRepository userRepositoryService;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setUpAll()
    {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag("16.0"));
        postgres.start();
    }

    @AfterAll
    static void tearDownAll()
    {
        postgres.stop();
    }

    @Test
    @DisplayName("countAllUsers should return existing users count")
    void countAllUsersTest1()
    {
        Long count = userRepositoryService.countAllUsers();

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("""
            findUserSummaryByUsername
             should return user data
             given an existent user
             when his id is provided
            """
    )
    void findUserByIdTest1()
    {
        Optional<User> optUser = userRepositoryService.findUserById(1001L);

        assertThat(optUser).isNotEmpty().hasValueSatisfying(this::assertUser1001);
    }

    @Test
    @DisplayName("""
            findUserByUsername
             should return user data
             given an existent user
             when his username is provided
            """
    )
    void findUserByUsernameTest1()
    {
        Optional<User> optUser = userRepositoryService.findUserByUsername("john.admin");

        assertThat(optUser).isNotEmpty().hasValueSatisfying(this::assertUser1001);
    }

    @Test
    @DisplayName("""
            createUser
             should create a new user
             given no previous matching user exists
            """
    )
    void createUserTest1()
    {
        final LocalDateTime controlDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        final User expectedUser = UserObjectMother.builder()
                .aNonExistingUser()
                .id(null)
                .groups(
                        UserGroupObjectMother.user(),
                        UserGroupObjectMother.admin()
                )
                .creationDate(null)
                .lastUpdated(null)
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

    @Test
    @DisplayName("""
            updateUser
             should create a update user
             given existing user
            """
    )
    void updateUserTest1()
    {
        final LocalDateTime controlDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        final User expectedUser = UserObjectMother.builder()
                .aNormalUser()
                .completeName("Jane Modified")
                .groups(
                        UserGroupObjectMother.user(),
                        UserGroupObjectMother.admin()
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
            assertThat(userToAssert.creationDate()).isEqualTo(expectedUser.creationDate());
            assertThat(userToAssert.lastUpdated().truncatedTo(ChronoUnit.MINUTES)).isEqualTo(controlDate);
        };

        User actualUser = userRepositoryService.updateUser(expectedUser.id(), expectedUser);

        assertConsumer.accept(actualUser);

        Optional<User> optActualUser = userRepositoryService.findUserByUsername(expectedUser.username());

        assertThat(optActualUser).isPresent().hasValueSatisfying(assertConsumer);
    }

    @Test
    @DisplayName("""
            deleteUser
             should delete user
             given existing user
            """
    )
    void deleteUserTest1()
    {
        final Long providedUserId = 1001L;

        userRepositoryService.deleteUser(providedUserId);

        Optional<UserEntity> userOpt = userRepository.findById(providedUserId);

        assertThat(userOpt).isEmpty();
    }

    /*---------------------------*
     * HELPERS
     *---------------------------*/
    private void assertUser1001(User user)
    {
        LocalDateTime controlDate = LocalDateTime.of(LocalDate.of(2024, 1, 1), LocalTime.MIN);

        assertThat(user.id()).isEqualTo(1001);
        assertThat(user.username()).isEqualTo("john.admin");
        assertThat(user.password()).isEqualTo("{bcrypt}$2a$10$Tkgbh44XuwiFNGC/mel.h.ZTONgR2.H1N0L..Xk/JVh0.68kYga/a");
        assertThat(user.completeName()).isEqualTo("John Admin");
        assertThat(user.active()).isTrue();
        assertThat(user.groups()).hasSize(2);
        assertThat(user.groups()).filteredOn(userGroups -> UserConstants.USER_GROUP_USER.equals(userGroups.name()))
                .isNotEmpty()
                .flatMap(UserGroup::authorities)
                .extracting(Authority::name)
                .containsExactlyInAnyOrder("user.permission1", "user.permission2");
        assertThat(user.groups()).filteredOn(userGroups -> UserConstants.USER_GROUP_ADMIN.equals(userGroups.name()))
                .isNotEmpty()
                .flatMap(UserGroup::authorities)
                .extracting(Authority::name)
                .containsExactlyInAnyOrder("admin.permission1", "admin.permission2");
        assertThat(user.creationDate()).isEqualTo(controlDate);
        assertThat(user.lastUpdated()).isEqualTo(controlDate);
    }
}
