package com.duapps.affair.demo.aspect.annotationTest;

import org.springframework.stereotype.Component;

@Component
public class AutoService2 {

    @LogAnnotation(value = "我是计算测试减法", name = "323233")
    public int substract(int i, int j) {

        int result = i + j;
        return result;
    }


    public int add(int i, int j) {

        int result = i + j;
        return result;
    }



}
