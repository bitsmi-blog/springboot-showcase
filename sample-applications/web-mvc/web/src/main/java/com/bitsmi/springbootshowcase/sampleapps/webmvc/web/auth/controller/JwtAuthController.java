package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bitsmi.springbootshowcase.sampleapps.webmvc.web.config.JWTProperties;
import io.micrometer.observation.annotation.Observed;
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
@Observed(name = "auth.jwt")
public class JwtAuthController
{
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTProperties jwtProperties;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> postAuth(@AuthenticationPrincipal UserDetails userDetails)
    {
        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Instant.now().plus(jwtProperties.getExpirationTime(), ChronoUnit.MILLIS))
                .sign(Algorithm.HMAC512(jwtProperties.getSecret()));

        return ResponseEntity.ok(token);
    }
}
