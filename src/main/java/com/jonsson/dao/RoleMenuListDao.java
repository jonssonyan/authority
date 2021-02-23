package com.jonsson.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jonsson.entity.RoleMenuList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMenuListDao extends BaseMapper<RoleMenuList> {
}
