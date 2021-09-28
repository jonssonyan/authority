# 介绍

一个前后端分离的用户权限管理系统

# 前端地址

https://github.com/jonssonyan/authority-ui

# 相关视频

演示视频：https://www.bilibili.com/video/BV1DK4y1S7h7

部署视频：https://www.bilibili.com/video/BV1kV411s7N2

# 开发工具

1. JDK 1.8
2. IDEA 2019.3.5

# 框架/技术

1. Spring Boot 2.4.3
2. Mybatis-plus 3.2.0
3. Shiro
4. JWT

# 默认账号

- 管理员：admin 123456

- 普通用户：user1 123456

这些数据是存在数据库里的，注册一个用户，然后在数据库里把角色改为1（管理员角色）即可

# 角色初始权限

见数据表 `permission` `role_permission` `role`

# 界面展示

![界面1](https://img-blog.csdnimg.cn/20210224010314613.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3kxNTM0NDE0NDI1,size_16,color_FFFFFF,t_70)
![界面2](https://img-blog.csdnimg.cn/20210224233054534.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3kxNTM0NDE0NDI1,size_16,color_FFFFFF,t_70)
