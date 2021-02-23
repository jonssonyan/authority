package com.jonsson.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jonsson.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PermissionDao extends BaseMapper<Permission> {
}
