package com.jonssonyan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.CategoryDao;
import com.jonssonyan.entity.Category;
import com.jonssonyan.entity.dto.CategoryDto;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.CategoryService;
import com.jonssonyan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public IPage<Category> selectPage(CategoryDto categoryDto) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        return lambdaQuery().like(StrUtil.isNotBlank(categoryDto.getName()), Category::getName, categoryDto.getName())
                .in(Category::getCreator, longs)
                .eq(null != categoryDto.getState(), Category::getState, categoryDto.getState())
                .eq(null != categoryDto.getParentId(), Category::getParentId, categoryDto.getParentId())
                .between(null != categoryDto.getStartTime() && null != categoryDto.getEndTime(), Category::getCreateTime, categoryDto.getStartTime(), categoryDto.getCreateTime())
                .orderByDesc(Category::getCreateTime)
                .page(new Page<>(categoryDto.getPageNum(), categoryDto.getPageSize()));
    }

    @Override
    public List<Category> selectList(Long id, Boolean bool) {
        Category category = getById(id);
        List<Category> categories = selectByPath(category.getId(), category.getPath());
        if (bool) categories.add(category);
        return categories;
    }

    @Override
    public List<Category> selectByPath(Long id, String path) {
        return categoryDao.selectByPath((path == null ? "" : path) + id + "-");
    }

    @Override
    public List<Category> selectChilds(Long creator) {
        List<Long> longs = userService.selectChild(creator, true);
        if (CollectionUtil.isNotEmpty(longs)) return new ArrayList<>();
        List<Category> categories = lambdaQuery()
                .or(categoryLambdaQueryWrapper -> categoryLambdaQueryWrapper.isNull(Category::getParentId).or().eq(Category::getParentId, ""))
                .in(Category::getCreator, longs)
                .list();
        for (Category category : categories) {
            List<Category> categories1 = selectChild(category.getId());
            category.setCategorys(categories1);
        }
        return categories;
    }

    @Override
    public List<Category> selectChild(Long id) {
        List<Category> categoryList = lambdaQuery().eq(Category::getParentId, id).list();
        if (CollectionUtil.isEmpty(categoryList)) return null;
        for (Category category1 : categoryList) {
            List<Category> categoryList1 = selectChild(category1.getId());
            category1.setCategorys(categoryList1);
        }
        return categoryList;
    }
}