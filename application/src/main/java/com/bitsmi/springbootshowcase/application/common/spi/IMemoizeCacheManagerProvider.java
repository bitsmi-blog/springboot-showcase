package com.bitsmi.springbootshowcase.application.common.spi;

import org.springframework.cache.CacheManager;

public interface IMemoizeCacheManagerProvider
{
    CacheManager get();
}
