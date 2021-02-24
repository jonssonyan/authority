package com.jonsson.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.jonsson.entity.Category;
import com.jonsson.entity.vo.CategoryVO;
import com.jonsson.entity.vo.Result;
import com.jonsson.security.utils.SecurityUtil;
import com.jonsson.service.CategoryService;
import com.jonsson.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    /**
     * 分页查询分类信息
     * 需要管理员权限
     *
     * @param categoryVO
     * @return
     */
    @PostMapping("/selectPage")
    public Result<Object> selectPage(@RequestBody CategoryVO categoryVO) {
        return Result.success(categoryService.selectPage(categoryVO));
    }

    /**
     * 单个删除分类
     * 需要管理员权限
     *
     * @param categoryVO
     * @return
     */
    @PostMapping("/removeById")
    public Result<Object> removeById(@RequestBody CategoryVO categoryVO) {
        List<Category> categories = categoryService.selectCategorys(categoryVO.getId(), false);
        if (CollectionUtil.isNotEmpty(categories)) return Result.fail("该分类下含有子集,不可以删除");
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        boolean remove = categoryService.lambdaUpdate()
                .in(Category::getCreator, longs)
                .eq(Category::getId, categoryVO.getId())
                .remove();
        if (remove) log.info("用户id{}删除分类{}", SecurityUtil.getCurrentUser().getId(), categoryVO.getName());
        return Result.success();
    }

    /**
     * 修改或者更新分类
     * 需要管理员权限
     *
     * @param category
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result<Object> saveOrUpdate(@RequestBody Category category) {
        List<Long> longs = userService.selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
        Integer count = categoryService.lambdaQuery()
                .eq(Category::getName, category.getName())
                .in(Category::getCreator, longs)
                .ne(category.getId() != null, Category::getId, category.getId())
                .count();
        if (count > 0) return Result.fail("该分类名称已存在");
        category.setCreator(category.getId() == null ? SecurityUtil.getCurrentUser().getId() : null);
        categoryService.saveOrUpdate(category);
        return Result.success();
    }

    @PostMapping("/select")
    public Result<Object> select(@RequestBody Category category) {
        List<Category> categories = categoryService.lambdaQuery()
                .ne(category.getId() != null, Category::getId, category.getId())
                .eq(Category::getCreator, SecurityUtil.getCurrentUser().getId())
                .list();
        return Result.success(categories);
    }

    @PostMapping("/getById")
    public Result<Object> getById(@RequestBody Category category) {
        Category one = categoryService.lambdaQuery().eq(Category::getCreator, SecurityUtil.getCurrentUser().getId())
                .eq(Category::getId, category.getId()).one();
        return Result.success(one);
    }
}