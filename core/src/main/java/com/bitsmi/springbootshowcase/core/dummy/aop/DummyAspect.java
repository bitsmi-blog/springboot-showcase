package com.bitsmi.springbootshowcase.core.dummy.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DummyAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyAspect.class);

    @Pointcut("within(com.bitsmi.springbootshowcase.core.dummy..*)")
    public void dummyPackagePointcut() { }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void servicePointcut() { }

    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void cacheableMethod() { }

    @Before("dummyPackagePointcut() && servicePointcut() && cacheableMethod()")
    public void beforeDummyServiceCacheableMethod(JoinPoint joinPoint)
    {
        LOGGER.info("[beforeDummyServiceCacheableMethod] Aspect executed before ({})", joinPoint.getSignature().getName());
    }

    @Around("dummyPackagePointcut() && servicePointcut() && cacheableMethod()")
    public Object aroundDummyServiceCacheableMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        LOGGER.info("[aroundDummyServiceCacheableMethod] Before ({})", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        LOGGER.info("[aroundDummyServiceCacheableMethod] After ({}) with result ({})", proceedingJoinPoint.getSignature().getName(), result);

        return result;
    }

    @Before("execution(* com.bitsmi.springbootshowcase.core.dummy.ISampleService.*(..))")
    public void beforeDummyServiceMethodExecution(JoinPoint joinPoint)
    {
        LOGGER.info("[beforeDummyServiceMethodExecution] Aspect executed before ({})", joinPoint.getSignature().getName());
    }
}
