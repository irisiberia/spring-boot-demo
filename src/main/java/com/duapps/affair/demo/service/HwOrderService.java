package com.duapps.affair.demo.service;import com.duapps.affair.demo.bean.HwOrder;import com.duapps.affair.demo.bean.OrderRequest;import com.duapps.affair.demo.bean.Pager;import java.util.List;/** * @Author he.zhou * @Date 2020-12-02 */public interface HwOrderService {    void insert(HwOrder hwOrder);    HwOrder getOrderByOrderId(String orderId);    void updateConsumptionState(String orderId, int i);    List<HwOrder> getOrderByOpenIds(List<String> openIds);    HwOrder selectById(Long id);    void update(HwOrder order);    Pager<HwOrder> getByPageRequest(OrderRequest request);}