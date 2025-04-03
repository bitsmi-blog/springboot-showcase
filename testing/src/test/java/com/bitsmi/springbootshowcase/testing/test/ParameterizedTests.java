package com.bitsmi.springbootshowcase.testing.test;

import com.bitsmi.springbootshowcase.testing.MeasureUnit;
import lombok.Builder;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ParameterizedTests {

    @DisplayName("Result given @ValueSource should be positive when ")
    @ParameterizedTest(name = "{arguments} is provided")
    @ValueSource(longs = {
        100L,
        200L,
        300L
    })
    void valueSourceTest1(Long param) {
        final Long actualResult = param * -1;

        assertThat(actualResult)
            .describedAs("actualResult")
            .isNegative();
    }

    /**
     * Annotations can be combined
     */
    @DisplayName("Result length given @ValueSource and @EmptySource should be >= 0 ")
    @ParameterizedTest(name = "{arguments} is provided")
    @ValueSource(strings = {
        "100L",
        "200L",
        "300L"
    })
    @EmptySource
    void valueSourceTest2(String param) {
        assertThat(param)
            .describedAs("actualResult")
            .isNotNull()
            .hasSizeGreaterThanOrEqualTo(0);
    }

    @DisplayName("Result given @EnumSource should be true when ")
    @ParameterizedTest(name = "provided measure unit is {arguments}")
    @EnumSource(value = MeasureUnit.class, names = { "KILOMETER", "METER", "CENTIMETER" })
    void enumSourceTest1(MeasureUnit measureUnit) {
        assertThat(measureUnit)
            .describedAs("measureUnit")
            .extracting(MeasureUnit::isDecimalMetricSystem)
            .asInstanceOf(InstanceOfAssertFactories.BOOLEAN)
            .isTrue();
    }

    /**
     * Negate from previous test case
     */
    @DisplayName("Result given @EnumSource should be false when ")
    @ParameterizedTest(name = "provided measure unit is {arguments}")
    @EnumSource(value = MeasureUnit.class, mode = Mode.EXCLUDE, names = { "KILOMETER", "METER", "CENTIMETER" })
    void enumSourceTest2(MeasureUnit measureUnit) {
        assertThat(measureUnit)
            .describedAs("measureUnit")
            .extracting(MeasureUnit::isDecimalMetricSystem)
            .asInstanceOf(InstanceOfAssertFactories.BOOLEAN)
            .isFalse();
    }

    /**
     * Equivalent to:
     * <code>
     *  @NullSource
     *  @EmptySource
     * </code>
     */
    @DisplayName("Result given @NullAndEmptySource should be null when")
    @ParameterizedTest(name = "param is ''{0}''")
    @NullAndEmptySource
    void nullOrEmptySourceTest1(String param) {
        String expectedResult = "DEFAULT";

        final String actualResult = param==null || param.isEmpty() ? expectedResult : param;

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(expectedResult);
    }

    @DisplayName("Formatted result given @CsvSource should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    @CsvSource(
        delimiterString = ";",
        quoteCharacter = '"',
        textBlock = """
            # KEY;                VALUE;      RESULT
              "TestDataMethod1";  1;   "TestDataMethod1:1"
              "TestDataMethod2";  2;   "TestDataMethod2:2"
              "TestDataMethod3";  3;   "TestDataMethod3:3"
            """
    )
    void csvSourceTest1(
        String providedKey,
        Integer providedValue,
        String expectedResult
    ) {
        final String actualResult = "%s:%s".formatted(providedKey, providedValue);

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(expectedResult);
    }

    /**
     * {@link ArgumentsAccessor} can be used with {@link CsvSource}, {@link CsvFileSource} and {@link MethodSource}
     */
    @DisplayName("Formatted result given @CsvSource and ArgumentAccessor should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    @CsvSource(
        delimiterString = ";",
        quoteCharacter = '"',
        textBlock = """
            # KEY;                VALUE;      RESULT
              "TestDataMethod1";  1;   "TestDataMethod1:1"
              "TestDataMethod2";  2;   "TestDataMethod2:2"
              "TestDataMethod3";  3;   "TestDataMethod3:3"
            """
    )
    void csvSourceTest2(ArgumentsAccessor accessor) {
        final String providedKey = accessor.getString(0);
        final Integer providedValue = accessor.getInteger(1);
        final String expectedResult = accessor.getString(2);

        final String actualResult = "%s:%s".formatted(providedKey, providedValue);

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(expectedResult);
    }

    @DisplayName("Formatted result given @CsvSource and ArgumentsAggregator should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    @CsvSource(
        delimiterString = ";",
        quoteCharacter = '"',
        textBlock = """
            # KEY;                VALUE;      RESULT
              "TestDataMethod1";  1;   "TestDataMethod1:1"
              "TestDataMethod2";  2;   "TestDataMethod2:2"
              "TestDataMethod3";  3;   "TestDataMethod3:3"
            """
    )
    void csvSourceTest3(@AggregateWith(ProvidedValueDtoAggregator.class) ProvidedValueDto providedValueDto) {
        final String actualResult = "%s:%s".formatted(providedValueDto.key, providedValueDto.value);

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(providedValueDto.expectedResult);
    }

    @DisplayName("Formatted result given @CsvFileSource should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    @CsvFileSource(
        delimiterString = ";",
        quoteCharacter = '"',
        // Skip header
        numLinesToSkip = 1,
        // Use files to search in project root folder
//        files = { "./parameterized_tests_csv_file_source.csv" }
        // Use resources to search in classpath
        resources = { "/parameterized_tests_csv_file_source.csv" }
    )
    void csvFileSourceTest1(
        String providedKey,
        Integer providedValue,
        String expectedResult
    ) {
        final String actualResult = "%s:%s".formatted(providedKey, providedValue);

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(expectedResult);
    }

    private static final List<String> fieldSourceTestData = List.of("VALUE1", "VALUE2", "VALUE3");

    @DisplayName("fieldSource should be")
    @ParameterizedTest(name = "{index} - {0}")
    // If no field names are declared, a field within the test class that has the same name as the test method will be used as the field by default.
    @FieldSource("fieldSourceTestData")
    void fieldSourceTest1(String providedKey) {
        assertThat(providedKey)
                .isIn(fieldSourceTestData);
    }

    @DisplayName("Formatted result given @MethodSource should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    @MethodSource("provideTestData")
    void methodSourceTest1(
        String providedKey,
        String providedValue,
        String expectedResult
    ) {
        final String actualResult = "%s:%s".formatted(providedKey, providedValue);

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(expectedResult);
    }

    @DisplayName("Formatted result given @MethodSource should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    // @MethodSource with no name will search for an static provider method with same name (methodSourceTest2)
    @MethodSource
    void methodSourceTest2(
            String providedKey,
            String providedValue,
            String expectedResult
    ) {
        final String actualResult = "%s:%s".formatted(providedKey, providedValue);

        assertThat(actualResult)
                .describedAs("actualResult")
                .isEqualTo(expectedResult);
    }

    /**
     * Argument providers implementing {@link org.junit.jupiter.params.support.AnnotationConsumer}
     * cannot be used with {@link ArgumentsSource} as they require an annotation
     */
    @DisplayName("Formatted result given @ArgumentsSource should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    @ArgumentsSource(SimpleArgumentProvider.class)
    void argumentsSourceTest1(
        String providedKey,
        String providedValue,
        String expectedResult
    ) {
        final String actualResult = "%s:%s".formatted(providedKey, providedValue);

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(expectedResult);
    }

    @DisplayName("Formatted result given @SamplesSource should be")
    @ParameterizedTest(name = "{index} - {2} when key = {0} and value = {1} are provided")
    @SamplesSource(5)
    void argumentsSourceTest2(
        String providedKey,
        String providedValue,
        String expectedResult
    ) {
        final String actualResult = "%s:%s".formatted(providedKey, providedValue);

        assertThat(actualResult)
            .describedAs("actualResult")
            .isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
            Arguments.of(
                    Named.of("Key 1", "TestDataMethod1"),
                "Value1",
                "TestDataMethod1:Value1"
            ),
            Arguments.of(
                    Named.of("Key 2", "TestDataMethod2"),
                "Value2",
                "TestDataMethod2:Value2"
            ),
            Arguments.of(
                    Named.of("Key 3", "TestDataMethod3"),
                "Value3",
                "TestDataMethod3:Value3"
            )
        );
    }

    private static Stream<Arguments> methodSourceTest2() {
        return provideTestData();
    }

    @Builder(builderClassName = "Builder", toBuilder = true)
    record ProvidedValueDto(
        String key,
        Integer value,
        String expectedResult
    ) {

    }

    static class ProvidedValueDtoAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            final String providedKey = accessor.getString(0);
            final Integer providedValue = accessor.getInteger(1);
            final String expectedResult = "%s:%s".formatted(providedKey, providedValue);

            return ProvidedValueDto.builder()
                .key(providedKey)
                .value(providedValue)
                .expectedResult(expectedResult)
                .build();
        }
    }
}
