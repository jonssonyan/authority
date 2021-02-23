package com.jonsson.controller;

import com.jonsson.entity.Role;
import com.jonsson.entity.vo.Result;
import com.jonsson.entity.vo.RoleVO;
import com.jonsson.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"role:add", "role:update"})
    @RequiresRoles({"admin"})
    public Result<Object> saveOrUpdate(@RequestBody Role role) {
        Integer count = roleService.lambdaQuery().eq(Role::getName, role.getName())
                .ne(role.getId() != null, Role::getId, role.getId())
                .count();
        if (count > 0) return Result.fail("已存在该角色名称");
        roleService.saveOrUpdate(role);
        return Result.success();
    }

    @PostMapping("/removeById")
    @RequiresPermissions({"role:delete"})
    @RequiresRoles({"admin"})
    public Result<Object> removeById(@RequestBody RoleVO roleVO) {
        roleService.removeById(roleVO.getId());
        return Result.success();
    }

    @PostMapping("/getById")
    @RequiresPermissions({"role:select"})
    @RequiresRoles({"admin"})
    public Result<Object> getById(@RequestBody Role role) {
        return Result.success(roleService.getById(role.getId()));
    }

    @PostMapping("/selectPage")
    @RequiresPermissions({"role:select"})
    @RequiresRoles({"admin"})
    public Result<Object> selectPage(@RequestBody RoleVO roleVO) {
        return Result.success(roleService.selectPage(roleVO));
    }

    @PostMapping("/select")
    @RequiresPermissions({"role:select"})
    @RequiresRoles({"admin"})
    public Result<Object> select(@RequestBody RoleVO roleVO) {
        return Result.success(roleService.lambdaQuery().list());
    }
}