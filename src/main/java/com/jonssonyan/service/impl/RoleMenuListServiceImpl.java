package com.jonssonyan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.MenuListDao;
import com.jonssonyan.dao.RoleDao;
import com.jonssonyan.dao.RoleMenuListDao;
import com.jonssonyan.entity.RoleMenuList;
import com.jonssonyan.entity.dto.RoleMenuListDto;
import com.jonssonyan.service.RoleMenuListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleMenuListServiceImpl extends ServiceImpl<RoleMenuListDao, RoleMenuList> implements RoleMenuListService {
    @Autowired
    private MenuListDao menuListDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public IPage<RoleMenuList> selectPage(RoleMenuListDto roleMenuListDto) {
        IPage<RoleMenuList> roleMenuListIPage = lambdaQuery().page(new Page<>(roleMenuListDto.getPageNum(), roleMenuListDto.getPageSize()));
        roleMenuListIPage.getRecords().forEach(rm -> {
            rm.setMenuList(menuListDao.selectById(rm.getMenuListId()));
            rm.setRole(roleDao.selectById(rm.getRoleId()));
        });
        return roleMenuListIPage;
    }
}
