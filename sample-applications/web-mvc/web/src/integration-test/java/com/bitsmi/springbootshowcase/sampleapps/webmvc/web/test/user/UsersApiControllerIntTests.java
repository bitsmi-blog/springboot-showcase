package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.user;

import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.UserRegistryApplicationServiceMocker;
import com.bitsmi.springbootshowcase.sampleapps.application.testsupport.common.UserRetrievalApplicationServiceMocker;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response.PaginatedResponse;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response.Pagination;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response.Sort;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.WebTestBase;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.internal.ControllerIntegrationTest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.request.CreateUserRequestObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.request.UpdateUserRequestObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.response.UserObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.ChangeUserPasswordRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.CreateUserRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.request.UpdateUserRequest;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerIntegrationTest
@WithUserDetails("john.doe")
class UsersApiControllerIntTests extends WebTestBase {

    private static final String USERS_ENDPOINT = "/api/users";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private UserRetrievalApplicationServiceMocker userRetrievalApplicationServiceMocker;

    @Autowired
    private UserRegistryApplicationServiceMocker userRegistryApplicationServiceMocker;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Nested
    @DisplayName("Get users")
    class GetUsersTests {

        @Test
        @DisplayName("""
            given existing users
             should return paginated data
             when no pagination provided
            """
        )
        void test1() throws Exception {
            var expectedResponse = PaginatedResponse.<User>builder()
                    .data(
                            List.of(
                                    UserObjectMother.fromModel(com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.anAdminUser()),
                                    UserObjectMother.fromModel(com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNormalUser())
                            )
                    )
                    .pagination(Pagination.of(0, 25, Sort.UNSORTED))
                    .pageCount(2)
                    .totalPages(1)
                    .totalCount(2)
                    .totalPages(1)
                    .build();
            final String expectedResponseAsString = jsonMapper.writeValueAsString(expectedResponse);

            com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination pagination = com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Pagination.of(0, 25, com.bitsmi.springbootshowcase.sampleapps.domain.common.dto.Sort.UNSORTED);
            userRetrievalApplicationServiceMocker.whenFindAllUsersByPaginationThenReturnPaginatedData(
                    pagination,
                    PaginatedData.<com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User>builder()
                            .data(
                                    List.of(
                                            com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.anAdminUser(),
                                            com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNormalUser()
                                    )
                            )
                            .pagination(pagination)
                            .pageCount(2)
                            .totalPages(1)
                            .totalCount(2)
                            .totalPages(1)
                            .build()
            );

            mockMvc.perform(get(USERS_ENDPOINT)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponseAsString));
        }

