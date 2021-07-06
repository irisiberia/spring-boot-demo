package com.duapps.affair.demo.elasticsearch.model;import com.duapps.affair.demo.elasticsearch.request.EsBaseReq;import org.apache.commons.lang3.StringUtils;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.data.domain.Sort;/** * @Author he.zhou * @Date 2020-08-25 */public class EsPageable {    public static Pageable getQueryPageable(EsBaseReq req) {        return PageRequest.of(req.getCurrentPage() - 1, req.getPageSize());    }    public static Sort getQuerySort(EsBaseReq request) {        if (StringUtils.isEmpty(request.getSortFiled())) {            return Sort.by(Sort.Direction.DESC, "id");        } else {            return Sort.by(request.getDirection(), request.getSortFiled());        }    }}