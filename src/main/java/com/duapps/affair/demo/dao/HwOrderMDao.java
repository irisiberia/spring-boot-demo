package com.duapps.affair.demo.dao;import com.baomidou.mybatisplus.core.mapper.BaseMapper;import com.duapps.affair.demo.bean.HwOrder;import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Select;import java.util.List;/** * @Author he.zhou * @Date 2020-12-05 */@Mapperpublic interface HwOrderMDao extends BaseMapper<HwOrder> {    @Select("select * from hw_order where order_id=#{s} ")    List<HwOrder> getByOrderId(String s);}