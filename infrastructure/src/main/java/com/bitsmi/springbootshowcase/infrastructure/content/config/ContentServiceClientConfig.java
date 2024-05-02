package com.bitsmi.springbootshowcase.infrastructure.content.config;

import com.bitsmi.springbootshowcase.infrastructure.InfrastructureConstants;
import com.bitsmi.springshowcase.contentservice.client.ContentServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentServiceClientConfig
{
    @Bean
    public ContentServiceClient contentServiceClient(@Value("${" + InfrastructureConstants.PROPERTIES_CONTENT_SERVICE_CLIENT_BASE_URL + "}") String baseUrl)
    {
        return ContentServiceClient.builder()
                .withDefaultValidation()
                .withBaseUrl(baseUrl)
                .build();
    }
}
