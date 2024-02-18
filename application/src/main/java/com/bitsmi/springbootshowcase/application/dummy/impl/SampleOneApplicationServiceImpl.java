package com.bitsmi.springbootshowcase.application.dummy.impl;

import com.bitsmi.springbootshowcase.application.ApplicationConstants;
import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationService;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SampleOneApplicationServiceImpl implements ISampleApplicationService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleOneApplicationServiceImpl.class);

    @Autowired
    private MeterRegistry meterRegistry;

    @Cacheable(
            cacheManager = ApplicationConstants.CACHE_MANAGER_MEMOIZE,
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
