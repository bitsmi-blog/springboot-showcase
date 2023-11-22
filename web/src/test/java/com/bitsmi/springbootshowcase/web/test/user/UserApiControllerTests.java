package com.bitsmi.springbootshowcase.web.test.user;

import com.bitsmi.springbootshowcase.api.user.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.web.common.service.IAuthenticationPrincipalService;
import com.bitsmi.springbootshowcase.web.user.controller.UserApiControllerImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApiControllerTests
{
    @Mock
    private IAuthenticationPrincipalService authenticationPrincipalService;

    @InjectMocks
    private UserApiControllerImpl userApiController;

    @Test
    @DisplayName("getDetails should return current username")
    public void getDetailsTest1()
    {
        final String expectedUsername = "john.doe";

        final UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(authenticationPrincipalService.getAuthenticationPrincipal())
                .thenReturn(userDetails);

        UserDetailsResponse response = userApiController.getDetails();

        assertThat(response.username()).isEqualTo(expectedUsername);
    }

    @Test
    @DisplayName("getAdminDetails should return current username")
    public void getAdminDetailsTest1()
    {
        final String expectedUsername = "admin";

        final UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(expectedUsername);
        when(authenticationPrincipalService.getAuthenticationPrincipal())
                .thenReturn(userDetails);

        UserDetailsResponse response = userApiController.getDetails();

        assertThat(response.username()).isEqualTo(expectedUsername);
    }
}
