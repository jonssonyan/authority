package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.RolePermission;
import com.jonssonyan.entity.dto.RolePermissionDto;

public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 分页查询
     *
     * @param rolePermissionVO
     * @return
     */
    IPage<RolePermission> selectPage(RolePermissionDto rolePermissionVO);
}
