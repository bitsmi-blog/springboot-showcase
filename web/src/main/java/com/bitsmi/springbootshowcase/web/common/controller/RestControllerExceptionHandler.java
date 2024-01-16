package com.bitsmi.springbootshowcase.web.common.controller;

import com.bitsmi.springbootshowcase.core.common.exception.CodedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@ControllerAdvice(annotations = {
		RestController.class
})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestControllerExceptionHandler
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
	
	@ExceptionHandler(value = CodedException.class)
	public ProblemDetail codedErrorHandler(HttpServletRequest request, HttpServletResponse response, CodedException e) throws IOException
	{
		LOGGER.error("[codedErrorHandler] Error message: {} - {}", e.getErrorCode(), e.getMessage(), e);
		return ProblemDetailBuilder.forException("Unexpected error", e);
	}
	
	/**
	 * Manage <code>@Valid</code> validations on controller method parameters
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ProblemDetail methodValidationErrorHandler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) throws IOException
	{
		LOGGER.error("[methodValidationErrorHandler] Error message: {}", e.getMessage(), e);
		return ProblemDetailBuilder.forException("Validation error", e);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public ProblemDetail accessDeniedErrorHandler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException
	{
		LOGGER.error("[accessDeniedErrorHandler] Error message: {}", e.getMessage(), e);
		return ProblemDetailBuilder.forStatus(403).build();
	}

	@ExceptionHandler(value = ErrorResponseException.class)
	public ProblemDetail errorResponseHandler(HttpServletRequest request, HttpServletResponse response, ErrorResponseException e) throws IOException
	{
		LOGGER.error("[errorResponseHandler] Error message: {}", e.getMessage(), e);
		return e.getBody();
	}

	@ExceptionHandler(value = Exception.class)
	public ProblemDetail defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException
	{
		LOGGER.error("[defaultErrorHandler] Error message: {}", e.getMessage(), e);
		return ProblemDetailBuilder.forException("Unexpected error", e);
	}
}
