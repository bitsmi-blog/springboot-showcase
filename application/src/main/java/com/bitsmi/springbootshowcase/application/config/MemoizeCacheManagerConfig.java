package com.bitsmi.springbootshowcase.application.config;

import com.bitsmi.springbootshowcase.application.common.spi.IMemoizeCacheManagerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoizeCacheManagerConfig
{
    /**
     * Bean name must match with the cache manager name specified in {@link org.springframework.cache.annotation.Cacheable} annotations
     * @see com.bitsmi.springbootshowcase.application.ApplicationConstants#CACHE_MANAGER_MEMOIZE
     */
    @Bean(name = "memoizeCacheManager")
    public CacheManager memoizeCacheManager(@Autowired(required = false) IMemoizeCacheManagerProvider provider)
    {
        // Memoize cache disabled
        if(provider==null) {
            return new NoOpCacheManager();
        }

        return provider.get();
    }
}
