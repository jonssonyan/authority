DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：0/下架 1/上架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商品分类';
LOCK TABLES `category` WRITE;
INSERT INTO `category` VALUES (1,NULL,NULL,NULL,'测试分类',1,1,'2021-11-28 15:08:32','2021-11-28 15:08:32');
UNLOCK TABLES;
DROP TABLE IF EXISTS `menu_list`;
CREATE TABLE `menu_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `router` varchar(50) DEFAULT NULL COMMENT '路由',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
LOCK TABLES `menu_list` WRITE;
INSERT INTO `menu_list` VALUES (1,NULL,'用户管理','el-icon-user-solid',1,'',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(2,NULL,'分类管理','el-icon-star-on',2,'',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(3,NULL,'商品管理','el-icon-s-goods',3,'',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(4,NULL,'订单管理','el-icon-s-order',4,'',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(5,NULL,'权限管理','el-icon-collection-tag',5,'',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(6,NULL,'角色管理','el-icon-s-flag',6,'',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(7,1,'用户列表','',7,'user',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(8,2,'分类列表','',8,'category',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(9,3,'商品列表','',9,'product',1,'2021-11-28 14:30:27','2021-11-28 15:06:04'),(10,4,'订单列表','',10,'order',1,'2021-11-28 14:30:28','2021-11-28 15:06:04'),(11,5,'权限列表','',11,'power',1,'2021-11-28 14:30:28','2021-11-28 15:06:04'),(12,6,'角色列表','',12,'role',1,'2021-11-28 14:30:28','2021-11-28 15:06:04');
UNLOCK TABLES;
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `quantity` bigint(20) DEFAULT NULL COMMENT '数量',
  `subject` varchar(50) DEFAULT NULL COMMENT '订单标题',
  `address` varchar(200) DEFAULT NULL COMMENT '收货地址',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '商户订单号',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0：未支付 1：已支付',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='订单';
LOCK TABLES `order` WRITE;
INSERT INTO `order` VALUES (2,1,1,1,'测试订单','上海','1122333',1.00,1,'2021-11-28 16:29:22','2021-11-28 16:29:26');
UNLOCK TABLES;
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `description` varchar(50) DEFAULT NULL COMMENT '权限描述',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='权限';
LOCK TABLES `permission` WRITE;
INSERT INTO `permission` VALUES (1,'user:select','用户查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(2,'user:update','用户修改',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(3,'user:delete','用户删除',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(4,'product:delete','商品删除',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(5,'product:update','商品修改',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(6,'product:add','商品添加',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(7,'role:add','角色添加',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(8,'role:update','角色修改',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(9,'role:delete','角色删除',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(10,'role:select','角色查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(11,'menuList:delete','菜单删除',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(12,'menuList:add','菜单添加',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(13,'menuList:update','菜单修改',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(14,'order:delete','订单删除',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(15,'order:update','订单修改',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(16,'rolePermission:add','授权添加',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(17,'rolePermission:delete','授权删除',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(18,'rolePermission:select','授权查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(19,'permission:select','权限查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(20,'user:add','用户添加',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(21,'product:select','商品查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(22,'menuList:select','菜单查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(23,'order:select','订单查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(24,'order:add','订单添加',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(25,'category:select','分类查询',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(26,'category:delete','分类删除',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(27,'category:update','分类修改',1,'2021-11-28 15:07:36','2021-11-28 15:07:36'),(28,'category:add','分类添加',1,'2021-11-28 15:07:36','2021-11-28 15:07:36');
UNLOCK TABLES;
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：0/下架 1/上架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商品';
LOCK TABLES `product` WRITE;
INSERT INTO `product` VALUES (1,1,'iphone x',5000.00,1,1,'2021-11-28 15:09:06','2021-11-28 15:09:06');
UNLOCK TABLES;
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级角色',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色';
LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (1,NULL,'admin','系统管理员',1,'2021-01-10 22:49:17','2021-03-29 15:19:15'),(2,1,'user','普通用户',1,'2021-01-13 00:28:58','2021-03-29 15:19:15');
UNLOCK TABLES;
DROP TABLE IF EXISTS `role_menu_list`;
CREATE TABLE `role_menu_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `menu_list_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `state` tinyint(4) DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';
LOCK TABLES `role_menu_list` WRITE;
INSERT INTO `role_menu_list` VALUES (1,1,1,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(2,1,2,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(3,1,3,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(4,1,4,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(5,1,5,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(6,1,6,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(7,1,7,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(8,1,8,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(9,1,9,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(10,1,10,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(11,1,11,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(12,1,12,1,'2021-11-28 15:10:03','2021-11-28 15:10:03'),(13,2,2,1,'2021-11-28 16:30:05','2021-11-28 16:30:05'),(14,2,3,1,'2021-11-28 16:30:05','2021-11-28 16:30:05'),(15,2,4,1,'2021-11-28 16:30:05','2021-11-28 16:30:05'),(16,2,8,1,'2021-11-28 16:30:05','2021-11-28 16:30:05'),(17,2,9,1,'2021-11-28 16:30:05','2021-11-28 16:30:05'),(18,2,10,1,'2021-11-28 16:30:05','2021-11-28 16:30:05');
UNLOCK TABLES;
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='角色权限';
LOCK TABLES `role_permission` WRITE;
INSERT INTO `role_permission` VALUES (1,1,1,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(2,1,2,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(3,1,3,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(4,1,4,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(5,1,5,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(6,1,6,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(7,1,7,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(8,1,8,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(9,1,9,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(10,1,10,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(11,1,11,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(12,1,12,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(13,1,13,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(14,1,14,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(15,1,15,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(16,1,16,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(17,1,17,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(18,1,18,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(19,1,19,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(20,1,20,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(21,1,21,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(22,1,22,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(23,1,23,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(24,1,24,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(25,1,25,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(26,1,26,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(27,1,27,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(28,1,28,1,'2021-11-28 15:07:54','2021-11-28 15:07:54'),(29,2,4,1,'2021-11-28 16:30:57','2021-11-28 16:30:57'),(30,2,5,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(31,2,6,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(32,2,14,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(33,2,15,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(34,2,21,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(35,2,23,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(36,2,24,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(37,2,25,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(38,2,26,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(39,2,27,1,'2021-11-28 16:30:57','2021-11-28 16:31:04'),(40,2,28,1,'2021-11-28 16:32:35','2021-11-28 16:37:20'),(41,2,22,1,'2021-11-28 19:57:35','2021-11-28 19:57:35');
UNLOCK TABLES;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '上级id',
  `path` varchar(200) NOT NULL DEFAULT '1-' COMMENT '路径',
  `level` int(11) NOT NULL DEFAULT '1' COMMENT '等级',
  `role_id` bigint(20) NOT NULL DEFAULT '2' COMMENT '角色id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq号',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0/禁止 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,0,'',0,1,'admin','b2f0c414a5ffb6944c424bc18ae3a8a0','admin@qq.com',NULL,NULL,1,'2021-11-28 14:31:39','2021-11-28 15:34:49'),(2,1,'1-',1,2,'user1','b2f0c414a5ffb6944c424bc18ae3a8a0','user1@qq.com',NULL,NULL,1,'2021-11-28 14:31:39','2021-11-28 15:09:36'),(3,1,'1-',1,2,'user2','e046c4175152aa3001d2c71c5bc1aee5','user2@qq.com',NULL,NULL,1,'2021-11-28 14:31:39','2021-11-28 15:09:36');
UNLOCK TABLES;
