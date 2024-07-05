package com.bitsmi.springbootshowcase.testing.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(SamplesArgumentProvider.class)
public @interface SamplesSource
{
    int DEFAULT_NUMBER_OF_SAMPLES = 3;

    int value() default DEFAULT_NUMBER_OF_SAMPLES;
}
