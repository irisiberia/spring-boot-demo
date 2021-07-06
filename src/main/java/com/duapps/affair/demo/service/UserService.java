package com.duapps.affair.demo.service;

import com.duapps.affair.demo.bean.Req;
import com.duapps.affair.demo.bean.UserInfo;

import java.util.List;

/**
 * @Author he.zhou
 * @Date 2020/5/20
 */
public interface UserService {
    UserInfo getById(long id);

    void save(UserInfo userInfo);

    UserInfo getByNickName(String nickName);

    List<UserInfo> queryAllByEx(Req req);
}
