# 权限管理系统
一个可以管理用户权限的前后端分离系统
# 在线演示
https://www.bilibili.com/video/BV1DK4y1S7h7
# 测试账号
管理员：admin 123456
普通用户：user1 123456
这些数据是存在数据库里的，注册一个用户，然后在数据库里把角色改为1（管理员角色）即可
# 开发工具
1. JDK 1.8
2. IDEA 2019.3.5
# 框架
1. Spring Boot 2.4.3
2. Mybatis-plus 3.2.0
3. Shiro
4. JWT
# 前端地址
https://github.com/jonssonyan/authority-ui
# 界面
![界面1](https://img-blog.csdnimg.cn/20210224010314613.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3kxNTM0NDE0NDI1,size_16,color_FFFFFF,t_70)
![界面2](https://img-blog.csdnimg.cn/20210224233054534.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3kxNTM0NDE0NDI1,size_16,color_FFFFFF,t_70)

# 初始权限

| 角色  | 权限名称 |
| :---: | :------: |
| admin | 商品修改 |
| admin | 商品删除 |
| admin | 商品添加 |
| admin | 授权删除 |
| admin | 授权查询 |
| admin | 授权添加 |
| admin | 权限查询 |
| admin | 用户修改 |
| admin | 用户删除 |
| admin | 用户查询 |
| admin | 菜单修改 |
| admin | 菜单删除 |
| admin | 菜单添加 |
| admin | 角色修改 |
| admin | 角色删除 |
| admin | 角色查询 |
| admin | 角色添加 |
| admin | 订单删除 |
| admin | 订单更新 |
| user  | 商品修改 |
| user  | 商品删除 |
| user  | 商品添加 |
| user  | 订单删除 |
| user  | 订单更新 |