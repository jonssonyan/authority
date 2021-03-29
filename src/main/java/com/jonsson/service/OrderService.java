package com.jonsson.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.OrderDao;
import com.jonsson.dao.ProductDao;
import com.jonsson.entity.Order;
import com.jonsson.entity.vo.OrderVO;
import com.jonsson.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderService extends ServiceImpl<OrderDao, Order> {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserService userService;

    public IPage<Order> selectPage(OrderVO orderVO) {
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