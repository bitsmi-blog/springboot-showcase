package com.bitsmi.springshowcase.contentservice.client.common.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ServiceException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1L;
    protected String errorCode;

    public ServiceException(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, Throwable cause)
    {
        super(cause);
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
