package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.config.JWTProperties;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.config.OpenApiConfig;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Authentication API", description = "Authenticate to access API")
@Observed(name = "auth.jwt")
public class JwtAuthController {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTProperties jwtProperties;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Obtain an API access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API Access token"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content())
    })
    @SecurityRequirement(name = OpenApiConfig.BASIC_SECURITY_SCHEME_NAME)
    public ResponseEntity<String> postAuth(@AuthenticationPrincipal UserDetails userDetails) {
        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Instant.now().plus(jwtProperties.getExpirationTime(), ChronoUnit.MILLIS))
                .sign(Algorithm.HMAC512(jwtProperties.getSecret()));

        return ResponseEntity.ok(token);
    }
}