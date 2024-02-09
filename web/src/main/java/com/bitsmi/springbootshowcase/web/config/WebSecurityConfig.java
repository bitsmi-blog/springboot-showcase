package com.bitsmi.springbootshowcase.web.config;

import com.bitsmi.springbootshowcase.web.common.controller.ProblemDetailBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties({
        JWTProperties.class
})
public class WebSecurityConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainBasic(final HttpSecurity http) throws Exception
    {
        http.securityMatcher("/auth/**",
                        "/actuator/**",
                        "/api/admin",
                        // TODO remove
                        "/api/user/**")
                .authorizeHttpRequests(this::authorizeHttpRequestsBasic)
                .csrf(this::csrf)
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(this::exceptionHandlingBasic)
                .sessionManagement(this::sessionManagement);

        return http.build();
    }

    @Bean
    // Default order = last
    public SecurityFilterChain securityFilterChainDefault(final HttpSecurity http,
                                                          final Environment environment,
                                                          final UserDetailsService userDetailsService) throws Exception
    {
        http.securityMatcher("/api/**")
                .authorizeHttpRequests(this::authorizeHttpRequestsDefault)
                .csrf(this::csrf)
                .exceptionHandling(this::exceptionHandlingDefault)
                .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(userDetailsService, passwordEncoder()),
                        userDetailsService,
                        environment), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(this::sessionManagement);

        return http.build();
    }

    private void authorizeHttpRequestsBasic(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurer)
    {
        configurer.anyRequest().authenticated();
    }

    private void authorizeHttpRequestsDefault(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurer)
    {
        configurer
                .requestMatchers(HttpMethod.GET, "/api/application/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/setup/**").permitAll()
                .anyRequest().authenticated();
    }

    private void csrf(CsrfConfigurer<HttpSecurity> configurer)
    {
        configurer.disable();
    }

    private void exceptionHandlingBasic(ExceptionHandlingConfigurer<HttpSecurity> configurer)
    {
        configurer.authenticationEntryPoint(new BasicAuthenticationEntryPoint());
    }

    private void exceptionHandlingDefault(ExceptionHandlingConfigurer<HttpSecurity> configurer)
    {
        configurer.authenticationEntryPoint(authenticationEntryPointDefault());
    }

    private AuthenticationEntryPoint authenticationEntryPointDefault()
    {
        return new AuthenticationEntryPoint()
        {
            private final ObjectMapper jsonMapper = Jackson2ObjectMapperBuilder.json().build();

            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
            {
                String requestURI = request.getRequestURI();

                LOGGER.warn("[authenticationEntryPointDefault] Forbidden access for request ({})", requestURI);

                ProblemDetail problemDetail = ProblemDetailBuilder.forStatus(HttpServletResponse.SC_FORBIDDEN)
                        .asError()
                        .withTitle("Forbidden")
                        .build();

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println(jsonMapper.writeValueAsString(problemDetail));
                response.getWriter().flush();
            }
        };
    }

    private void sessionManagement(SessionManagementConfigurer<HttpSecurity> customizer)
    {
        customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService myUserDetailsService, PasswordEncoder encoder)
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(encoder);
        return new ProviderManager(provider);
    }
}
