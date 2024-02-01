package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.MenuList;
import com.jonssonyan.entity.dto.MenuListDto;

import java.util.List;

public interface MenuListService extends IService<MenuList> {
    /**
     * 分页查询菜单
     *
     * @param menuListVO
     * @return
     */
    IPage<MenuList> selectPage(MenuListDto menuListVO);

    /**
     * 根据角色返回菜单,树状结构
     *
     * @param roleId
     * @return
     */
    List<MenuList> selectList(Long roleId);

    /**
     * 查询下级菜单,不包括自己
     *
     * @param parentId
     * @return
     */
    List<MenuList> selectByParentId(Long parentId, List<Long> ids);
}
