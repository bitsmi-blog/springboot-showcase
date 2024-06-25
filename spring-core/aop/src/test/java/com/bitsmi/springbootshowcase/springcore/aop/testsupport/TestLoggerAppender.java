package com.bitsmi.springbootshowcase.springcore.aop.testsupport;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class TestLoggerAppender extends AppenderBase<ILoggingEvent>
{
    private final List<ILoggingEvent> events = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent event)
    {
        this.events.add(event);
    }

    public List<ILoggingEvent> getEvents()
    {
        return events;
    }

    public void clear()
    {
        events.clear();
    }
}
