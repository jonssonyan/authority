package com.jonssonyan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jonssonyan.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RolePermissionDao extends BaseMapper<RolePermission> {
}