        @Test
        @DisplayName("""
            given existing users
             should return paginated data
             when provided page and size
            """
        )
        void test2() throws Exception {
            var expectedResponse = PaginatedResponse.<User>builder()
                    .data(
                            List.of(
                                    UserObjectMother.fromModel(com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.anAdminUser()),
                                    UserObjectMother.fromModel(com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNormalUser())
                            )
                    )
                    .pagination(Pagination.of(0, 10, Sort.UNSORTED))
                    .pageCount(2)
                    .totalPages(1)
                    .totalCount(2)
                    .totalPages(1)
                    .build();
            final String expectedResponseAsString = jsonMapper.writeValueAsString(expectedResponse);

            mockMvc.perform(get(USERS_ENDPOINT)
                            .queryParam("page", "0")
                            .queryParam("pageSize", "10")
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponseAsString));
        }
    }

    @Nested
    @DisplayName("Get user")
    class GetUserTests {

        @Test
        @DisplayName("""
            given an existing user
             should return data
             when its ID is provided
            """
        )
        void test1() throws Exception {
            final User expectedResponse = UserObjectMother.fromModel(com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNormalUser());
            final String expectedResponseAsString = jsonMapper.writeValueAsString(expectedResponse);

            mockMvc.perform(get(USERS_ENDPOINT + "/{id}", expectedResponse.id())
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponseAsString));
        }

        @Test
        @DisplayName("""
            should return not found
             when an unknown ID is provided
            """
        )
        void test2() throws Exception {
            mockMvc.perform(get(USERS_ENDPOINT + "/{id}", 9999L)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Create user")
    class CreateUserPasswordTests {
        @Test
        @DisplayName("""
            given a non existing user
             should return created
            """
        )
        void test1() throws Exception {
            final com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User user = com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNonExistingUser();
            final User expectedResponse = UserObjectMother.fromModel(user);
            final String expectedResponseAsString = jsonMapper.writeValueAsString(expectedResponse);
            final CreateUserRequest request = CreateUserRequestObjectMother.fromModelWithPassword(user,"foobar");
            final String requestAsString = jsonMapper.writeValueAsString(request);

            mockMvc.perform(post(USERS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestAsString)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(expectedResponseAsString));
        }

        @Test
        @DisplayName("""
            given an existing user
             should return conflict
            """
        )
        void test2() throws Exception {
            final CreateUserRequest request = CreateUserRequestObjectMother.fromModelWithPassword(
                    com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNonExistingUser(),
                    "foobar"
            );
            final String requestAsString = jsonMapper.writeValueAsString(request);

            userRegistryApplicationServiceMocker.whenCreateUserWithUsernameThenThrowElementAlreadyExistsException(
                    com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.A_NON_EXISTING_USER.username()
            );

            mockMvc.perform(post(USERS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestAsString)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("Update user")
    class UpdateUserPasswordTests {

        @Test
        @DisplayName("""
            given a existing user
             should return ok
            """
        )
        void test1() throws Exception {
            final com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User user = com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNormalUser();
            final User expectedResponse = UserObjectMother.fromModel(user);
            final String expectedResponseAsString = jsonMapper.writeValueAsString(expectedResponse);
            final UpdateUserRequest request = UpdateUserRequestObjectMother.fromModel(user);
            final String requestAsString = jsonMapper.writeValueAsString(request);

            mockMvc.perform(put(USERS_ENDPOINT + "/{id}", user.id())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestAsString)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(expectedResponseAsString));
        }

        @Test
        @DisplayName("""
            given a non existing user
             should return not found
            """
        )
        void test2() throws Exception {
            final com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User user = com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNonExistingUser();
            final UpdateUserRequest request = UpdateUserRequestObjectMother.fromModel(user);
            final String requestAsString = jsonMapper.writeValueAsString(request);

            userRegistryApplicationServiceMocker.whenUpdateUserWithIdThenThrowElementNotFoundExistsException(user.id());

            mockMvc.perform(put(USERS_ENDPOINT + "/{id}", user.id())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestAsString)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Change user password")
    class ChangeUserPasswordTests {

        @Test
        @DisplayName("""
            given an existing user
             should return no content
            """
        )
        void test1() throws Exception {
            final ChangeUserPasswordRequest request = ChangeUserPasswordRequest.builder()
                    .password("foobar".toCharArray())
                    .build();
            final String requestAsString = jsonMapper.writeValueAsString(request);

            mockMvc.perform(post(USERS_ENDPOINT + "/{id}/password", com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.A_NORMAL_USER.id())
                            .content(requestAsString)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("""
            given a non existing user
             should return not found
            """
        )
        void test2() throws Exception {
            final Long providedUserId = com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.A_NON_EXISTING_USER.id();
            final ChangeUserPasswordRequest request = ChangeUserPasswordRequest.builder()
                    .password("foobar".toCharArray())
                    .build();
            final String requestAsString = jsonMapper.writeValueAsString(request);

            userRegistryApplicationServiceMocker.whenChangeUserPasswordThenThrowElementNotFoundException(providedUserId);

            mockMvc.perform(post(USERS_ENDPOINT + "/{id}/password", providedUserId)
                            .content(requestAsString)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Delete user")
    class DeleteUserTests {
        @Test
        @DisplayName("""
            should return no content
            """
        )
        void test1() throws Exception {
            final Long providedUserId = com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.A_NORMAL_USER.id();
            mockMvc.perform(delete(USERS_ENDPOINT + "/{id}", providedUserId)
                            .with(testSecurityContext())
                    )
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }
    }
}
