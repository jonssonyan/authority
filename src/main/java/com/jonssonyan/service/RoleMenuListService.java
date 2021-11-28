package com.jonssonyan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jonssonyan.entity.RoleMenuList;
import com.jonssonyan.entity.vo.RoleMenuListVo;

public interface RoleMenuListService extends IService<RoleMenuList> {
    IPage<RoleMenuList> selectPage(RoleMenuListVo roleMenuListVO);
}
