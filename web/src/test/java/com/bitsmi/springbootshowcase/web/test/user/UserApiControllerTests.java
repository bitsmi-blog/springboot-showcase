package com.bitsmi.springbootshowcase.web.test.user;

import com.bitsmi.springbootshowcase.api.user.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.core.common.IUserManagementService;
import com.bitsmi.springbootshowcase.core.common.entity.IUserSummaryProjection;
import com.bitsmi.springbootshowcase.web.common.service.IAuthenticationPrincipalService;
import com.bitsmi.springbootshowcase.web.user.controller.UserApiControllerImpl;
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
    private IUserManagementService userManagementService;

    @InjectMocks
    private UserApiControllerImpl userApiController;

    @Test
    @DisplayName("getDetails should return current username")
    public void getDetailsTest1()
    {
        final String expectedUsername = "john.doe";
        final String expectedCompleteName = "John Doe";

        final UserDetails userDetails = mock(UserDetails.class);
        final IUserSummaryProjection userSummary = mock(IUserSummaryProjection.class);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(userSummary.getCompleteName()).thenReturn(expectedCompleteName);
        when(authenticationPrincipalService.getAuthenticationPrincipal())
                .thenReturn(userDetails);
        when(userManagementService.findUserSummaryByUsername(expectedUsername)).thenReturn(Optional.of(userSummary));

        UserDetailsResponse response = userApiController.getDetails();

        assertThat(response.username()).isEqualTo(expectedUsername);
        assertThat(response.completeName()).isEqualTo(expectedCompleteName);
    }
}
