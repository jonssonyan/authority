package com.jonsson.controller;

import cn.hutool.core.util.StrUtil;
import com.jonsson.entity.User;
import com.jonsson.entity.vo.Result;
import com.jonsson.entity.vo.UserVO;
import com.jonsson.security.constant.SystemConstant;
import com.jonsson.security.util.JwtUtil;
import com.jonsson.security.util.SecurityUtil;
import com.jonsson.service.UserService;
import com.jonsson.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@Slf4j
public class DefaultController {
    @Autowired
    private UserService userService;

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<Object> register(@RequestBody User user, HttpServletRequest request) {
        ValidatorUtil.validateEntity(user);
        try {
            String domain = request.getServerName();
            if (StrUtil.isBlank(domain)) return Result.fail("注册失败");
            User one = userService.lambdaQuery().select(User::getId).eq(User::getDomain, domain).one();
            if (one != null) {
                user.setParentId(one.getId());
                String path = (one.getPath() == null ? "" : one.getPath()) + one.getId() + "-";
                user.setPath(path);
                user.setLevel(StrUtil.count(path, "-"));
            } else {
                return Result.fail("注册失败");
            }
        } catch (Exception e) {
            return Result.fail("注册失败");
        }
        Integer integer = userService.countByUsername(user.getUsername());
        if (integer > 0) return Result.fail("用户名已经存在");
        // 通过shiro默认的加密工具类为注册用户的密码进行加密
        Object salt = ByteSource.Util.bytes(SystemConstant.JWT_SECRET_KEY);
        String md5 = new SimpleHash("MD5", user.getPassword(), salt, 1024).toHex();
        user.setPassword(md5);
        userService.saveOrUpdate(user);
        return Result.success();
    }

    /**
     * 登录
     *
     * @param userVO 登录的用户对象
     * @return
     */
    @PostMapping("/login")
    public Result<Object> login(@RequestBody UserVO userVO) {
        ValidatorUtil.validateEntity(userVO);
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userVO.getUsername(), userVO.getPassword(), true);
            try {
                // shiro验证用户名密码
                SecurityUtils.getSubject().login(usernamePasswordToken);
                // 生成token
                String token = JwtUtil.createToken(userVO.getUsername(), false);
                // 将用户户名和token返回
                HashMap<String, String> map = new HashMap<>();
                map.put("username", userVO.getUsername());
                map.put("Authorization", token);
                map.put("role_id", SecurityUtil.getCurrentUser().getRoleId().toString());
                return Result.success(map);
            } catch (IncorrectCredentialsException e) {
                return Result.fail("登录密码错误");
            } catch (ExcessiveAttemptsException e) {
                return Result.fail("登录失败次数过多");
            } catch (LockedAccountException e) {
                return Result.fail("帐号已被锁定");
            } catch (DisabledAccountException e) {
                return Result.fail("帐号已被禁用");
            } catch (ExpiredCredentialsException e) {
                return Result.fail("帐号已过期");
            } catch (UnknownAccountException e) {
                return Result.fail("帐号不存在");
            } catch (UnauthorizedException e) {
                return Result.fail("您没有得到相应的授权");
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail("登录失败！！！");
            }
        }
        return Result.fail("你已经登录了");
    }

    /**
     * 注册时校验用户名是否存在
     *
     * @param username 用户名
     * @return
     */
    @PostMapping("/countUsername")
    public Result<Object> countUsername(@PathVariable String username) {
        return Result.success(userService.countByUsername(username));
    }
}
