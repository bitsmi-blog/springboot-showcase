package com.bitsmi.springbootshowcase.infrastructure.event.listener;

import com.bitsmi.springbootshowcase.infrastructure.InfrastructureConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.cache.CacheMetricsRegistrar;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CacheMetricsRegistrarApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent>
{
    @Autowired
    private CacheMetricsRegistrar cacheMetricsRegistrar;
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event)
    {
        this.cacheMetricsRegistrar.bindCacheToRegistry(cacheManager.getCache(InfrastructureConstants.CACHE_ALL_SCHEMAS));
        this.cacheMetricsRegistrar.bindCacheToRegistry(cacheManager.getCache(InfrastructureConstants.CACHE_SCHEMA_BY_EXTERNAL_ID));
    }
}
