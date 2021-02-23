package com.jonsson.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jonsson.dao.PermissionDao;
import com.jonsson.entity.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PermissionService extends ServiceImpl<PermissionDao, Permission>  {
}
