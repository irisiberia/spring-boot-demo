package com.duapps.affair.demo.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
public class StoreDocument extends EsBaseEntity {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String code;
    /**
     * 基础信息
     */
    @Field(type = FieldType.Object)
    private StoreBaseInfo baseInfo;
    /**
     * 标签
     */
    @Field(type = FieldType.Object)
    private List<StoreTags> tags;

}

