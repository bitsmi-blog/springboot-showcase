package com.bitsmi.springbootshowcase.clients.fluentclient.common.exception;

public class ServerErrorServiceException extends ServiceException
{

    public ServerErrorServiceException(String errorCode)
    {
        super(errorCode);
    }

    public ServerErrorServiceException(String errorCode, String message)
    {
        super(errorCode, message);
    }

    public ServerErrorServiceException(String errorCode, Throwable cause)
    {
        super(errorCode, cause);
    }

    public ServerErrorServiceException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(errorCode, message, cause, enableSuppression, writableStackTrace);
    }
}
