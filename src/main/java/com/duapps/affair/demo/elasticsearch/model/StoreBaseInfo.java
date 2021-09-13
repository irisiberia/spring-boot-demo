package com.duapps.affair.demo.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 门店基础信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreBaseInfo {

    /**
     * 门店id
     */
    private String storeId;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 门店简称
     */

    private String shortName;

    /**
     * 门店简介
     */
    private String profile;

    /**
     * 门店属性
     */
    private Integer property;

    /**
     * 门店类型
     */

    private Integer type;

    /**
     * 详细地址
     */
    @Field(type = FieldType.Keyword)
    private String address;

    /**
     * 所在城市
     */

    private String cityCode;

    /**
     * 城市名称
     */

    private String cityName;

    /**
     * 所在省份
     */

    private String provinceCode;

    /**
     * 省份名称
     */

    private String provinceName;

    /**
     * 所在地区
     */

    private String regionCode;
    /**
     * 创建时间
     */

    public Date createdTime;


    /**
     * 修改时间
     */
    private Date updatedTime;
}