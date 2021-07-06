package com.duapps.affair.demo.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhouhe
 * @date Create at 18:15 2019/2/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreTags {
    private String key;

    private String value;

    private String showName;
}
