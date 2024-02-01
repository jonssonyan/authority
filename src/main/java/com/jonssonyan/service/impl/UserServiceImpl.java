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

    public IPage<User> selectPage(UserDto userVO) {
        List<Long> longs = selectChild(SecurityUtil.getCurrentUser().getId(), true);
        return lambdaQuery()
                .in(User::getId, longs)
                .like(StrUtil.isNotBlank(userVO.getUsername()), User::getUsername, userVO.getUsername())
                .like(StrUtil.isNotBlank(userVO.getEmail()), User::getEmail, userVO.getEmail())
                .like(StrUtil.isNotBlank(userVO.getQq()), User::getQq, userVO.getQq())
                .like(StrUtil.isNotBlank(userVO.getPhone()), User::getPhone, userVO.getPhone())
                .eq(null != userVO.getState(), User::getState, userVO.getState())
                .eq(null != userVO.getRoleId(), User::getRoleId, userVO.getRoleId())
                .eq(null != userVO.getParentId(), User::getParentId, userVO.getParentId())
                .between(null != userVO.getStartTime() && null != userVO.getEndTime(), User::getCreateTime, userVO.getStartTime(), userVO.getEndTime())
                .orderByAsc(User::getRoleId)
                .orderByDesc(User::getCreateTime)
                .page(new Page<>(userVO.getPageNum(), userVO.getPageSize()));
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