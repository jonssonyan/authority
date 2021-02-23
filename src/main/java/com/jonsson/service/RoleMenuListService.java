package com.jonsson.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.MenuListDao;
import com.jonsson.dao.RoleDao;
import com.jonsson.dao.RoleMenuListDao;
import com.jonsson.entity.RoleMenuList;
import com.jonsson.entity.vo.RoleMenuListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleMenuListService extends ServiceImpl<RoleMenuListDao, RoleMenuList> {
    @Autowired
    private MenuListDao menuListDao;
    @Autowired
    private RoleDao roleDao;

    public IPage<RoleMenuList> selectPage(RoleMenuListVO roleMenuListVO) {
        IPage<RoleMenuList> roleMenuListIPage = lambdaQuery().page(new Page<>(roleMenuListVO.getPageNum(), roleMenuListVO.getPageSize()));
        roleMenuListIPage.getRecords().forEach(rm -> {
            rm.setMenuList(menuListDao.selectById(rm.getMenuListId()));
            rm.setRole(roleDao.selectById(rm.getRoleId()));
        });
        return roleMenuListIPage;
    }
}
