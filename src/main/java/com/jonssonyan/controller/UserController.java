package com.jonssonyan.controller;

import com.jonssonyan.entity.User;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.entity.vo.UserVo;
import com.jonssonyan.security.constant.SystemConstant;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/user")
@Api(tags = "用户")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询用户")
    @GetMapping("/selectPage")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"user:select"})
    public Result selectPage(UserVo userVO) {
        return Result.success(userService.selectPage(userVO));
    }

    @ApiOperation(value = "添加或或者修改用户")
    @PostMapping("/saveOrUpdate")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"user:update", "user:add"})
    public Result saveOrUpdate(@RequestBody User user) {
        if ("admin".equals(user.getUsername())) {
            return Result.fail("管理员不可以被禁用");
        }
        Integer count = userService.lambdaQuery().eq(User::getUsername, user.getUsername())
                .ne(user.getId() != null, User::getId, user.getId())
                .count();
        if (count > 0) return Result.fail("用户名已存在");
        // 通过shiro默认的加密工具类为注册用户的密码进行加密
        Object salt = ByteSource.Util.bytes(SystemConstant.JWT_SECRET_KEY);
        String md5 = new SimpleHash("MD5", user.getPassword(), salt, 1024).toHex();
        user.setPassword(md5);
        userService.saveOrUpdate(user);
        return Result.success();
    }

    @ApiOperation(value = "通过id删除用户")
    @PostMapping("/removeById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"user:delete"})
    public Result removeById(@RequestBody UserVo userVO) {
        userService.removeById(userVO.getId());
        return Result.success();
    }

    @ApiOperation(value = "通过id查询用户")
    @GetMapping("/getById")
    @RequiresPermissions({"user:select"})
    public Result selectById(UserVo userVO) {
        return Result.success(userService.getById(userVO.getId()));
    }

    @ApiOperation(value = "注销登录，前提是在登录状态")
    @PostMapping("/loginOut")
    public Result loginOut() {
        User currentUser = SecurityUtil.getCurrentUser();
        if (currentUser == null) {
            return Result.fail("您暂未登录");
        }
        SecurityUtils.getSubject().logout();
        return Result.success("注销成功");
    }
}