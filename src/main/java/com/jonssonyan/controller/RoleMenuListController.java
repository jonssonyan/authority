package com.jonssonyan.controller;

import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.entity.vo.RoleMenuListVo;
import com.jonssonyan.service.RoleMenuListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "角色和菜单关系")
@RequestMapping("/api/roleMenuList")
public class RoleMenuListController {
    @Autowired
    private RoleMenuListService roleMenuListService;

    @ApiOperation(value = "分页查询角色和菜单关系")
    @GetMapping("/selectPage")
    @RequiresRoles({"admin"})
    public Result selectPage(RoleMenuListVo roleMenuListVO) {
        return Result.success(roleMenuListService.selectPage(roleMenuListVO));
    }
}