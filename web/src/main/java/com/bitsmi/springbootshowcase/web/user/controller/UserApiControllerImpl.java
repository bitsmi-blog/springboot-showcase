package com.bitsmi.springbootshowcase.web.user.controller;

import com.bitsmi.springbootshowcase.api.user.IUserApi;
import com.bitsmi.springbootshowcase.api.user.response.UserDetailsResponse;
import com.bitsmi.springbootshowcase.application.common.IUserSummaryApplicationQuery;
import com.bitsmi.springbootshowcase.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.web.common.service.IAuthenticationPrincipalService;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "user.api")
public class UserApiControllerImpl implements IUserApi
{
    @Autowired
    private IAuthenticationPrincipalService authenticationPrincipalService;
    @Autowired
    private IUserSummaryApplicationQuery userSummaryApplicationQuery;

    @Override
    public UserDetailsResponse getDetails()
    {
        final UserDetails userDetails = authenticationPrincipalService.getAuthenticationPrincipal();
        final UserSummary userSummary = userSummaryApplicationQuery.findUserSummaryByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));

        return UserDetailsResponse.builder()
                .username(userDetails.getUsername())
                .completeName(userSummary.completeName())
                .build();
    }

    @Override
//    @PreAuthorize("hasRole('" + UserConstants.USER_GROUP_ADMIN + "')")
    @PreAuthorize("hasAuthority('" + UserConstants.USER_PERMISSION_ADMIN_PERMISSION1 + "')")
    public UserDetailsResponse getAdminDetails()
    {
        final UserDetails userDetails = authenticationPrincipalService.getAuthenticationPrincipal();

        return UserDetailsResponse.builder()
                .username(userDetails.getUsername())
                .build();
    }
}
