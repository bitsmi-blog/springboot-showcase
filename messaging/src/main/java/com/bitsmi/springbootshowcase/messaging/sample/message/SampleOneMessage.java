package com.bitsmi.springbootshowcase.messaging.sample.message;

public record SampleOneMessage(
        String oneName,
        String oneValue,
        boolean fail
)
{

}
