package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.user;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRegistryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.application.common.UserRetrievalApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.mapper.PaginationMapper;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.testsupport.user.controller.response.UserObjectMother;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.UserApiController;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.User;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.mapper.UserApiMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserApiControllerTests
{
    @Mock
    private UserRetrievalApplicationService userRetrievalApplicationService;
    @Mock
    private UserRegistryApplicationService userRegistryApplicationService;
    @Mock
    private UserApiMapper userApiMapper;
    @Mock
    private PaginationMapper paginationMapper;

    private UserApiController sut;

    @BeforeEach
    void setUp()
    {
        sut = new UserApiController(
                userRetrievalApplicationService,
                userRegistryApplicationService,
                userApiMapper,
                paginationMapper
        );
    }

    @Test
    @DisplayName("getDetails should return current username")
    void getDetailsTest1()
    {
        final com.bitsmi.springbootshowcase.sampleapps.domain.common.model.User expectedUser = com.bitsmi.springbootshowcase.sampleapps.domain.testsupport.common.model.UserObjectMother.aNormalUser();
        final User expectedResponse = UserObjectMother.fromModel(expectedUser);

        when(userRetrievalApplicationService.findUserById(expectedUser.id()))
                .thenReturn(Optional.of(expectedUser));
        when(userApiMapper.mapResponseFromModel(expectedUser))
                .thenReturn(expectedResponse);

        User actualResponse = sut.getUser(expectedUser.id());

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }
}
