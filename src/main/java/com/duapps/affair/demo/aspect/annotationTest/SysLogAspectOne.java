package com.duapps.affair.demo.aspect.annotationTest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: he.zhou
 * @Date: 2019-02-01
 * <p>
 * <p>
 * 基于注解@Aspect的AOP实现
 * com.automan.siberia.annotationTest下面所有的方法都能实现aop
 */
@Aspect
@Component
public class SysLogAspectOne {

    private static final Map<String, Method[]> BEFORE_METHODS = new ConcurrentHashMap<>();
    private static final Map<String, Method[]> AFTER_METHODS = new ConcurrentHashMap<>();
    private static final Map<String, Method[]> FINALLY_METHODS = new ConcurrentHashMap<>();


    /**
     * 前置通知：目标方法执行之前执行以下方法体的内容
     *
     * @param jp
     */
    @Before("execution(* com.duapps.affair.demo.aspect.annotationTest.*.*(..))")
    public void beforeMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("【前置通知】the method 【" + methodName + "】 begins with " + Arrays.asList(jp.getArgs()));
    }

    /**
     * 返回通知：目标方法正常执行完毕时执行以下代码
     *
     * @param jp
     * @param result
     */
    @AfterReturning(value = "execution(* com.duapps.affair.demo.aspect.annotationTest.*.*(..))", returning = "result")
    public void afterReturningMethod(JoinPoint jp, Object result) {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Method methodReal = null;
        try {
            methodReal = jp.getTarget().getClass().getDeclaredMethod(method.getName(),
                    method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //通过反射也能获取到自定义注解
        LogAnnotation tranSpondAspect = methodReal.getAnnotation(LogAnnotation.class);
        LogAnnotation tranSpondAspect1 = methodReal.getDeclaredAnnotation(LogAnnotation.class);
        if (Objects.isNull(tranSpondAspect)) {
            System.out.println("没有添加自定义注解");
        } else {
            System.out.println("添加了自定义注解" + tranSpondAspect.value());
        }

        String methodName = jp.getSignature().getName();

        System.out.println("【返回通知】the method 【" + methodName + "】 ends with 【" + result + "】");
    }

    /**
     * 后置通知：目标方法执行之后执行以下方法体的内容，不管是否发生异常。
     *
     * @param jp
     */
    @After("execution(* com.duapps.affair.demo.aspect.annotationTest.*.*(..))")
    public void afterMethod(JoinPoint jp) {
        System.out.println("【后置通知】this is a afterMethod advice...");
    }

    /**
     * 异常通知：目标方法发生异常的时候执行以下代码
     */
    @AfterThrowing(value = "execution(* com.duapps.affair.demo.aspect.annotationTest.*.*(..))", throwing = "e")
    public void afterThorwingMethod(JoinPoint jp, NullPointerException e) {
        String methodName = jp.getSignature().getName();
        System.out.println("【异常通知】the method 【" + methodName + "】 occurs exception: " + e);
    }

    /**
     * 环绕通知：目标方法执行前后分别执行一些代码，发生异常的时候执行另外一些代码
     *
     * @return
     */
    @Around(value = "execution(* com.duapps.affair.demo.aspect.annotationTest.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//        Method method = ((MethodSignature) jp.getSignature()).getMethod();
//        Object result = null;
//        try {
//            System.out.println("【环绕通知中的--->前置通知】：the method 【" + methodName + "】 begins with " + Arrays.asList(jp.getArgs()));
//            //执行目标方法
//            result = jp.proceed();
//            System.out.println("【环绕通知中的--->返回通知】：the method 【" + methodName + "】 ends with " + result);
//        } catch (Throwable e) {
//            System.out.println("【环绕通知中的--->异常通知】：the method 【" + methodName + "】 occurs exception " + e);
//        }
//
//        System.out.println("【环绕通知中的--->后置通知】：-----------------end.----------------------");
//        return 9999;


        Class<?> clazz = jp.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();

        Method[] beforeMethods = BEFORE_METHODS.get(clazz.getName());
        Method[] afterMethods = AFTER_METHODS.get(clazz.getName());
        Method[] finallyMethods = FINALLY_METHODS.get(clazz.getName());

        if (beforeMethods == null || afterMethods == null || finallyMethods == null) {
            Method[] methods = clazz.getMethods();
            beforeMethods = Arrays.stream(methods)
                    .filter(m -> m.getAnnotation(LogAnnotation.class) != null)
                    .toArray(Method[]::new);

            BEFORE_METHODS.put(clazz.getName(), beforeMethods);
            AFTER_METHODS.put(clazz.getName(), afterMethods);
            FINALLY_METHODS.put(clazz.getName(), finallyMethods);
        }

            Method method = methodSignature.getMethod();

        Object obj = null;
        try {
            obj = jp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return obj;

    }

}
