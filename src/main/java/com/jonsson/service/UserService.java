package com.jonsson.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.UserDao;
import com.jonsson.entity.User;
import com.jonsson.entity.vo.UserVO;
import com.jonsson.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserDao, User> {
    @Autowired
    private UserDao userDao;

    public IPage<User> selectPage(UserVO userVO) {
        List<Long> longs = selectChild(SecurityUtil.getCurrentUser().getId(), true);
        IPage<User> userIPage = lambdaQuery()
                .in(User::getId, longs)
                .like(StrUtil.isNotBlank(userVO.getUsername()), User::getUsername, userVO.getUsername())
                .like(StrUtil.isNotBlank(userVO.getEmail()), User::getEmail, userVO.getEmail())
                .like(StrUtil.isNotBlank(userVO.getQq()), User::getQq, userVO.getQq())
                .like(StrUtil.isNotBlank(userVO.getPhone()), User::getPhone, userVO.getPhone())
                .eq(null != userVO.getState(), User::getState, userVO.getState())
                .eq(null != userVO.getRoleId(), User::getRoleId, userVO.getRoleId())
                .eq(null != userVO.getParentId(), User::getParentId, userVO.getParentId())
                .between(null != userVO.getStartTime() && null != userVO.getEndTime(), User::getCreateTime, userVO.getStartTime(), userVO.getEndTime())
                .orderByDesc(User::getCreateTime)
                .page(new Page<>(userVO.getPageNum(), userVO.getPageSize()));
        return userIPage;
    }

    public User selectByUsername(String username) {
        return lambdaQuery().select(User::getId, User::getRoleId, User::getUsername, User::getPassword, User::getState).eq(User::getUsername, username).one();
    }

    public Integer countByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).count();
    }

    /**
     * 查询用户及下级用户的id集合
     *
     * @param id   用户id
     * @param bool 是否包含自己
     * @return
     */
    public List<Long> selectChild(Long id, Boolean bool) {
        User user = getById(id);
        List<User> users = userDao.selectChild(user.getPath() + user.getId() + "-");
        List<Long> ids = users.stream().map(User::getId).collect(Collectors.toList());
        if (bool) ids.add(id);
        return ids;
    }
}