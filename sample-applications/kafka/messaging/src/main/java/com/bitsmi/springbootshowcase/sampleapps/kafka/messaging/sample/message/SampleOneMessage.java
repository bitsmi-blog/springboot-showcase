package com.bitsmi.springbootshowcase.sampleapps.kafka.messaging.sample.message;

public record SampleOneMessage(
        String oneName,
        String oneValue,
        boolean fail
)
{

}
