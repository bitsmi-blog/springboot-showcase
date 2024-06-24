package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.config;

import com.bitsmi.springbootshowcase.springcore.cache.infrastructure.InfrastructureConstants;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
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
     * @see InfrastructureConstants#CACHE_MANAGER_MEMOIZE
     */
    @Bean(name = InfrastructureConstants.CACHE_MANAGER_MEMOIZE)
    public CacheManager memoizeCacheManager(@Autowired(required = false) MemoizeCacheManagerProvider provider)
    {
        // Memoize cache disabled
        if(provider==null) {
            return new NoOpCacheManager();
        }

        return provider.get();
    }
}
