package com.jonsson.controller;

import cn.hutool.core.util.StrUtil;
import com.jonsson.entity.Order;
import com.jonsson.entity.vo.OrderVO;
import com.jonsson.entity.vo.Result;
import com.jonsson.security.util.SecurityUtil;
import com.jonsson.service.OrderService;
import com.jonsson.service.ProductService;
import com.jonsson.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PostMapping("/selectPage")
    @RequiresPermissions({"order:select"})
    public Result<Object> selectPage(@RequestBody OrderVO orderVO) {
        return Result.success(orderService.selectPage(orderVO));
    }

    @PostMapping("/getById")
    @RequiresPermissions({"order:select"})
    public Result<Object> getById(@RequestBody OrderVO orderVO) {
        Order order = orderService.lambdaQuery().eq(Order::getCreator, SecurityUtil.getCurrentUser().getId())
                .eq(Order::getId, orderVO.getId()).one();
        if (order != null) order.setProduct(productService.getById(order.getProductId()));
        return Result.success(order);
    }

    /**
     * 删除订单
     * 需要管理员权限
     *
     * @param orderVO
     * @return
     */
    @PostMapping("/removeById")
    @RequiresPermissions({"order:delete"})
    public Result<Object> removeById(@RequestBody OrderVO orderVO) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        boolean remove = orderService.lambdaUpdate().in(Order::getCreator, longs)
                .eq(Order::getId, orderVO.getId()).remove();
        if (remove) log.info("用户id{}删除了订单，订单号为{}", SecurityUtil.getCurrentUser().getId(), orderVO.getOutTradeNo());
        return Result.success();
    }

    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"order:update", "order:add"})
    public Result<Object> saveOrUpdate(@RequestBody Order order) {
        Integer count = orderService.lambdaQuery()
                .eq(StrUtil.isNotBlank(order.getSubject()), Order::getSubject, order.getSubject())
                .ne(order.getId() != null, Order::getId, order.getId())
                .count();
        if (count > 0) return Result.fail("订单标题或者订单号已存在");
        boolean update = orderService.lambdaUpdate()
                .set(StrUtil.isNotBlank(order.getSubject()), Order::getSubject, order.getSubject())
                .set(order.getState() != null, Order::getState, order.getState())
                .update();
        if (update) log.info("用户id{}更新了订单{}", SecurityUtil.getCurrentUser().getId(), order);
        return Result.success();
    }
}
