package com.jonsson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.RolePermissionDao;
import com.jonsson.entity.RolePermission;
import com.jonsson.entity.vo.RolePermissionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RolePermissionService extends ServiceImpl<RolePermissionDao, RolePermission> {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    public IPage<RolePermission> selectPage(RolePermissionVO rolePermissionVO) {
        IPage<RolePermission> rolePermissionIPage = lambdaQuery()
                .orderByAsc(RolePermission::getRoleId)
                .page(new Page<>(rolePermissionVO.getPageNum(), rolePermissionVO.getPageSize()));
        rolePermissionIPage.getRecords().forEach(rolePermission -> {
            rolePermission.setRole(roleService.getById(rolePermission.getRoleId()));
            rolePermission.setPermission(permissionService.getById(rolePermission.getPermissionId()));
        });
        return rolePermissionIPage;
    }
}
