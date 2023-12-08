package com.bitsmi.springbootshowcase.core.dummy.impl;

import com.bitsmi.springbootshowcase.core.CoreConstants;
import com.bitsmi.springbootshowcase.core.dummy.ISampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SampleOneServiceImpl implements ISampleService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleOneServiceImpl.class);

    @Cacheable(
            cacheManager = CoreConstants.CACHE_MANAGER_MEMOIZE,
            cacheNames = "sampleOne"
    )
    @Override
    public String getSample()
    {
        LOGGER.info("Getting Sample One...");
        return "Sample One";
    }
}
