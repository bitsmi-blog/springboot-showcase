package com.bitsmi.springbootshowcase.sampleapps.domain.test.common.impl;

import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserDomainFactory;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserGroupDomainRepository;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.impl.UserDomainFactoryImpl;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserGroupObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDomainFactoryImplTests {

    @Mock
    private UserGroupDomainRepository userGroupDomainRepositoryMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;

    private UserDomainFactory sut;

    @BeforeEach
    void setUp() {
        sut = new UserDomainFactoryImpl(userGroupDomainRepositoryMock, passwordEncoderMock);
    }

    @Test
    @DisplayName("createAdminUser should create a default admin user user with groups ADMIN, USER")
    void createAdminUserTest1() {
        final String expectedUsername = "a.username";
        final String givenPassword = "A_PASSWORD";
        final String expectedEncodedPassword = "ENCODED_PASSWORD";

        doReturn(expectedEncodedPassword)
                .when(passwordEncoderMock).encode(argThat(givenPassword::contentEquals));
        when(userGroupDomainRepositoryMock.findUserGroupByName(UserConstants.USER_GROUP_USER))
                .thenReturn(Optional.of(UserGroupObjectMother.USER));
        when(userGroupDomainRepositoryMock.findUserGroupByName(UserConstants.USER_GROUP_ADMIN))
                .thenReturn(Optional.of(UserGroupObjectMother.ADMIN));

        User actualUser = sut.createAdminUser(expectedUsername, givenPassword.toCharArray());

        assertThat(actualUser).extracting(User::username, User::password, User::completeName, User::groups, User::active)
                .containsExactly(expectedUsername, expectedEncodedPassword, "Admin", Set.of(UserGroupObjectMother.USER, UserGroupObjectMother.ADMIN), Boolean.TRUE);
    }

    @Test
    @DisplayName("createUser should create a user with group USER when no groups provided")
    void createUserTest1() {
        final String givenPassword = "A_PASSWORD";
        final String expectedEncodedPassword = "ENCODED_PASSWORD";
        final User expectedUser = UserObjectMother.aNormalUser();

        doReturn(expectedEncodedPassword)
                .when(passwordEncoderMock).encode(argThat(givenPassword::contentEquals));
        when(userGroupDomainRepositoryMock.findUserGroupByName(UserConstants.USER_GROUP_USER))
                .thenReturn(Optional.of(UserGroupObjectMother.USER));

        User actualUser = sut.createUser(
                expectedUser.username(),
                givenPassword.toCharArray(),
                expectedUser.completeName(),
                Collections.emptySet()
        );

        assertThat(actualUser).extracting(User::username, User::password, User::completeName, User::groups, User::active)
                .containsExactly(expectedUser.username(), expectedEncodedPassword, expectedUser.completeName(), Set.of(UserGroupObjectMother.USER), Boolean.TRUE);
    }

    @Test
    @DisplayName("createUser should throw a ElementNotFoundException when a non existing group is provided")
    void createUserTest2() {
        final String givenPassword = "A_PASSWORD";
        final String expectedEncodedPassword = "ENCODED_PASSWORD";
        final User expectedUser = UserObjectMother.aNormalUser();

        assertThatThrownBy(() -> sut.createUser(
                        expectedUser.username(),
                        givenPassword.toCharArray(),
                        expectedUser.completeName(),
                        Set.of("NON_VALID_GROUP")
                )
        )
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessage("Element (NON_VALID_GROUP) of type (com.bitsmi.inventoryclerk.server.domain.common.model.UserGroup) not found");
    }

    @Test
    @DisplayName("updateUser should update a user instance with provided data")
    void updateUserTest1() {
        final String expectedModifiedUsername = "modified.username";
        final String expectedModifiedCompleteName = "Modified User";
        final User givenUser = UserObjectMother.aNormalUser();

        when(userGroupDomainRepositoryMock.findUserGroupByName(UserConstants.USER_GROUP_USER))
                .thenReturn(Optional.of(UserGroupObjectMother.USER));

        User actualUser = sut.updatedUser(
                givenUser,
                expectedModifiedUsername,
                expectedModifiedCompleteName,
                Collections.emptySet()
        );

        assertThat(actualUser).extracting(User::username, User::password, User::completeName, User::groups, User::active)
                .containsExactly(expectedModifiedUsername, givenUser.password(), expectedModifiedCompleteName, Set.of(UserGroupObjectMother.USER), Boolean.TRUE);
    }

    @Test
    @DisplayName("updateUser should throw a ElementNotFoundException when a non existing group is provided")
    void updateUserTest2() {
        final User expectedUser = UserObjectMother.aNormalUser();

        assertThatThrownBy(() -> sut.updatedUser(
                        expectedUser,
                        "modified.username",
                        expectedUser.completeName(),
                        Set.of("NON_VALID_GROUP")
                )
        )
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessage("Element (NON_VALID_GROUP) of type (com.bitsmi.inventoryclerk.server.domain.common.model.UserGroup) not found");
    }

    @Test
    @DisplayName("encodePassword should return encoded password given a char array")
    void encodePasswordTest1() {
        final String givenPassword = "A_PASSWORD";
        final String expectedEncodedPassword = "ENCODED_PASSWORD";

        doReturn(expectedEncodedPassword)
                .when(passwordEncoderMock).encode(argThat(givenPassword::contentEquals));

        String actualEncodedPassword = sut.encodePassword(givenPassword.toCharArray());

        assertThat(actualEncodedPassword).isEqualTo(expectedEncodedPassword);
    }
}