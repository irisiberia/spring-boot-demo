package com.duapps.affair.demo.elasticsearch.util;import java.lang.annotation.ElementType;import java.lang.annotation.Retention;import java.lang.annotation.RetentionPolicy;import java.lang.annotation.Target;/** * @Author he.zhou * @Date 2020-08-25 */@Target({ElementType.FIELD})@Retention(RetentionPolicy.RUNTIME)public @interface EsIn {    /**     * filed name     */    String name() default "";}