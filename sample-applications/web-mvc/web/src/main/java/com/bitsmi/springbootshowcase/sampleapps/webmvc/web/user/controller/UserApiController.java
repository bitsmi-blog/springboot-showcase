package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller;

import com.bitsmi.springbootshowcase.sampleapps.application.common.UserSummaryApplicationService;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.UserConstants;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.service.IAuthenticationPrincipalService;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.user.controller.response.UserDetailsResponse;
import io.micrometer.observation.annotation.Observed;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@Observed(name = "user.api")
public class UserApiController
{
    private final IAuthenticationPrincipalService authenticationPrincipalService;
    private final UserSummaryApplicationService userSummaryApplicationService;

    public UserApiController(
            IAuthenticationPrincipalService authenticationPrincipalService,
            UserSummaryApplicationService userSummaryApplicationService
    ) {
        this.authenticationPrincipalService = authenticationPrincipalService;
        this.userSummaryApplicationService = userSummaryApplicationService;
    }

    @GetMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsResponse getDetails()
    {
        final UserDetails userDetails = authenticationPrincipalService.getAuthenticationPrincipal();
        final UserSummary userSummary = userSummaryApplicationService.findUserSummaryByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));

        return UserDetailsResponse.builder()
                .username(userDetails.getUsername())
                .completeName(userSummary.completeName())
                .build();
    }

    @GetMapping("/admin/details")
    @ResponseStatus(HttpStatus.OK)
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
