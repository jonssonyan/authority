package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.Product;
import com.jonssonyan.entity.vo.ProductVo;

import java.util.List;

public interface ProductService extends IService<Product> {
    List<Product> selectByCategoryId(ProductVo productVO);

    IPage<Product> selectPage(ProductVo productVO);
}
