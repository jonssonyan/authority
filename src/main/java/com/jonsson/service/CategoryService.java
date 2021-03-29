package com.jonsson.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.CategoryDao;
import com.jonsson.entity.Category;
import com.jonsson.entity.vo.CategoryVO;
import com.jonsson.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryService extends ServiceImpl<CategoryDao, Category> {
    @Autowired
    private UserService userService;

    public IPage<Category> selectPage(CategoryVO categoryVO) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        return lambdaQuery().like(StrUtil.isNotBlank(categoryVO.getName()), Category::getName, categoryVO.getName())
                .in(Category::getCreator, longs)
                .eq(null != categoryVO.getState(), Category::getState, categoryVO.getState())
                .eq(null != categoryVO.getParentId(), Category::getParentId, categoryVO.getParentId())
                .between(null != categoryVO.getStartTime() && null != categoryVO.getEndTime(), Category::getCreateTime, categoryVO.getStartTime(), categoryVO.getCreateTime())
                .orderByDesc(Category::getCreateTime)
                .page(new Page<>(categoryVO.getPageNum(), categoryVO.getPageSize()));
    }

    /**
     * 查询子集分类,返回集合结构
     *
     * @param id
     * @return
     */
    public List<Category> selectCategorys(Long id, Boolean bool) {
        List<Category> categories = new ArrayList<>();
        if (bool) categories.add(lambdaQuery().eq(Category::getId, id).one());
        selectByParentId(id, categories);
        return categories;
    }

    /**
     * 查询子集,不包含自己,返回集合结构
     *
     * @param parentId
     * @param categories
     * @return
     */
    public List<Category> selectByParentId(Long parentId, List<Category> categories) {
        List<Category> list = lambdaQuery().eq(Category::getParentId, parentId).list();
        if (CollectionUtil.isNotEmpty(list)) {
            categories.addAll(list);
            list.forEach(category -> categories.addAll(selectByParentId(category.getId(), categories)));
        }
        return categories;
    }
}