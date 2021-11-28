package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.Order;
import com.jonssonyan.entity.vo.OrderVo;

public interface OrderService extends IService<Order> {
    /**
     * 分页查询订单
     *
     * @param orderVO
     * @return
     */
    IPage<Order> selectPage(OrderVo orderVO);
}
