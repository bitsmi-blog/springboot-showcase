package com.bitsmi.springbootshowcase.web.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheConfig
{
    @Bean
    @Primary
    public CacheManager cacheManager()
    {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(500)
                .weakKeys()
                .recordStats());

        return cacheManager;
    }

    /**
     * Bean name must match with the cache manager name specified in {@link org.springframework.cache.annotation.Cacheable} annotations
     * @see com.bitsmi.springbootshowcase.core.CoreConstants#CACHE_MANAGER_MEMOIZE
     */
    @Bean
    public CacheManager memoizeCacheManager()
    {
        return new RequestScopedCacheManager();
    }
}
