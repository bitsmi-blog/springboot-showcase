package com.bitsmi.springbootshowcase.core.dummy.impl;

import com.bitsmi.springbootshowcase.core.CoreConstants;
import com.bitsmi.springbootshowcase.core.dummy.ISampleService;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SampleOneServiceImpl implements ISampleService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleOneServiceImpl.class);

    @Autowired
    private MeterRegistry meterRegistry;

    @Cacheable(
            cacheManager = CoreConstants.CACHE_MANAGER_MEMOIZE,
            cacheNames = "sampleOne"
    )
    @Override
    public String getSample()
    {
        LOGGER.info("Getting Sample One...");

        // Independent counter
        meterRegistry.counter("custom.metric", "tag1", "tag2").increment();

        return "Sample One";
    }
}
