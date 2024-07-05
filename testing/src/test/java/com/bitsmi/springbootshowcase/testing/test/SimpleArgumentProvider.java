package com.bitsmi.springbootshowcase.testing.test;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class SimpleArgumentProvider implements ArgumentsProvider
{
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception
    {
        return IntStream.range(0, 5)
            .mapToObj(this::buildSample);
    }

    private Arguments buildSample(int index)
    {
        final String key = "TestDataMethod" + index;
        final String value = "Value" + index;
        final String result = "%s:%s".formatted(key, value);

        return Arguments.of(key, value, result);
    }
}
