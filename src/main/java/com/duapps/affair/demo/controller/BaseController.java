package com.duapps.affair.demo.controller;

import com.duapps.affair.demo.aspect.controllerAop.Before;
import com.duapps.affair.demo.aspect.controllerAop.Finally;
import com.duapps.affair.demo.bean.R;
import com.duapps.affair.demo.bean.ThreadLocals;
import com.duapps.affair.demo.bean.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class BaseController {

    @Before
    public void doBefore(HttpServletRequest request, HttpServletResponse response, Class<?> targetClass, Method targetMethod) throws Exception {

//        String loginToken = request.getHeader("ltoken");
//        if (StringUtils.isBlank(loginToken)) {
//            throw new Exception();
//        }
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(21212L);
//
//        ThreadLocals.setUser(userInfo);
    }

    @Finally
    public void doFinally(HttpServletRequest request, HttpServletResponse response, Class<?> targetClass, Method targetMethod) {
        ThreadLocals.clear();
    }
}


