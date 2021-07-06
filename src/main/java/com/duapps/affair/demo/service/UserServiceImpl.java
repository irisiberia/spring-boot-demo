package com.duapps.affair.demo.service;

import com.duapps.affair.demo.bean.Req;
import com.duapps.affair.demo.bean.UserInfo;
import com.duapps.affair.demo.dao.UserDao;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.util.List;

/**
 * @Author he.zhou
 * @Date 2020/5/20
 */
@Component
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public UserInfo getById(long id) {

        return userDao.getOne(id);

    }

    @Override
    public void save(UserInfo userInfo) {
        userDao.save(userInfo);
    }

    @Override
    public UserInfo getByNickName(String nickName) {
        return userDao.findUserByName(nickName);
    }

    @Override
    public List<UserInfo> queryAllByEx(Req req) {


        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(req.getNickName());


        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id");
        Example<UserInfo> example = Example.of(userInfo, matcher);

        PageRequest request = PageRequest.of(req.getCurrentPage()-1, req.getPageSize(), Sort.Direction.DESC, "id");
        Page<UserInfo> page = userDao.findAll(example, request);
        return page.getContent();

    }


}
