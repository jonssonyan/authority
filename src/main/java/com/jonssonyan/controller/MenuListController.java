package com.jonssonyan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.jonssonyan.entity.MenuList;
import com.jonssonyan.entity.dto.MenuListDto;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.MenuListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "菜单")
@RequestMapping("/api/menuList")
public class MenuListController {
    @Autowired
    private MenuListService menuListService;

    @ApiOperation(value = "查询菜单列表")
    @GetMapping("/selectList")
    @RequiresPermissions({"menuList:select"})
    public Result selectList(MenuList menuList) {
        return Result.success(menuListService.selectList(SecurityUtil.getCurrentUser().getRoleId()));
    }

    @ApiOperation(value = "分页查询菜单")
    @GetMapping("/selectPage")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"menuList:select"})
    public Result selectPage(MenuListDto menuListDto) {
        return Result.success(menuListService.selectPage(menuListDto));
    }

    @ApiOperation(value = "根据id删除菜单")
    @PostMapping("/removeById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"menuList:delete"})
    public Result removeById(@RequestBody MenuListDto menuListDto) {
        menuListService.removeById(menuListDto.getId());
        return Result.success();
    }

    @ApiOperation(value = "添加或者更新菜单")
    @PostMapping("/saveOrUpdate")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"menuList:add", "menuList:update"})
    public Result saveOrUpdate(@RequestBody MenuList menuList) {
        List<MenuList> menuLists = menuListService.lambdaQuery()
                .eq(MenuList::getName, menuList.getName())
                .or()
                .eq(MenuList::getRouter, menuList.getRouter())
                .ne(menuList.getId() != null, MenuList::getId, menuList.getId())
                .list();
        if (CollectionUtil.isNotEmpty(menuLists)) {
            return Result.fail("菜单名称或者路由已存在");
        }
        menuListService.saveOrUpdate(menuList);
        return Result.success();
    }
}
