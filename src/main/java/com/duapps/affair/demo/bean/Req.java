package com.duapps.affair.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Req {
    private String nickName;
    private int pageSize;
    private int currentPage;

    public int getLimit() {
        return pageSize;
    }

    public int getOffset() {
        return (getCurrentPage() - 1) * getPageSize();
    }
}
