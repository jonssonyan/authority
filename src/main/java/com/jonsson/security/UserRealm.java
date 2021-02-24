package com.jonsson.security;

import cn.hutool.core.collection.CollectionUtil;
import com.jonsson.entity.*;
import com.jonsson.security.constant.SystemConstants;
import com.jonsson.security.utils.SecurityUtil;
import com.jonsson.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuListService menuListService;
    @Autowired
    private RoleMenuListService roleMenuListService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 执行授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置角色
        List<Role> roles = roleService.selectRoles(SecurityUtil.getCurrentUser().getRoleId(), true);
        authorizationInfo.addRoles(roles.stream().map(Role::getName).collect(Collectors.toList()));
        List<RolePermission> rolePermissions = rolePermissionService.lambdaQuery().eq(RolePermission::getRoleId, SecurityUtil.getCurrentUser().getRoleId()).list();
        if (CollectionUtil.isNotEmpty(rolePermissions)) {
            Set<Permission> set = new HashSet<>();
            for (RolePermission rolePermission : rolePermissions) {
                List<Permission> permissions = permissionService.lambdaQuery().eq(Permission::getId, rolePermission.getPermissionId()).list();
                set.addAll(permissions);
            }
            // 设置权限
            authorizationInfo.addStringPermissions(set.stream().map(Permission::getName).collect(Collectors.toList()));
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 执行认证
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user = userService.selectByUsername(usernamePasswordToken.getUsername());
        // 判断用户
        if (user == null) {
            throw new UnknownAccountException("用户不存在!");
        }
        if (user.getState() == 0) {
            throw new DisabledAccountException("账号已被禁用!");
        }

        // 认证成功之后设置角色关联的菜单
        List<RoleMenuList> roleMenuLists = roleMenuListService.lambdaQuery().in(RoleMenuList::getRoleId, user.getRoleId()).list();
        if (CollectionUtil.isNotEmpty(roleMenuLists)) {
            List<Long> collect = roleMenuLists.stream().map(RoleMenuList::getMenuListId).collect(Collectors.toList());
            List<MenuList> menuLists = menuListService.lambdaQuery().in(CollectionUtil.isNotEmpty(collect), MenuList::getId, collect).list();
            // 认证成功之后设置角色关联的菜单
            user.setMenuLists(CollectionUtil.isNotEmpty(collect) ? menuLists : null);
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(SystemConstants.JWT_SECRET_KEY), getName());
    }
}