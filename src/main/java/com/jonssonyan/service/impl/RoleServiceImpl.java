package com.jonssonyan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.RoleDao;
import com.jonssonyan.entity.Role;
import com.jonssonyan.entity.dto.RoleDto;
import com.jonssonyan.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<Role> selectRolesByParentId(Set<Role> roles, Long id) {
        List<Role> list = lambdaQuery().eq(Role::getParentId, id).eq(Role::getState, 1).list();
        roles.addAll(list);
        for (Role role : list) {
            Set<Role> roles1 = selectRolesByParentId(roles, role.getId());
            roles.addAll(roles1);
        }
        return roles;
    }

    @Override
    public List<Role> selectRoles(Long id, Boolean contain) {
        HashSet<Role> roles = new HashSet<>();
        if (contain) {
            roles.add(roleDao.selectById(id));
        }
        selectRolesByParentId(roles, id);
        return new ArrayList<>(roles);
    }

    @Override
    public IPage<Role> selectPage(RoleDto roleDto) {
        return lambdaQuery()
                .like(StrUtil.isNotBlank(roleDto.getName()), Role::getName, roleDto.getName())
                .page(new Page<>(roleDto.getPageNum(), roleDto.getPageSize()));
    }
}