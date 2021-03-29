package com.jonsson.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.MenuListDao;
import com.jonsson.dao.RoleMenuListDao;
import com.jonsson.entity.MenuList;
import com.jonsson.entity.Role;
import com.jonsson.entity.RoleMenuList;
import com.jonsson.entity.vo.MenuListVO;
import com.jonsson.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuListService extends ServiceImpl<MenuListDao, MenuList> {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuListDao roleMenuListDao;

    public IPage<MenuList> selectPage(MenuListVO menuListVO) {
        return lambdaQuery()
                .like(StrUtil.isNotBlank(menuListVO.getName()), MenuList::getName, menuListVO.getName())
                .eq(null != menuListVO.getState(), MenuList::getState, menuListVO.getState())
                .between(null != menuListVO.getStartTime() && null != menuListVO.getEndTime(), MenuList::getCreateTime, menuListVO.getStartTime(), menuListVO.getEndTime())
                .orderByAsc(MenuList::getParentId).last(",create_time desc")
                .page(new Page<>(menuListVO.getPageNum(), menuListVO.getPageSize()));
    }

    /**
     * 根据角色返回菜单,树状结构
     *
     * @param roleId
     * @return
     */
    public List<MenuList> selectList(Long roleId) {
        List<Role> roles = roleService.selectRoles(roleId == null ? SecurityUtil.getCurrentUser().getRoleId() : roleId, true);
        List<Long> ids = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<RoleMenuList> roleMenuLists = roleMenuListDao.selectList(new QueryWrapper<RoleMenuList>().lambda()
                .in(RoleMenuList::getRoleId, ids));
        List<Long> collect = roleMenuLists.stream().map(RoleMenuList::getMenuListId).collect(Collectors.toList());
        List<MenuList> menuLists = lambdaQuery()
                .isNull(MenuList::getParentId)
                .in(CollectionUtil.isNotEmpty(collect), MenuList::getId, collect)
                .list();
        for (MenuList menuList : menuLists) {
            List<MenuList> list = selectByParentId(menuList.getId());
            menuList.setMenuLists(list);
        }
        return menuLists;
    }

    /**
     * 查询下级菜单,不包括自己
     *
     * @param parentId
     * @return
     */
    public List<MenuList> selectByParentId(Long parentId) {
        List<MenuList> list = lambdaQuery().eq(MenuList::getParentId, parentId).list();
        if (CollectionUtil.isEmpty(list)) return null;
        for (MenuList menuList : list) {
            List<MenuList> menuLists = selectByParentId(menuList.getId());
            menuList.setMenuLists(menuLists);
        }
        return list;
    }
}