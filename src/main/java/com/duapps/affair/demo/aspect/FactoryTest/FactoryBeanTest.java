package com.duapps.affair.demo.aspect.FactoryTest;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Author: he.zhou
 * @Date: 2019-01-24
 */
//@Component
public class FactoryBeanTest implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
