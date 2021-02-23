package com.jonsson.controller;

import com.jonsson.entity.vo.PermissionVO;
import com.jonsson.entity.vo.Result;
import com.jonsson.service.PermissionService;
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
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/getById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"permission:select"})
    public Result<Object> getById(@RequestBody PermissionVO permissionVO) {
        return Result.success(permissionService.getById(permissionVO.getId()));
    }

    @PostMapping("/select")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"permission:select"})
    public Result<Object> select(@RequestBody PermissionVO permissionVO) {
        return Result.success(permissionService.lambdaQuery().list());
    }
}
