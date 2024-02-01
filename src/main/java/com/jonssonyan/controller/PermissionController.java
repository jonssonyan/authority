package com.jonssonyan.controller;

import com.jonssonyan.entity.dto.PermissionDto;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "权限")
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "根据id查询权限")
    @GetMapping("/getById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"permission:select"})
    public Result getById(PermissionDto permissionDto) {
        return Result.success(permissionService.getById(permissionDto.getId()));
    }

    @ApiOperation(value = "查询权限列表")
    @GetMapping("/select")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"permission:select"})
    public Result select(PermissionDto permissionDto) {
        return Result.success(permissionService.lambdaQuery().list());
    }
}
