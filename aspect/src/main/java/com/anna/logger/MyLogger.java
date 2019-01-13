package com.anna.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

public class MyLogger {
    private static final Logger LOGGER = LogManager.getLogger(MyLogger.class.getName());

    public Object logWebServiceCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        Thread curThread = Thread.currentThread();
        LOGGER.info("From Class " + className + " method " + methodName + " was called on thread " + curThread.getName() + " with args " + Arrays.toString(methodArgs));
        Object result = joinPoint.proceed();
        LOGGER.info("From Class " + className + " method " + methodName + " returned value = " + result + " on thread " + curThread.getName());

        return result;
    }
}