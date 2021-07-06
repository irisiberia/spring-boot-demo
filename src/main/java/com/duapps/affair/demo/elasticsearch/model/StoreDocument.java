package com.duapps.affair.demo.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * 门店Document
 *
 * @author zhouhe
 * @date Create at 19:31 2019/8/22
 */

@Data
@Document(indexName = "store")
@AllArgsConstructor
@NoArgsConstructor
public class StoreDocument extends EsBaseEntity{

    @Id
    private Long id;

    private String code;
    /**
     * 基础信息
     */
    private StoreBaseInfo baseInfo;
    /**
     * 标签
     */
    private List<StoreTags> tags;

}

