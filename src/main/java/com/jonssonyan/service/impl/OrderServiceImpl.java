package com.jonssonyan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.OrderDao;
import com.jonssonyan.dao.ProductDao;
import com.jonssonyan.entity.Order;
import com.jonssonyan.entity.dto.OrderDto;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.OrderService;
import com.jonssonyan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserService userService;

    @Override
    public IPage<Order> selectPage(OrderDto orderVO) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        IPage<Order> orderIPage = lambdaQuery()
                .in(Order::getCreator, longs)
                .like(!StrUtil.isBlank(orderVO.getSubject()), Order::getSubject, orderVO.getSubject())
                .like(!StrUtil.isBlank(orderVO.getOutTradeNo()), Order::getOutTradeNo, orderVO.getOutTradeNo())
                .eq(null != orderVO.getState(), Order::getState, orderVO.getState())
                .between(null != orderVO.getStartTime() && null != orderVO.getEndTime(), Order::getCreateTime, orderVO.getStartTime(), orderVO.getEndTime())
                .eq(Order::getCreator, SecurityUtil.getCurrentUser().getId())
                .orderByDesc(Order::getCreateTime)
                .page(new Page<>(orderVO.getPageNum(), orderVO.getPageSize()));
        orderIPage.getRecords().forEach(order -> {
            order.setProduct(productDao.selectById(order.getProductId()));
        });
        return orderIPage;
    }
}