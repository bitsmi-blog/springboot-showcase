package com.bitsmi.springbootshowcase.sampleapps.webapp.web.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.nio.charset.StandardCharsets;

/**
 * Cannot use a record with @Validated because Spring will try to create a proxied class and it will fail as record is final
 * Record equivalent class would be (without validations)
 *
 * <pre>
 * {@code @ConfigurationProperties(prefix = "jwt")}
 * public record JWTProperties(
 *         byte[] secret,
 *         Long expirationTime
 * )
 * {
 *     {@code @ConstructorBinding}
 *     public JWTProperties(String secret, Long expirationTime)
 *     {
 *         this(secret.getBytes(StandardCharsets.UTF_8), expirationTime);
 *     }
 * }
 * </pre>
 */
@ConfigurationProperties(prefix = "jwt")
@Validated
@Getter
public class JWTProperties
{
    @NotEmpty
    private final byte[] secret;
    @NotNull
    @Positive
    private final Long expirationTime;

    @ConstructorBinding
    public JWTProperties(String secret, Long expirationTime)
    {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationTime = expirationTime;
    }
}
