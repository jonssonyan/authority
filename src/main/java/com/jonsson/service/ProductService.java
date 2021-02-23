package com.jonsson.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.ProductDao;
import com.jonsson.entity.Category;
import com.jonsson.entity.Product;
import com.jonsson.entity.vo.ProductVO;
import com.jonsson.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService extends ServiceImpl<ProductDao, Product> {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    public List<Product> selectByCategoryId(ProductVO productVO) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        return lambdaQuery()
                .eq(Product::getCategoryId, productVO.getCategoryId())
                .in(Product::getCreator, longs)
                .orderByDesc(Product::getState)
                .list();
    }

    public IPage<Product> selectPage(ProductVO productVO) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        IPage<Product> productIPage = lambdaQuery().like(!StrUtil.isBlank(productVO.getName()), Product::getName, productVO.getName())
                .eq(null != productVO.getState(), Product::getState, productVO.getState())
                .eq(null != productVO.getCategoryId(), Product::getCategoryId, productVO.getCategoryId())
                .between(null != productVO.getStartTime() && null != productVO.getEndTime(), Product::getCreateTime, productVO.getStartTime(), productVO.getEndTime())
                .in(Product::getCreator, longs)
                .orderByDesc(Product::getCreateTime)
                .page(new Page<>(productVO.getPageNum(), productVO.getPageSize()));
        productIPage.getRecords().forEach(product -> {
            Category category = categoryService.getById(product.getCategoryId());
            product.setCategory(category);
        });
        return productIPage;
    }
}