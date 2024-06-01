package com.bitsmi.springbootshowcase.sampleapps.application.dummy.aop;

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

    @Pointcut("within(com.bitsmi.springbootshowcase.sampleapps.application.dummy..*)")
    public void dummyPackagePointcut() { }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void servicePointcut() { }

    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void cacheableMethod() { }

    @Before("dummyPackagePointcut() && servicePointcut() && cacheableMethod()")
    public void beforeDummyApplicationServiceCacheableMethod(JoinPoint joinPoint)
    {
        LOGGER.info("[beforeDummyApplicationServiceCacheableMethod] Aspect executed before ({})", joinPoint.getSignature().getName());
    }

    @Around("dummyPackagePointcut() && servicePointcut() && cacheableMethod()")
    public Object aroundDummyServiceCacheableMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        LOGGER.info("[aroundDummyApplicationServiceCacheableMethod] Before ({})", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        LOGGER.info("[aroundDummyApplicationServiceCacheableMethod] After ({}) with result ({})", proceedingJoinPoint.getSignature().getName(), result);

        return result;
    }

    @Before("execution(* com.bitsmi.springbootshowcase.sampleapps.application.dummy.ISampleApplicationService.*(..))")
    public void beforeDummyServiceMethodExecution(JoinPoint joinPoint)
    {
        LOGGER.info("[beforeDummyApplicationServiceMethodExecution] Aspect executed before ({})", joinPoint.getSignature().getName());
    }
}
