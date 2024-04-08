package com.bitsmi.springshowcase.contentservice.client.test;

import com.bitsmi.springshowcase.contentservice.client.common.request.ISelector;
import com.bitsmi.springshowcase.contentservice.client.common.request.RelationalOperator;
import com.bitsmi.springshowcase.contentservice.client.common.request.SimpleSelector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectorTests
{
    @Test
    @DisplayName("selector toString should describe a composed selector given multiple statements")
    public void composedSelectorTest1()
    {
        ISelector actualSelector = SimpleSelector.of("name", RelationalOperator.EQUALS, "foo")
                .and(SimpleSelector.of("field1", RelationalOperator.EQUALS, "bar"))
                .and(SimpleSelector.of("field2", RelationalOperator.EQUALS, "baz"));

        assertThat(actualSelector.toString()).isEqualTo("((name EQUALS foo AND field1 EQUALS bar) AND field2 EQUALS baz)");
    }

    @Test
    @DisplayName("selector toString should quote field and values with multiple terms")
    public void composedSelectorTest2()
    {
        ISelector actualSelector = SimpleSelector.of("name", RelationalOperator.EQUALS, "foo foo")
                .and(SimpleSelector.of("field 1", RelationalOperator.EQUALS, "bar"))
                .and(SimpleSelector.of("field 2", RelationalOperator.EQUALS, "baz baz"));

        assertThat(actualSelector.toString()).isEqualTo("((name EQUALS 'foo foo' AND 'field 1' EQUALS bar) AND 'field 2' EQUALS 'baz baz')");
    }
}
