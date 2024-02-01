package com.jonssonyan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.jonssonyan.entity.Category;
import com.jonssonyan.entity.dto.CategoryDto;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.CategoryService;
import com.jonssonyan.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "分类")
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询分类")
    @GetMapping("/selectPage")
    @RequiresPermissions({"category:select"})
    public Result selectPage(CategoryDto categoryDto) {
        return Result.success(categoryService.selectPage(categoryDto));
    }

    @ApiOperation(value = "单个删除分类")
    @PostMapping("/removeById")
    @RequiresPermissions({"category:delete"})
    public Result removeById(@RequestBody CategoryDto categoryDto) {
        List<Category> categories = categoryService.selectList(categoryDto.getId(), false);
        if (CollectionUtil.isNotEmpty(categories)) return Result.fail("该分类下含有子集,不可以删除");
        List<Long> longs = userService.selectChild(SecurityUtil.getCurrentUser().getId(), true);
        boolean remove = categoryService.lambdaUpdate()
                .in(Category::getCreator, longs)
                .eq(Category::getId, categoryDto.getId())
                .remove();
        if (remove) log.info("用户id{}删除分类{}", SecurityUtil.getCurrentUser().getId(), categoryDto.getName());
        return Result.success();
    }

    @ApiOperation(value = "修改或者更新分类")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"category:update", "category:add"})
    public Result saveOrUpdate(@RequestBody Category category) {
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

    @ApiOperation(value = "查询某人创建的分类，但是排除当前选中的分类，用户修改分类使用")
    @GetMapping("/select")
    @RequiresPermissions({"category:select"})
    public Result select(Category category) {
        List<Category> categories = categoryService.lambdaQuery()
                .ne(category.getId() != null, Category::getId, category.getId())
                .eq(Category::getCreator, SecurityUtil.getCurrentUser().getId())
                .list();
        return Result.success(categories);
    }

    @ApiOperation(value = "查询分类，嵌套数据结构")
    @GetMapping("/selectChilds")
    @RequiresPermissions({"category:select"})
    public Result selectChilds(Category category) {
        return Result.success(categoryService.selectChilds(SecurityUtil.getCurrentUser().getId()));
    }

    @ApiOperation(value = "通过id查询分类")
    @GetMapping("/getById")
    @RequiresPermissions({"category:select"})
    public Result getById(Category category) {
        Category one = categoryService.lambdaQuery().eq(Category::getCreator, SecurityUtil.getCurrentUser().getId())
                .eq(Category::getId, category.getId()).one();
        return Result.success(one);
    }
}