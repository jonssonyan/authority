package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.Role;
import com.jonssonyan.entity.vo.RoleVo;

import java.util.List;
import java.util.Set;

public interface RoleService extends IService<Role> {
    Set<Role> selectRolesByParentId(Set<Role> roles, Long id);

    List<Role> selectRoles(Long id, Boolean contain);

    IPage<Role> selectPage(RoleVo roleVO);
}
