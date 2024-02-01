package com.jonssonyan.controller;

import cn.hutool.core.util.StrUtil;
import com.jonssonyan.entity.Order;
import com.jonssonyan.entity.dto.OrderDto;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.OrderService;
import com.jonssonyan.service.ProductService;
import com.jonssonyan.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "订单")
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询订单")
    @GetMapping("/selectPage")
    @RequiresPermissions({"order:select"})
    public Result selectPage(OrderDto orderVO) {
        return Result.success(orderService.selectPage(orderVO));
    }

    @ApiOperation(value = "根据id查询订单")
    @GetMapping("/getById")
    @RequiresPermissions({"order:select"})
    public Result getById(OrderDto orderVO) {
        Order order = orderService.lambdaQuery().eq(Order::getCreator, SecurityUtil.getCurrentUser().getId())
                .eq(Order::getId, orderVO.getId()).one();
        if (order != null) order.setProduct(productService.getById(order.getProductId()));
        return Result.success(order);
    }

    @ApiOperation(value = "删除订单")
    @PostMapping("/removeById")
    @RequiresPermissions({"order:delete"})
    public Result removeById(@RequestBody OrderDto orderVO) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        boolean remove = orderService.lambdaUpdate().in(Order::getCreator, longs)
                .eq(Order::getId, orderVO.getId()).remove();
        if (remove) log.info("用户id{}删除了订单，订单号为{}", SecurityUtil.getCurrentUser().getId(), orderVO.getOutTradeNo());
        return Result.success();
    }

    @ApiOperation(value = "添加或者更新订单")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"order:update", "order:add"})
    public Result saveOrUpdate(@RequestBody Order order) {
        Integer count = orderService.lambdaQuery()
                .eq(StrUtil.isNotBlank(order.getSubject()), Order::getSubject, order.getSubject())
                .ne(order.getId() != null, Order::getId, order.getId())
                .count();
        if (count > 0) return Result.fail("订单标题或者订单号已存在");
        boolean update = orderService.lambdaUpdate()
                .set(StrUtil.isNotBlank(order.getSubject()), Order::getSubject, order.getSubject())
                .set(StrUtil.isNotBlank(order.getAddress()), Order::getAddress, order.getAddress())
                .set(order.getState() != null, Order::getState, order.getState())
                .update();
        if (update) log.info("用户id{}更新了订单{}", SecurityUtil.getCurrentUser().getId(), order);
        return Result.success();
    }
}
