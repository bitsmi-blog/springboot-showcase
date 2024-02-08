package com.bitsmi.springbootshowcase.web.user.controller;

import com.bitsmi.springbootshowcase.api.user.IUserApi;
import com.bitsmi.springbootshowcase.api.user.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.web.common.service.IAuthenticationPrincipalService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 */
@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "user.api")
public class UserApiControllerImpl implements IUserApi
{
    @Autowired
    private IAuthenticationPrincipalService authenticationPrincipalService;
//    @Autowired
//    private IUserManagementService userManagementService;

    @Override
    public UserDetailsResponse getDetails()
    {
        final UserDetails userDetails = authenticationPrincipalService.getAuthenticationPrincipal();
//        final UserSummary userSummary = userManagementService.findUserSummaryByUsername(userDetails.getUsername())
//                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));

        return UserDetailsResponse.builder()
                .username(userDetails.getUsername())
                .completeName("John Doe")
                .build();
    }

    @Override
    @PreAuthorize("hasRole('admin.authority1')")
    public UserDetailsResponse getAdminDetails()
    {
        final UserDetails userDetails = authenticationPrincipalService.getAuthenticationPrincipal();

        return UserDetailsResponse.builder()
                .username(userDetails.getUsername())
                .build();
    }
}
