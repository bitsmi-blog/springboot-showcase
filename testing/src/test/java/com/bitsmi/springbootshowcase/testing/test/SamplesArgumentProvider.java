package com.bitsmi.springbootshowcase.testing.test;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

public class SamplesArgumentProvider implements ArgumentsProvider, AnnotationConsumer<SamplesSource> {

    private int numberOfSamples = SamplesSource.DEFAULT_NUMBER_OF_SAMPLES;

    @Override
    public void accept(SamplesSource samplesSource)
    {
        validateSampleSource(samplesSource);

        this.numberOfSamples = samplesSource.value();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception
    {
        return IntStream.range(0, numberOfSamples)
            .mapToObj(this::buildSample);
    }

    private void validateSampleSource(SamplesSource samplesSource)
    {
        if(samplesSource.value() < 1) {
            throw new IllegalArgumentException("Samples value must be greater than 0");
        }
    }

    private Arguments buildSample(int index)
    {
        final String key = "TestDataMethod" + index;
        final String value = "Value" + index;
        final String result = "%s:%s".formatted(key, value);

        return Arguments.of(key, value, result);
    }
}
