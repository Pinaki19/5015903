package com.library.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.library.*.*(..))")
    public void logBefore() {
        logger.info("Method execution started");
    }

    @After("execution(* com.example.library.*.*(..))")
    public void logAfter() {
        logger.info("Method execution completed");
    }
}
