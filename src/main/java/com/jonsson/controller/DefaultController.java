package com.jonsson.controller;

import cn.hutool.core.util.StrUtil;
import com.jonsson.entity.User;
import com.jonsson.entity.vo.Result;
import com.jonsson.entity.vo.UserVO;
import com.jonsson.security.constant.SystemConstants;
import com.jonsson.security.utils.JwtUtils;
import com.jonsson.security.utils.SecurityUtil;
import com.jonsson.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class DefaultController {
    @Autowired
    private UserService userService;

    /**
     * 注册用户
     *
     * @param userVO
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public Result<Object> insert(@Validated @RequestBody UserVO userVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return Result.fail(0, "参数错误", allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        }
        Integer integer = userService.countByUsername(userVO.getUsername());
        if (integer > 0) {
            return Result.fail("用户名已经存在");
        }
        // 通过shiro默认的加密工具类为注册用户的密码进行加密
        Object salt = ByteSource.Util.bytes(SystemConstants.JWT_SECRET_KEY);
        String md5 = new SimpleHash("MD5", userVO.getPassword(), salt, 1024).toHex();
        User user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(md5);
        user.setEmail(userVO.getEmail());
        if (StrUtil.isNotBlank(userVO.getQq())) {
            user.setQq(userVO.getQq());
        }
        if (StrUtil.isNotBlank(userVO.getPhone())) {
            user.setPhone(userVO.getPhone());
        }
        userService.saveOrUpdate(user);
        return Result.success();
    }

    /**
     * 登录
     *
     * @param user 登录的用户对象
     * @return
     */
    @PostMapping("/login")
    public Result<Object> findByUsernameAndPassword(@RequestBody User user) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword(), true);
            try {
                // shiro验证用户名密码
                SecurityUtils.getSubject().login(usernamePasswordToken);
                // 生成token
                String token = JwtUtils.createToken(user.getUsername(), false);
                // 将用户户名和token返回
                HashMap<String, String> map = new HashMap<>();
                map.put("username", user.getUsername());
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
                return Result.fail("出错了！！！");
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
