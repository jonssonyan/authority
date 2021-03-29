package com.jonsson.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.UserDao;
import com.jonsson.entity.User;
import com.jonsson.entity.vo.UserVO;
import com.jonsson.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserDao, User> {

    public IPage<User> selectPage(UserVO userVO) {
        List<Long> longs = selectUserIds(SecurityUtil.getCurrentUser().getId(), true);
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
        return lambdaQuery().eq(User::getUsername, username).one();
    }

    public Integer countByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).count();
    }

    /**
     * 查询用户及下级用户的id集合
     *
     * @param id
     * @param bool
     * @return
     */
    public List<Long> selectUserIds(Long id, Boolean bool) {
        List<Long> ids = new ArrayList<>();
        if (bool) ids.add(id);
        selectByParentId(id, ids);
        return ids;
    }

    public void selectByParentId(Long parentId, List<Long> ids) {
        List<User> list = lambdaQuery().eq(User::getParentId, parentId).list();
        if (CollectionUtil.isNotEmpty(list)) {
            List<Long> collect = list.stream().map(User::getId).collect(Collectors.toList());
            ids.addAll(collect);
            for (User user : list) {
                selectByParentId(user.getId(), ids);
            }
        }
    }
}