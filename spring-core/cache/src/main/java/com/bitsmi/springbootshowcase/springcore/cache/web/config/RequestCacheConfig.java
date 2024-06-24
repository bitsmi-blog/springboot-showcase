package com.bitsmi.springbootshowcase.springcore.cache.web.config;

import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.config.MemoizeCacheManagerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestCacheConfig
{
    @Bean
    public MemoizeCacheManagerProvider memoizeCacheManagerProvider()
    {
        return RequestScopedCacheManager::new;
    }
}
