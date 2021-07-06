package com.duapps.affair.demo.aspect.controllerAop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class ControllerAop {
    private Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    private static final ThreadLocal<Long> REQUEST_BEGIN_TIME_TL = new ThreadLocal<>();

    public static final ThreadLocal<Method> REQUEST_METHOD = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Pointcut("execution(* *..*controller..*.*(..))")
    public void requestHook() {
    }

    private static final Map<String, Method[]> BEFORE_METHODS = new ConcurrentHashMap<>();
    private static final Map<String, Method[]> AFTER_METHODS = new ConcurrentHashMap<>();
    private static final Map<String, Method[]> FINALLY_METHODS = new ConcurrentHashMap<>();

    @Around(value = "ControllerAop.requestHook()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Class<?> clazz = pjp.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        Method[] beforeMethods = BEFORE_METHODS.get(clazz.getName());
        Method[] afterMethods = AFTER_METHODS.get(clazz.getName());
        Method[] finallyMethods = FINALLY_METHODS.get(clazz.getName());

        if (beforeMethods == null || afterMethods == null || finallyMethods == null) {
            Method[] methods = clazz.getMethods();
            beforeMethods = Arrays.stream(methods)
                    .filter(m -> m.getAnnotation(Before.class) != null)
                    .sorted(Comparator.comparingInt(m -> m.getAnnotation(Before.class).priority()))
                    .toArray(Method[]::new);
            afterMethods = Arrays.stream(methods)
                    .filter(m -> m.getAnnotation(After.class) != null)
                    .sorted(Comparator.comparingInt(m -> m.getAnnotation(After.class).priority()))
                    .toArray(Method[]::new);
            finallyMethods = Arrays.stream(methods)
                    .filter(m -> m.getAnnotation(Finally.class) != null)
                    .sorted(Comparator.comparingInt(m -> m.getAnnotation(Finally.class).priority()))
                    .toArray(Method[]::new);
            BEFORE_METHODS.put(clazz.getName(), beforeMethods);
            AFTER_METHODS.put(clazz.getName(), afterMethods);
            FINALLY_METHODS.put(clazz.getName(), finallyMethods);
        }
        try {
            Method method = methodSignature.getMethod();
            REQUEST_BEGIN_TIME_TL.set(System.currentTimeMillis());
            REQUEST_METHOD.set(method);
            doBefore(pjp, methodSignature, beforeMethods);
            Object obj = pjp.proceed();
            doAfter(pjp, methodSignature, afterMethods);
            return obj;
        } finally {
            doFinally(pjp, methodSignature, finallyMethods);
        }
    }

    @AfterReturning(returning = "ret", pointcut = "requestHook()")
    public void doAfterReturning(Object ret) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        logger.info("[SPEND TIME: " + (System.currentTimeMillis() - REQUEST_BEGIN_TIME_TL.get()) + " ms] "
                + request.getRequestURI());
        REQUEST_BEGIN_TIME_TL.remove();
        REQUEST_METHOD.remove();
    }

    public String getClientIp() {
        if (request != null) {
            String forwardedForStr = request.getHeader("x-forwarded-for");
            if (StringUtils.isNotBlank(forwardedForStr)) {
                return forwardedForStr.split(",")[0];
            }
            String realIpStr = request.getHeader("x-real-ip");
            if (StringUtils.isNotBlank(realIpStr)) {
                return realIpStr;
            }
            return request.getRemoteAddr();
        }
        return "";
    }

    private void doBefore(ProceedingJoinPoint pjp, MethodSignature methodSignature, Method[] methods) throws Throwable {
        Class<?> clazz = pjp.getTarget().getClass();
        methods = Arrays.stream(methods)
                .filter(m -> checkOnlyAnno(clazz, methodSignature, m.getAnnotation(Before.class).only()))
                .filter(m -> checkUnlessAnno(clazz, methodSignature, m.getAnnotation(Before.class).unless()))
                .toArray(Method[]::new);
        for (Method m : methods) {
            doMethod(m, pjp, clazz, methodSignature.getMethod());
        }
    }

    private void doAfter(ProceedingJoinPoint pjp, MethodSignature methodSignature, Method[] methods) throws Throwable {
        Class<?> clazz = pjp.getTarget().getClass();
        methods = Arrays.stream(methods)
                .filter(m -> checkOnlyAnno(clazz, methodSignature, m.getAnnotation(After.class).only()))
                .filter(m -> checkUnlessAnno(clazz, methodSignature, m.getAnnotation(After.class).unless()))
                .toArray(Method[]::new);
        for (Method m : methods) {
            doMethod(m, pjp, clazz, methodSignature.getMethod());
        }
    }

    private void doFinally(ProceedingJoinPoint pjp, MethodSignature methodSignature, Method[] methods) throws Throwable {
        Class<?> clazz = pjp.getTarget().getClass();
        methods = Arrays.stream(methods)
                .filter(m -> checkOnlyAnno(clazz, methodSignature, m.getAnnotation(Finally.class).only()))
                .filter(m -> checkUnlessAnno(clazz, methodSignature, m.getAnnotation(Finally.class).unless()))
                .toArray(Method[]::new);
        for (Method m : methods) {
            doMethod(m, pjp, clazz, methodSignature.getMethod());
        }
    }

    private void doMethod(Method m, ProceedingJoinPoint pjp, Class<?> targetClazz, Method targetMethod) throws Throwable {
        try {

            m.invoke(pjp.getTarget(), request, response, targetClazz, targetMethod);
        } catch (Exception e) {
            if (e.getCause() != null) {
                throw e.getCause();
            } else {
                throw e;
            }
        }
    }

    private boolean checkOnlyAnno(Class<?> clazz, MethodSignature methodSignature, String[] only) {
        if (only.length == 0) {
            return true;
        }
        String mm = clazz.getName() + "." + methodSignature.getName();
        for (String s : only) {
            if (mm.toLowerCase().endsWith(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkUnlessAnno(Class<?> clazz, MethodSignature methodSignature, String[] unless) {
        if (unless.length == 0) {
            return true;
        }
        String mm = clazz.getName() + "." + methodSignature.getName();
        for (String s : unless) {
            if (mm.toLowerCase().endsWith(s.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
