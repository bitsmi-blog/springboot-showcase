package com.bitsmi.springbootshowcase.springcore.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EnhancedAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EnhancedAspect.class);

    private final StringEnhancer enhancer;

    public EnhancedAspect(StringEnhancer enhancer)
    {
        this.enhancer = enhancer;
    }

    @Pointcut("within(com.bitsmi.springbootshowcase.springcore.aop.service..*)")
    public void servicePackagePointcut() { }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void servicePointcut() { }

    @Pointcut("@annotation(com.bitsmi.springbootshowcase.springcore.aop.util.Enhanced)")
    public void enhancedMethod() { }

    @Around("servicePackagePointcut() && servicePointcut() && enhancedMethod()")
    public Object aroundServicePackageEnhancedMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        LOGGER.info("[aroundServicePackageEnhancedMethod] Before ({})", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        LOGGER.info("[aroundServicePackageEnhancedMethod] After ({}) with result ({})", proceedingJoinPoint.getSignature().getName(), result);

        return enhancer.enhance(result);
    }
}
