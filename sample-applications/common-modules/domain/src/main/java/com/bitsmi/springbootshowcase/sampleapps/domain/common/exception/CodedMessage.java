package com.bitsmi.springbootshowcase.sampleapps.domain.common.exception;

import java.util.Objects;

public class CodedMessage
{
    private String code;
    private String message;

    public CodedMessage()
    {
    }

    public String getCode()
    {
        return this.code;
    }

    public CodedMessage setCode(String code)
    {
        this.code = code;
        return this;
    }

    public String getMessage()
    {
        return this.message;
    }

    public CodedMessage setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public CodedMessage setMessage(String message, Object... args)
    {
        String formattedMessage = String.format(message, args);
        this.setMessage(formattedMessage);
        return this;
    }

    public static CodedMessage of(String code, String message)
    {
        return (new CodedMessage()).setCode(code).setMessage(message);
    }

    public static CodedMessage of(String code, String message, Object... args)
    {
        return (new CodedMessage()).setCode(code).setMessage(message, args);
    }

    public int hashCode()
    {
        return Objects.hash(code, message);
    }

    public boolean equals(Object o)
    {
        return this == o
                || o instanceof CodedMessage other
                    && Objects.equals(code, other.code)
                    && Objects.equals(message, other.message);
    }
}
