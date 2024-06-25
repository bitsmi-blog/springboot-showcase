package com.bitsmi.springbootshowcase.springcore.cache.domain.common.exception;

import java.io.Serial;

public class ElementAlreadyExistsException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1L;

    private final String type;
    private final String id;

    public ElementAlreadyExistsException(String type, String id)
    {
        this.type = type;
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public String getId()
    {
        return id;
    }

    @Override
    public String getMessage()
    {
        return String.format("Element (%s) of type (%s) already exists", id, type);
    }
}
