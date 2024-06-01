package com.bitsmi.springbootshowcase.sampleapps.webapp.web.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestScopedCacheManager implements CacheManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestScopedCacheManager.class);

    @Override
    public Collection<String> getCacheNames()
    {
        return getCacheMap().keySet();
    }

    @Override
    public Cache getCache(String name)
    {
        return getCacheMap().computeIfAbsent(name, this::createCache);
    }

    protected Map<String, Cache> getCacheMap()
    {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return new HashMap<>();
        }
        @SuppressWarnings("unchecked")
        Map<String, Cache> cacheMap = (Map<String, Cache>) requestAttributes.getAttribute(getCacheMapAttributeName(), RequestAttributes.SCOPE_REQUEST);
        if (cacheMap == null) {
            cacheMap = new HashMap<>();
            requestAttributes.setAttribute(getCacheMapAttributeName(), cacheMap, RequestAttributes.SCOPE_REQUEST);
            requestAttributes.registerDestructionCallback(
                    getCacheMapAttributeName(),
                    () -> LOGGER.info("Removed scoped cache from request: {}", getCurrentHttpRequest()),
                    RequestAttributes.SCOPE_REQUEST
            );
        }
        return cacheMap;
    }

    protected String getCacheMapAttributeName()
    {
        return this.getClass().getName();
    }

    protected Cache createCache(String name)
    {
        return new ConcurrentMapCache(name);
    }

    public void clearCaches()
    {
        getCacheMap().values()
                .forEach(Cache::clear);
        getCacheMap().clear();
    }

    private Optional<HttpServletRequest> getCurrentHttpRequest()
    {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }
}
