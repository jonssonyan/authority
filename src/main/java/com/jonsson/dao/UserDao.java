package com.jonsson.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jonsson.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {
    List<Long> selectUserIds(Long id);
}
