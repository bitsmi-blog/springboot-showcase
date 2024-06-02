package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.test.user;

import com.bitsmi.springbootshowcase.sampleapps.application.common.IUserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service.IAuthenticationPrincipalService;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.UserApiController;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.UserDetailsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApiControllerTests
{
    @Mock
    private IAuthenticationPrincipalService authenticationPrincipalService;
    @Mock
    private IUserSummaryApplicationQuery userSummaryApplicationQuery;

    @InjectMocks
    private UserApiController userApiController;

    @Test
    @DisplayName("getDetails should return current username")
    public void getDetailsTest1()
    {
        final String expectedUsername = "john.doe";
        final String expectedCompleteName = "John Doe";

        final UserDetails userDetails = mock(UserDetails.class);
        final UserSummary userSummary = UserSummary.builder()
                .username(expectedUsername)
                .completeName(expectedCompleteName)
                .build();
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(authenticationPrincipalService.getAuthenticationPrincipal())
                .thenReturn(userDetails);
        when(userSummaryApplicationQuery.findUserSummaryByUsername(expectedUsername)).thenReturn(Optional.of(userSummary));

        UserDetailsResponse response = userApiController.getDetails();

        assertThat(response.username()).isEqualTo(expectedUsername);
        assertThat(response.completeName()).isEqualTo(expectedCompleteName);
    }
}
