package com.jonsson.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jonsson.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderDao extends BaseMapper<Order> {
    void orderUpdateStateByOutTradeNo(@Param("outTradeNo") String outTradeNo, @Param("state") Integer state);
}