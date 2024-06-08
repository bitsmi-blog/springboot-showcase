package com.bitsmi.springbootshowcase.springcore.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductServiceAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceAspect.class);

    @Pointcut("execution(* com.bitsmi.springbootshowcase.springcore.aop.service.ProductService.*(..))")
    public void productServiceExecutionPointcut() { }

    @Before("productServiceExecutionPointcut()")
    public void beforeProductServiceMethodExecution(JoinPoint joinPoint)
    {
        LOGGER.info("[beforeProductServiceMethodExecution] Aspect executed before ({})", joinPoint.getSignature().getName());
    }

    @After("productServiceExecutionPointcut()")
    public void afterProductServiceMethodExecution(JoinPoint joinPoint)
    {
        LOGGER.info("[afterProductServiceMethodExecution] Aspect executed after ({})", joinPoint.getSignature().getName());
    }
}
