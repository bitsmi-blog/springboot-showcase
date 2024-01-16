package com.bitsmi.springbootshowcase.web.common.controller;

import com.bitsmi.springbootshowcase.core.common.exception.CodedException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.stream.Collectors;

public class ProblemDetailBuilder
{
    public static final String LEVEL_WARN = "WARN";
    public static final String LEVEL_ERROR = "ERROR";

    private final int status;
    private String title;
    private String detail;
    private String level;
    private String errorCode;

    private ProblemDetailBuilder(int status)
    {
        this.status = status;
    }

    public static ProblemDetail forException(String title, Exception e)
    {
        ResponseStatus statusAnnotation = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        ProblemDetailBuilder problemDetailBuilder = null;
        String message = e.getMessage();
        if(statusAnnotation!=null) {
            problemDetailBuilder = ProblemDetailBuilder.forStatus(statusAnnotation.value().value());
        }
        else if(e instanceof HttpClientErrorException ce) {
            problemDetailBuilder = ProblemDetailBuilder.forStatus(ce.getStatusCode().value());
        }
        else if(e instanceof MethodArgumentNotValidException ce) {
            //Get all fields errors
            message = ce.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(f -> f.getField() + " " + f.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            problemDetailBuilder = ProblemDetailBuilder.forStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            problemDetailBuilder = ProblemDetailBuilder.forStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if(e instanceof CodedException ce) {
            problemDetailBuilder.withErrorCode(ce.getErrorCode());
        }

        return problemDetailBuilder.asError()
                .withTitle(title)
                .withDetail(message)
                .build();
    }

    public static ProblemDetailBuilder forStatus(int status)
    {
        return new ProblemDetailBuilder(status);
    }

    public ProblemDetailBuilder withTitle(String title)
    {
        this.title = title;
        return this;
    }

    public ProblemDetailBuilder withDetail(String detail)
    {
        this.detail = detail;
        return this;
    }

    public ProblemDetailBuilder asWarn()
    {
        this.level = LEVEL_WARN;
        return this;
    }

    public ProblemDetailBuilder asError()
    {
        this.level = LEVEL_ERROR;
        return this;
    }

    public ProblemDetailBuilder withErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
        return this;
    }

    public ProblemDetail build()
    {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(StringUtils.defaultString(title, "Unexpected error"));
        problemDetail.setDetail(StringUtils.defaultString(detail));
        problemDetail.setProperty("level", StringUtils.defaultString(level, "ERROR"));
        problemDetail.setProperty("errorCode", StringUtils.defaultString(errorCode));

        return problemDetail;
    }
}
