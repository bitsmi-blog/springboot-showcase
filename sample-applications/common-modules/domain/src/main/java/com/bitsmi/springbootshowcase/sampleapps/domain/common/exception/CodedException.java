package com.bitsmi.springbootshowcase.sampleapps.domain.common.exception;

import java.io.Serial;

public class CodedException extends Exception
{
    @Serial
    private static final long serialVersionUID = 1L;
    protected String errorCode;

    public CodedException(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public CodedException(String errorCode, Throwable cause)
    {
        super(cause);
        this.errorCode = errorCode;
    }

    public CodedException(CodedMessage error)
    {
        super(error.getMessage());
        this.errorCode = error.getCode();
    }

    public CodedException(CodedMessage error, Throwable cause)
    {
        super(error.getMessage(), cause);
        this.errorCode = error.getCode();
    }

    public CodedException(CodedMessage error, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(error.getMessage(), cause, enableSuppression, writableStackTrace);
        this.errorCode = error.getCode();
    }

    public String getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
}
