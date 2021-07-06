package com.duapps.affair.demo.aspect.AspectTest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wei.gao on 2017/7/28.
 *
 * 对订单进行数据权限验证
 */
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataAuth {

    /**
     * 要验证的权限
     * @return
     */
    AuthDescription[] authentications();
}
