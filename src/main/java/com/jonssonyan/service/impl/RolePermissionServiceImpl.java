package com.jonssonyan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.RolePermissionDao;
import com.jonssonyan.entity.RolePermission;
import com.jonssonyan.entity.dto.RolePermissionDto;
import com.jonssonyan.service.PermissionService;
import com.jonssonyan.service.RolePermissionService;
import com.jonssonyan.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public IPage<RolePermission> selectPage(RolePermissionDto rolePermissionDto) {
        IPage<RolePermission> rolePermissionIPage = lambdaQuery()
                .orderByAsc(RolePermission::getRoleId)
                .page(new Page<>(rolePermissionDto.getPageNum(), rolePermissionDto.getPageSize()));
        rolePermissionIPage.getRecords().forEach(rolePermission -> {
            rolePermission.setRole(roleService.getById(rolePermission.getRoleId()));
            rolePermission.setPermission(permissionService.getById(rolePermission.getPermissionId()));
        });
        return rolePermissionIPage;
    }
}
