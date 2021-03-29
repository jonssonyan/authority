package com.jonsson.controller;

import com.jonsson.entity.User;
import com.jonsson.entity.vo.Result;
import com.jonsson.entity.vo.UserVO;
import com.jonsson.security.util.SecurityUtil;
import com.jonsson.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 分页查询用户
     * 需要管理员权限
     *
     * @param userVO
     * @return
     */
    @PostMapping("/selectPage")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"user:select"})
    public Result<Object> selectPage(@RequestBody UserVO userVO) {
        return Result.success(userService.selectPage(userVO));
    }

    /**
     * 添加或或者修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"user:update", "user:add"})
    public Result<Object> saveOrUpdate(@RequestBody User user) {
        if ("admin".equals(user.getUsername())) {
            return Result.fail("管理员不可以被禁用");
        }
        Integer count = userService.lambdaQuery().eq(User::getUsername, user.getUsername())
                .ne(user.getId() != null, User::getId, user.getId())
                .count();
        if (count > 0) return Result.fail("用户名已存在");
        userService.saveOrUpdate(user);
        return Result.success();
    }

    /**
     * 通过id删除用户
     * 需要管理员权限
     *
     * @param userVO
     * @return
     */
    @PostMapping("/removeById")
    @RequiresRoles({"admin"})
    @RequiresPermissions({"user:delete"})
    public Result<Object> removeById(@RequestBody UserVO userVO) {
        userService.removeById(userVO.getId());
        return Result.success();
    }

    /**
     * 通过id查询用户
     *
     * @param userVO
     * @return
     */
    @PostMapping("/getById")
    @RequiresPermissions({"user:select"})
    public Result<Object> selectById(@RequestBody UserVO userVO) {
        return Result.success(userService.getById(userVO.getId()));
    }

    /**
     * 注销登录
     * 前提是在登录状态
     *
     * @return
     */
    @PostMapping("/loginOut")
    public Result<Object> loginOut() {
        User currentUser = SecurityUtil.getCurrentUser();
        if (currentUser == null) {
            return Result.fail("您暂未登录");
        }
        SecurityUtils.getSubject().logout();
        return Result.success("注销成功");
    }
}