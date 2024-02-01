package com.jonssonyan.controller;


import cn.hutool.core.util.StrUtil;
import com.jonssonyan.entity.Category;
import com.jonssonyan.entity.Product;
import com.jonssonyan.entity.dto.ProductDto;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.CategoryService;
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
@Api(tags = "商品")
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "通过id查询产品")
    @GetMapping("/getById")
    @RequiresPermissions({"product:select"})
    public Result getById(ProductDto productDto) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        Product product = productService.lambdaQuery().in(Product::getCreator, longs)
                .eq(Product::getId, productDto.getId()).one();
        Category category = categoryService.getById(product.getCategoryId());
        product.setCategory(category);
        return Result.success(product);
    }

    @ApiOperation(value = "查询产品列表")
    @GetMapping("/selectList")
    @RequiresPermissions({"product:select"})
    public Result selectList(ProductDto productDto) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        List<Product> products = productService.lambdaQuery().in(Product::getCreator, longs).list();
        return Result.success(products);
    }

    @ApiOperation(value = "查看指定分类的产品")
    @GetMapping("/selectByCategoryId")
    @RequiresPermissions({"product:select"})
    public Result selectByCategoryId(ProductDto productDto) {
        return Result.success(productService.selectByCategoryId(productDto));
    }

    @ApiOperation(value = "分页查询产品信息")
    @GetMapping("/selectPage")
    @RequiresPermissions({"product:select"})
    public Result selectPage(ProductDto productDto) {
        return Result.success(productService.selectPage(productDto));
    }

    @ApiOperation(value = "删除产品")
    @PostMapping("/removeById")
    @RequiresPermissions({"product:delete"})
    public Result removeById(@RequestBody ProductDto productDto) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        boolean remove = productService.lambdaUpdate().in(Product::getCreator, longs)
                .eq(Product::getId, productDto.getId()).remove();
        if (remove) log.info("用户id{}删除商品{}", SecurityUtil.getCurrentUser().getId(), productDto.getName());
        return Result.success();
    }

    @ApiOperation(value = "修改或者添加产品信息")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"product:update", "product:add"})
    public Result saveOrUpdate(@RequestBody Product product) {
        Integer count = productService.lambdaQuery().eq(StrUtil.isNotBlank(product.getName()), Product::getName, product.getName())
                .ne(product.getId() != null, Product::getId, product.getId()).count();
        if (count > 0) return Result.fail("该商品名称已存在");
        product.setCreator(product.getId() == null ? SecurityUtil.getCurrentUser().getId() : null);
        productService.saveOrUpdate(product);
        return Result.success();
    }
}