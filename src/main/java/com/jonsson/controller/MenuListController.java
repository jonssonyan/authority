package com.jonsson.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.jonsson.entity.MenuList;
import com.jonsson.entity.vo.MenuListVO;
import com.jonsson.entity.vo.Result;
import com.jonsson.security.util.SecurityUtil;
import com.jonsson.service.MenuListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/menuList")
public class MenuListController {
    @Autowired
    private MenuListService menuListService;

    @PostMapping("/selectList")
    @RequiresPermissions({"menuList:select"})
    public Result<Object> selectList(@RequestBody MenuList menuList) {
        return Result.success(menuListService.selectList(SecurityUtil.getCurrentUser().getRoleId()));
    }

    @PostMapping("/selectPage")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"menuList:select"})
    public Result<Object> selectPage(@RequestBody MenuListVO MenuListVO) {
        return Result.success(menuListService.selectPage(MenuListVO));
    }

    @PostMapping("/removeById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"menuList:delete"})
    public Result<Object> removeById(@RequestBody MenuListVO MenuListVO) {
        menuListService.removeById(MenuListVO.getId());
        return Result.success();
    }

    @PostMapping("/saveOrUpdate")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"menuList:add", "menuList:update"})
    public Result<Object> saveOrUpdate(@RequestBody MenuList menuList) {
        List<MenuList> menuLists = menuListService.lambdaQuery()
                .eq(MenuList::getName, menuList.getName())
                .or()
                .eq(MenuList::getPath, menuList.getPath())
                .ne(menuList.getId() != null, MenuList::getId, menuList.getId())
                .list();
        if (CollectionUtil.isNotEmpty(menuLists)) {
            return Result.fail("菜单名称或者路由已存在");
        }
        menuListService.saveOrUpdate(menuList);
        return Result.success();
    }
}
