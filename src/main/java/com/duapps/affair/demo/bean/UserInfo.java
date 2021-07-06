package com.duapps.affair.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author he.zhou
 * @Date 2020/5/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Proxy(lazy = false)
@Table(name = "user_info")
@Builder
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String nickName;

    private String avatar;

    private String openId;

    private Date updateTime;
    
    private Date createTime;
}
