package com.jonssonyan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.MenuListDao;
import com.jonssonyan.dao.RoleMenuListDao;
import com.jonssonyan.entity.MenuList;
import com.jonssonyan.entity.Role;
import com.jonssonyan.entity.RoleMenuList;
import com.jonssonyan.entity.dto.MenuListDto;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.MenuListService;
import com.jonssonyan.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuListServiceImpl extends ServiceImpl<MenuListDao, MenuList> implements MenuListService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuListDao roleMenuListDao;

    @Override
    public IPage<MenuList> selectPage(MenuListDto menuListDto) {
        return lambdaQuery()
                .like(StrUtil.isNotBlank(menuListDto.getName()), MenuList::getName, menuListDto.getName())
                .eq(null != menuListDto.getState(), MenuList::getState, menuListDto.getState())
                .between(null != menuListDto.getStartTime() && null != menuListDto.getEndTime(), MenuList::getCreateTime, menuListDto.getStartTime(), menuListDto.getEndTime())
                .orderByAsc(MenuList::getParentId).last(",create_time desc")
                .page(new Page<>(menuListDto.getPageNum(), menuListDto.getPageSize()));
    }

    @Override
    public List<MenuList> selectList(Long roleId) {
        List<Role> roles = roleService.selectRoles(roleId == null ? SecurityUtil.getCurrentUser().getRoleId() : roleId, true);
        List<Long> ids = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<RoleMenuList> roleMenuLists = roleMenuListDao.selectList(new QueryWrapper<RoleMenuList>().lambda()
                .in(RoleMenuList::getRoleId, ids));
        List<Long> collect = roleMenuLists.stream().map(RoleMenuList::getMenuListId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) return new ArrayList<>();
        List<MenuList> menuLists = lambdaQuery()
                .or(menuListLambdaQueryWrapper -> menuListLambdaQueryWrapper.isNull(MenuList::getParentId).or().eq(MenuList::getParentId, ""))
                .in(MenuList::getId, collect)
                .list();
        for (MenuList menuList : menuLists) {
            List<MenuList> list = selectByParentId(menuList.getId(), collect);
            menuList.setMenuLists(list);
        }
        return menuLists;
    }

    @Override
    public List<MenuList> selectByParentId(Long parentId, List<Long> ids) {
        List<MenuList> list = lambdaQuery().eq(MenuList::getParentId, parentId).in(MenuList::getId, ids).list();
        if (CollectionUtil.isEmpty(list)) return null;
        for (MenuList menuList : list) {
            List<MenuList> menuLists = selectByParentId(menuList.getId(), ids);
            menuList.setMenuLists(menuLists);
        }
        return list;
    }
}