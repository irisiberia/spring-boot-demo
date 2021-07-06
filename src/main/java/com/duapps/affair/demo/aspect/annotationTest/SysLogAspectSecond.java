package com.duapps.affair.demo.aspect.annotationTest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author he.zhou
 * @date 2019/9/26
 **/

@Aspect
//@Component
public class SysLogAspectSecond {

    @Around("@annotation(logAnnotation)")
    public Object aroundMethod(ProceedingJoinPoint jp, LogAnnotation logAnnotation) {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        String methodName = jp.getSignature().getName();
        Object result = null;
        return 0;
    }
}
