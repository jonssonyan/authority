package com.jonssonyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jonssonyan.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductDao extends BaseMapper<Product> {
    void productInsert(@Param("product") Product product);

    void productDeleteByIds(@Param("ids") List<Long> ids);

    void productUpdateById(@Param("id") Long id, @Param("product") Product product);
}