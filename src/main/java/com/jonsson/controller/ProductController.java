package com.jonsson.controller;


import cn.hutool.core.util.StrUtil;
import com.jonsson.entity.Category;
import com.jonsson.entity.Product;
import com.jonsson.entity.vo.ProductVO;
import com.jonsson.entity.vo.Result;
import com.jonsson.security.util.SecurityUtil;
import com.jonsson.service.CategoryService;
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
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    /**
     * 通过id查询产品信息
     *
     * @param productVO
     * @return
     */
    @PostMapping("/getById")
    public Result<Object> getById(@RequestBody ProductVO productVO) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        Product product = productService.lambdaQuery().in(Product::getCreator, longs)
                .eq(Product::getId, productVO.getId()).one();
        Category category = categoryService.getById(product.getCategoryId());
        product.setCategory(category);
        return Result.success(product);
    }

    @PostMapping("/selectList")
    public Result<Object> selectList(@RequestBody ProductVO productVO) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        List<Product> products = productService.lambdaQuery().in(Product::getCreator, longs).list();
        return Result.success(products);
    }

    /**
     * 查看指定分类的产品
     *
     * @param productVO
     * @return
     */
    @PostMapping("/selectByCategoryId")
    public Result<Object> selectByCategoryId(@RequestBody ProductVO productVO) {
        return Result.success(productService.selectByCategoryId(productVO));
    }

    /**
     * 分页查询产品信息
     * 需要管理员权限
     *
     * @param productVO
     * @return
     */
    @PostMapping("/selectPage")
    public Result<Object> selectPage(@RequestBody ProductVO productVO) {
        return Result.success(productService.selectPage(productVO));
    }

    /**
     * 删除产品
     * 需要管理员权限
     *
     * @param productVO
     * @return
     */
    @PostMapping("/removeById")
    @RequiresPermissions({"product:delete"})
    public Result<Object> removeById(@RequestBody ProductVO productVO) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        boolean remove = productService.lambdaUpdate().in(Product::getCreator, longs)
                .eq(Product::getId, productVO.getId()).remove();
        if (remove) log.info("用户id{}删除商品{}", SecurityUtil.getCurrentUser().getId(), productVO.getName());
        return Result.success();
    }

    /**
     * 修改或者添加产品信息
     * 需要管理员权限
     *
     * @param product
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"product:update", "product:add"})
    public Result<Object> saveOrUpdate(@RequestBody Product product) {
        Integer count = productService.lambdaQuery().eq(StrUtil.isNotBlank(product.getName()), Product::getName, product.getName())
                .ne(product.getId() != null, Product::getId, product.getId()).count();
        if (count > 0) return Result.fail("该商品名称已存在");
        product.setCreator(product.getId() == null ? SecurityUtil.getCurrentUser().getId() : null);
        productService.saveOrUpdate(product);
        return Result.success();
    }
}