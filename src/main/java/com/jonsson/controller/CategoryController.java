package com.jonsson.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.jonsson.entity.Category;
import com.jonsson.entity.vo.CategoryVO;
import com.jonsson.entity.vo.Result;
import com.jonsson.security.util.SecurityUtil;
import com.jonsson.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    /**
     * 分页查询分类信息
     *
     * @param categoryVO
     * @return
     */
    @PostMapping("/selectPage")
    @RequiresPermissions({"category:select"})
    public Result<Object> selectPage(@RequestBody CategoryVO categoryVO) {
        return Result.success(categoryService.selectPage(categoryVO));
    }

    /**
     * 单个删除分类
     *
     * @param categoryVO
     * @return
     */
    @PostMapping("/removeById")
    @RequiresPermissions({"category:delete"})
    public Result<Object> removeById(@RequestBody CategoryVO categoryVO) {
        List<Category> categories = categoryService.selectList(categoryVO.getId(), false);
        if (CollectionUtil.isNotEmpty(categories)) return Result.fail("该分类下含有子集,不可以删除");
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        boolean remove = categoryService.lambdaUpdate()
                .in(Category::getCreator, longs)
                .eq(Category::getId, categoryVO.getId())
                .remove();
        if (remove) log.info("用户id{}删除分类{}", SecurityUtil.getCurrentUser().getId(), categoryVO.getName());
        return Result.success();
    }

    /**
     * 修改或者更新分类
     *
     * @param category
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"category:update", "category:add"})
    public Result<Object> saveOrUpdate(@RequestBody Category category) {
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
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

    /**
     * 查询某人创建的分类，但是排除当前选中的分类，用户修改分类使用
     *
     * @param category
     * @return
     */
    @PostMapping("/select")
    @RequiresPermissions({"category:select"})
    public Result<Object> select(@RequestBody Category category) {
        List<Category> categories = categoryService.lambdaQuery()
                .ne(category.getId() != null, Category::getId, category.getId())
                .eq(Category::getCreator, SecurityUtil.getCurrentUser().getId())
                .list();
        return Result.success(categories);
    }

    /**
     * 查询分类，嵌套数据结构
     *
     * @param category
     * @return
     */
    @PostMapping("/selectChilds")
    @RequiresPermissions({"category:select"})
    public Result<Object> selectChilds(@RequestBody Category category) {
        return Result.success(categoryService.selectChilds(SecurityUtil.getCurrentUser().getId()));
    }

    /**
     * 通过id查询单个分类
     *
     * @param category
     * @return
     */
    @PostMapping("/getById")
    @RequiresPermissions({"category:select"})
    public Result<Object> getById(@RequestBody Category category) {
        Category one = categoryService.lambdaQuery().eq(Category::getCreator, SecurityUtil.getCurrentUser().getId())
                .eq(Category::getId, category.getId()).one();
        return Result.success(one);
    }
}