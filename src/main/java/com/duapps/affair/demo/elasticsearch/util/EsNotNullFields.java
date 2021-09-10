package com.duapps.affair.demo.elasticsearch.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author he.zhou
 * @Date 2021-09-10
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsNotNullFields {
    /**
     * filed name
     */
    String name() default "";
}
