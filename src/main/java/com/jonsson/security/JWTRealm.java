package com.jonsson.security;

import cn.hutool.core.util.StrUtil;
import com.jonsson.entity.Permission;
import com.jonsson.entity.Role;
import com.jonsson.entity.RolePermission;
import com.jonsson.entity.User;
import com.jonsson.security.entity.JwtToken;
import com.jonsson.security.utils.JwtUtils;
import com.jonsson.security.utils.SecurityUtil;
import com.jonsson.service.PermissionService;
import com.jonsson.service.RolePermissionService;
import com.jonsson.service.RoleService;
import com.jonsson.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class JWTRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 执行授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置角色
        List<Role> roles = roleService.selectRoles(SecurityUtil.getCurrentUser().getRoleId(), true);
        authorizationInfo.addRoles(roles.stream().map(Role::getName).collect(Collectors.toList()));
        List<RolePermission> rolePermissions = rolePermissionService.lambdaQuery()
                .eq(RolePermission::getState, 1)
                .eq(RolePermission::getRoleId, SecurityUtil.getCurrentUser().getRoleId()).list();
        Set<Permission> set = new HashSet<>();
        for (RolePermission rolePermission : rolePermissions) {
            List<Permission> permissions = permissionService.lambdaQuery().eq(Permission::getId, rolePermission.getPermissionId()).list();
            set.addAll(permissions);
        }
        // 设置权限
        authorizationInfo.addStringPermissions(set.stream().map(Permission::getName).collect(Collectors.toList()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtils.getUsernameByToken(token);
        if (StrUtil.isBlank(username)) {
            throw new AuthenticationException("token认证失败!");
        }
        User user = userService.selectByUsername(username);
        // 判断用户
        if (user == null) {
            throw new AuthenticationException("用户不存在!");
        }
        if (user.getState() == 0) {
            throw new AuthenticationException("账号已被禁用!");
        }
        return new SimpleAuthenticationInfo(user, token, getName());
    }
}
