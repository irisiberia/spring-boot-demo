package com.duapps.affair.demo.dao;

import com.duapps.affair.demo.bean.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author he.zhou
 * @Date 2020/5/20
 */
public interface UserDao extends JpaRepository<UserInfo, Long> {

    @Query("select  u from UserInfo u where u.nickName = :nickName")
    UserInfo findUserByName(@Param("nickName") String nickName);

    @Transactional
    @Modifying
    @Query("update UserInfo sc set sc.nickName = :nickName ,sc.avatar= :avatar where sc.id in :ids")
    Integer updateByNme(@Param("avatar") String avatar, @Param("nickName") String nickName, @Param("ids") List<Integer> ids);


}
