package com.bitsmi.springbootshowcase.sample.domain.common.exception;

import java.io.Serial;

/**
 * TODO Move to yggdrasil-commons
 */
public class ElementNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1L;

    private final String type;
    private final String id;

    public ElementNotFoundException(String type, Object id)
    {
        this.type = type;
        this.id = id!=null ? id.toString() : null;
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
        return String.format("Element (%s) of type (%s) not found", id, type);
    }
}
