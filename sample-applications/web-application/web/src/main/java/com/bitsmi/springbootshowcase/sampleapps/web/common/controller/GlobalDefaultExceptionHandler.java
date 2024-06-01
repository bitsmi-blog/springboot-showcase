package com.bitsmi.springbootshowcase.sampleapps.web.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class GlobalDefaultExceptionHandler 
{
	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	private final ObjectMapper jsonMapper;

	public GlobalDefaultExceptionHandler()
	{
		 jsonMapper = Jackson2ObjectMapperBuilder.json().build();
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public void noHandlerFound(NoHandlerFoundException e, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String url = e.getRequestURL();
		LOGGER.warn("[noHandlerFound] No handler found for ajax / api request ({})", url, e);

		ProblemDetail problemDetail = ProblemDetailBuilder.forStatus(HttpServletResponse.SC_NOT_FOUND)
				.asError()
				.withTitle("Not found")
				.withDetail(e.getMessage())
				.build();

		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(jsonMapper.writeValueAsString(problemDetail));
		response.getWriter().flush();
	}
}
