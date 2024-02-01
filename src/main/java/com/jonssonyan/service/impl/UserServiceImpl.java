package com.jonssonyan.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonssonyan.dao.UserDao;
import com.jonssonyan.entity.User;
import com.jonssonyan.entity.dto.UserDto;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;

    public IPage<User> selectPage(UserDto userDto) {
        List<Long> longs = selectChild(SecurityUtil.getCurrentUser().getId(), true);
        return lambdaQuery()
                .in(User::getId, longs)
                .like(StrUtil.isNotBlank(userDto.getUsername()), User::getUsername, userDto.getUsername())
                .like(StrUtil.isNotBlank(userDto.getEmail()), User::getEmail, userDto.getEmail())
                .like(StrUtil.isNotBlank(userDto.getQq()), User::getQq, userDto.getQq())
                .like(StrUtil.isNotBlank(userDto.getPhone()), User::getPhone, userDto.getPhone())
                .eq(null != userDto.getState(), User::getState, userDto.getState())
                .eq(null != userDto.getRoleId(), User::getRoleId, userDto.getRoleId())
                .eq(null != userDto.getParentId(), User::getParentId, userDto.getParentId())
                .between(null != userDto.getStartTime() && null != userDto.getEndTime(), User::getCreateTime, userDto.getStartTime(), userDto.getEndTime())
                .orderByAsc(User::getRoleId)
                .orderByDesc(User::getCreateTime)
                .page(new Page<>(userDto.getPageNum(), userDto.getPageSize()));
    }

    public User selectByUsername(String username) {
        return lambdaQuery().select(User::getId, User::getRoleId, User::getUsername, User::getPassword, User::getState).eq(User::getUsername, username).one();
    }

    public Integer countByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).count();
    }

    public List<Long> selectChild(Long id, Boolean bool) {
        User user = getById(id);
        List<User> users = userDao.selectChild(user.getPath() + user.getId() + "-");
        List<Long> ids = users.stream().map(User::getId).collect(Collectors.toList());
        if (bool) ids.add(id);
        return ids;
    }
}