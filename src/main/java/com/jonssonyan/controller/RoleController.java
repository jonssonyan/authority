package com.jonssonyan.controller;

import com.jonssonyan.entity.Role;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.entity.dto.RoleDto;
import com.jonssonyan.service.RoleService;
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

@RestController
@Slf4j
@Api(tags = "角色")
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "添加或者更新角色")
    @PostMapping("/saveOrUpdate")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"role:add", "role:update"})
    public Result saveOrUpdate(@RequestBody Role role) {
        Integer count = roleService.lambdaQuery().eq(Role::getName, role.getName())
                .ne(role.getId() != null, Role::getId, role.getId())
                .count();
        if (count > 0) return Result.fail("已存在该角色名称");
        roleService.saveOrUpdate(role);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除角色")
    @PostMapping("/removeById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"role:delete"})
    public Result removeById(@RequestBody RoleDto roleVO) {
        roleService.removeById(roleVO.getId());
        return Result.success();
    }

    @ApiOperation(value = "根据id查询角色")
    @GetMapping("/getById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"role:select"})
    public Result getById(Role role) {
        return Result.success(roleService.getById(role.getId()));
    }

    @ApiOperation(value = "分页查询角色")
    @GetMapping("/selectPage")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"role:select"})
    public Result selectPage(RoleDto roleVO) {
        return Result.success(roleService.selectPage(roleVO));
    }

    @ApiOperation(value = "查询角色列表")
    @GetMapping("/select")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"role:select"})
    public Result select(RoleDto roleVO) {
        return Result.success(roleService.lambdaQuery().list());
    }
}