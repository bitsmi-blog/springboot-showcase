package com.bitsmi.springbootshowcase.web.auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "/auth")
@ResponseBody
@Observed(name = "auth.jwt")
public class JwtAuthController
{
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private byte[] jwtSecret;
    @Value("${jwt.expirationTime}")
    private Long expirationTime;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> postAuth(@AuthenticationPrincipal UserDetails userDetails)
    {
        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Instant.now().plus(expirationTime, ChronoUnit.MILLIS))
                .sign(Algorithm.HMAC512(jwtSecret));

        return ResponseEntity.ok(token);
    }
}
