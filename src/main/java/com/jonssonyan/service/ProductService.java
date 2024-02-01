package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.Product;
import com.jonssonyan.entity.dto.ProductDto;

import java.util.List;

public interface ProductService extends IService<Product> {
    List<Product> selectByCategoryId(ProductDto productVO);

    IPage<Product> selectPage(ProductDto productVO);
}
