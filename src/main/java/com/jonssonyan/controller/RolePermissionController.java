package com.jonssonyan.controller;

import com.jonssonyan.entity.Permission;
import com.jonssonyan.entity.Role;
import com.jonssonyan.entity.RolePermission;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.entity.dto.RolePermissionDto;
import com.jonssonyan.service.PermissionService;
import com.jonssonyan.service.RolePermissionService;
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
@Api(tags = "角色和权限关系")
@RequestMapping("/api/rolePermission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "添加或者更新角色和权限关系")
    @PostMapping("/saveOrUpdate")
    @RequiresPermissions({"rolePermission:add"})
    @RequiresRoles({"admin"})
    public Result saveOrUpdate(@RequestBody RolePermission rolePermission) {
        Integer count = rolePermissionService.lambdaQuery()
                .eq(RolePermission::getRoleId, rolePermission.getRoleId())
                .eq(RolePermission::getPermissionId, rolePermission.getPermissionId())
                .ne(rolePermission.getId() != null, RolePermission::getId, rolePermission.getId())
                .count();
        if (count > 0) return Result.fail("授权已存在");
        rolePermissionService.saveOrUpdate(rolePermission);
        return Result.success();
    }

    @ApiOperation(value = "根据id删除角色和权限关系")
    @PostMapping("/removeById")
    @RequiresPermissions({"rolePermission:delete"})
    @RequiresRoles({"admin"})
    public Result removeById(@RequestBody RolePermissionDto rolePermissionVO) {
        rolePermissionService.removeById(rolePermissionVO.getId());
        return Result.success();
    }

    @ApiOperation(value = "分页查询角色和权限关系")
    @GetMapping("/selectPage")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"rolePermission:select"})
    public Result selectPage(RolePermissionDto rolePermissionVO) {
        return Result.success(rolePermissionService.selectPage(rolePermissionVO));
    }

    @ApiOperation(value = "根据id查询角色和权限关系")
    @GetMapping("/getById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"rolePermission:select"})
    public Result getById(RolePermissionDto rolePermissionVO) {
        RolePermission rolePermission = rolePermissionService.getById(rolePermissionVO.getId());
        if (rolePermission != null) {
            Role role = roleService.getById(rolePermission.getRoleId());
            Permission permission = permissionService.getById(rolePermission.getPermissionId());
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
        }
        return Result.success(rolePermission);
    }
}
