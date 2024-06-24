package com.bitsmi.springbootshowcase.springcore.cache.infrastructure.config;

import org.springframework.cache.CacheManager;

public interface MemoizeCacheManagerProvider
{
    CacheManager get();
}
