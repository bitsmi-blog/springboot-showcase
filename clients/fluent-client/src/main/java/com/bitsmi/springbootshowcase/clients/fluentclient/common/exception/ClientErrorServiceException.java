package com.bitsmi.springbootshowcase.clients.fluentclient.common.exception;

public class ClientErrorServiceException extends ServiceException
{
    public ClientErrorServiceException(String errorCode)
    {
        super(errorCode);
    }

    public ClientErrorServiceException(String errorCode, String message)
    {
        super(errorCode, message);
    }

    public ClientErrorServiceException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public ClientErrorServiceException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(errorCode, message, cause, enableSuppression, writableStackTrace);
    }
}
