package com.jonssonyan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.ProductDao;
import com.jonssonyan.entity.Category;
import com.jonssonyan.entity.Product;
import com.jonssonyan.entity.dto.ProductDto;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.CategoryService;
import com.jonssonyan.service.ProductService;
import com.jonssonyan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @Override
    public List<Product> selectByCategoryId(ProductDto productDto) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        return lambdaQuery()
                .eq(Product::getCategoryId, productDto.getCategoryId())
                .in(Product::getCreator, longs)
                .orderByDesc(Product::getState)
                .list();
    }

    @Override
    public IPage<Product> selectPage(ProductDto productDto) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        IPage<Product> productIPage = lambdaQuery().like(!StrUtil.isBlank(productDto.getName()), Product::getName, productDto.getName())
                .eq(null != productDto.getState(), Product::getState, productDto.getState())
                .eq(null != productDto.getCategoryId(), Product::getCategoryId, productDto.getCategoryId())
                .between(null != productDto.getStartTime() && null != productDto.getEndTime(), Product::getCreateTime, productDto.getStartTime(), productDto.getEndTime())
                .in(Product::getCreator, longs)
                .orderByDesc(Product::getCreateTime)
                .page(new Page<>(productDto.getPageNum(), productDto.getPageSize()));
        productIPage.getRecords().forEach(product -> {
            Category category = categoryService.getById(product.getCategoryId());
            product.setCategory(category);
        });
        return productIPage;
    }
}