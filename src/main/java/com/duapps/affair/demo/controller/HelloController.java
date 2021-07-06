package com.duapps.affair.demo.controller;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duapps.affair.demo.aspect.controllerAop.Public;
import com.duapps.affair.demo.bean.UserInfo;
import com.duapps.affair.demo.dao.UserDao;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.hibernate.annotations.GeneratorType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @Author he.zhou
 * @Date 2020/5/20
 */
@RestController
@RequestMapping("/users")
public class HelloController extends BaseController {
    @Resource
    private UserDao userDao;

    @Public
    @GetMapping("on")
    public Object getOne() {
        UserInfo user=new UserInfo();
        user.setId(0L);
        user.setUserId("3232");
        user.setNickName("3232");
        user.setAvatar("32323");
        user.setOpenId("3232");
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        return user;
    }

    @GetMapping("on1")
    public Object getOne1() {
        UserInfo user=new UserInfo();
        user.setId(0L);
        user.setUserId("3232");
        user.setNickName("3232");
        user.setAvatar("32323");
        user.setOpenId("3232");
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        return user;
    }

    @RequestMapping("/get")
    public String getUser(long id) {
        UserInfo userInfo = userDao.getOne(id);
        if (Objects.nonNull(userInfo)) {
            return String.valueOf(userInfo.getNickName());
        } else {
            return "对象为空";
        }
    }

    @PostMapping("/add")
    public String addUser(@RequestBody UserInfo userInfo) {
        System.out.println(JSONObject.toJSONString(userInfo));
        UserInfo userInfo1 = userDao.save(userInfo);
        return String.valueOf(userInfo1.getId());
    }

    public static void main(String[] args) {
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setOpenId("ewewe");
        userInfo1.setNickName("21212");
        userInfo1.setAvatar("dsdsdsd");
        System.out.println(JSONObject.toJSONString(userInfo1));
    }
}
