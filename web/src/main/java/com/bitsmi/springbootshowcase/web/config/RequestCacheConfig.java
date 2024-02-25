package com.bitsmi.springbootshowcase.web.config;

import com.bitsmi.springbootshowcase.application.common.spi.IMemoizeCacheManagerProvider;
import org.springframework.context.annotation.Bean;

public class RequestCacheConfig
{
    @Bean
    public IMemoizeCacheManagerProvider memoizeCacheManagerProvider()
    {
        return RequestScopedCacheManager::new;
    }
}
