-- MySQL dump 10.13  Distrib 5.7.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: authority
-- ------------------------------------------------------
-- Server version	5.7.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：0/下架 1/上架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='商品分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (6,'华为',6,3,1,'2021-01-15 00:50:10','2021-01-15 00:50:10');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_list`
--

DROP TABLE IF EXISTS `menu_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `path` varchar(50) DEFAULT NULL COMMENT '路由',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_list`
--

LOCK TABLES `menu_list` WRITE;
/*!40000 ALTER TABLE `menu_list` DISABLE KEYS */;
INSERT INTO `menu_list` VALUES (1,NULL,'主页','el-icon-s-home',1,'home',1,'2021-01-10 22:48:18','2021-01-12 00:56:12'),(2,NULL,'用户管理','el-icon-user-solid',2,'',1,'2021-01-10 22:48:18','2021-01-12 00:56:12'),(3,NULL,'分类管理','el-icon-star-on',3,'',1,'2021-01-10 22:48:18','2021-01-12 00:56:12'),(4,NULL,'商品管理','el-icon-s-goods',4,'',1,'2021-01-10 22:48:18','2021-01-12 00:56:12'),(6,NULL,'订单管理','el-icon-s-order',6,'',1,'2021-01-10 22:48:18','2021-01-12 00:56:12'),(8,NULL,'权限管理','el-icon-collection-tag',8,'',1,'2021-01-10 22:48:18','2021-01-12 00:56:12'),(9,NULL,'角色管理','el-icon-s-flag',9,'',1,'2021-01-10 22:48:18','2021-01-12 00:56:12'),(10,2,'用户列表','',10,'userList',1,'2021-01-10 22:48:18','2021-01-25 22:05:07'),(11,3,'分类列表','',11,'categoryList',1,'2021-01-10 22:48:18','2021-01-25 22:05:07'),(12,4,'商品列表','',12,'productList',1,'2021-01-10 22:48:18','2021-01-25 22:05:07'),(14,6,'订单列表','',14,'orderList',1,'2021-01-10 22:57:11','2021-01-25 22:05:07'),(16,8,'权限列表','',16,'rightsList',1,'2021-01-10 22:57:11','2021-01-25 22:05:07'),(17,9,'角色列表','',17,'roleList',1,'2021-01-10 22:57:11','2021-01-25 22:05:07'),(20,1,'主面板','',20,'panel',1,'2021-01-20 22:35:16','2021-01-25 22:05:07');
/*!40000 ALTER TABLE `menu_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `quantity` bigint(20) DEFAULT NULL COMMENT '数量',
  `subject` varchar(50) DEFAULT NULL COMMENT '订单标题',
  `address` varchar(200) DEFAULT NULL COMMENT '收货地址',
  `outTradeNo` varchar(50) DEFAULT NULL COMMENT '商户订单号',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0：未支付 1：已支付',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,3,3,1,'手机','南京','132131',4999.00,1,'2021-01-19 00:31:44','2021-01-20 22:21:27');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `description` varchar(50) DEFAULT NULL COMMENT '权限描述',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'user:select','用户查询',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(2,'user:update','用户修改',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(3,'user:delete','用户删除',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(4,'product:delete','商品删除',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(5,'product:update','商品修改',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(6,'product:add','商品添加',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(7,'role:add','角色添加',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(8,'role:update','角色修改',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(9,'role:delete','角色删除',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(10,'role:select','角色查询',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(11,'menuList:delete','菜单删除',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(12,'menuList:add','菜单添加',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(13,'menuList:update','菜单修改',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(14,'order:delete','订单删除',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(15,'order:update','订单更新',1,'2021-01-10 22:54:13','2021-01-10 22:54:13'),(16,'rolePermission:add','授权添加',1,'2021-01-10 22:54:13','2021-01-17 23:27:11'),(17,'rolePermission:delete','授权删除',1,'2021-01-10 22:54:13','2021-01-17 23:27:11'),(18,'rolePermission:select','授权查询',1,'2021-01-17 22:24:14','2021-01-17 23:27:11'),(19,'permission:select','权限查询',1,'2021-01-17 23:02:18','2021-01-17 23:27:11');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (4,6,'iphone x',5000.00,3,0,'2021-01-15 22:15:40','2021-01-16 16:28:52');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级角色',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,NULL,'admin',1,'2021-01-10 22:49:17','2021-01-13 01:09:03'),(2,1,'user',1,'2021-01-13 00:28:58','2021-01-13 01:09:03'),(3,1,'consumer',1,'2021-02-09 19:57:03','2021-02-09 19:57:03');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu_list`
--

DROP TABLE IF EXISTS `role_menu_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_menu_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `menu_list_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `state` tinyint(4) DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_list`
--

LOCK TABLES `role_menu_list` WRITE;
/*!40000 ALTER TABLE `role_menu_list` DISABLE KEYS */;
INSERT INTO `role_menu_list` VALUES (1,1,1,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(2,1,2,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(3,1,3,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(4,1,4,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(5,1,5,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(6,1,6,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(7,1,7,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(8,1,8,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(9,1,9,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(10,1,10,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(11,1,11,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(12,1,12,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(13,1,13,1,'2021-01-10 22:49:45','2021-01-10 22:49:45'),(14,2,1,1,'2021-01-13 00:29:54','2021-01-13 00:29:54'),(15,2,2,1,'2021-01-13 00:29:54','2021-01-13 00:29:54'),(16,2,3,1,'2021-01-13 00:29:54','2021-01-13 00:29:54'),(17,2,10,1,'2021-01-13 00:29:54','2021-01-13 00:29:54'),(18,2,11,1,'2021-01-13 00:29:54','2021-01-13 00:29:54'),(19,2,4,1,'2021-01-16 16:38:11','2021-01-16 16:38:11'),(20,2,5,1,'2021-01-16 16:38:11','2021-01-16 16:38:11'),(21,2,6,1,'2021-01-16 16:38:11','2021-01-16 16:38:11'),(22,2,7,1,'2021-01-16 16:38:11','2021-01-16 16:38:11'),(23,2,12,1,'2021-01-16 16:38:11','2021-01-16 16:38:11'),(24,2,13,1,'2021-01-16 16:38:12','2021-01-16 16:38:12'),(25,2,14,1,'2021-01-16 16:38:12','2021-01-16 16:38:12'),(26,2,15,1,'2021-01-16 16:38:12','2021-01-16 16:38:12'),(27,1,18,1,'2021-01-19 00:53:59','2021-01-19 00:53:59'),(28,2,18,1,'2021-01-19 00:53:59','2021-01-19 00:53:59');
/*!40000 ALTER TABLE `role_menu_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='角色权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1,1,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(2,1,2,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(3,1,3,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(4,1,4,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(5,1,5,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(6,1,6,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(7,1,7,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(8,1,8,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(9,1,9,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(10,1,10,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(11,1,11,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(12,1,12,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(13,1,13,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(14,1,14,1,'2021-01-10 22:54:32','2021-01-10 22:54:32'),(15,1,15,1,'2021-01-10 22:54:32','2021-01-10 22:54:37'),(16,1,16,1,'2021-01-10 22:54:32','2021-01-10 22:54:37'),(17,1,17,1,'2021-01-10 22:54:32','2021-01-10 22:54:37'),(18,2,4,1,'2021-01-16 16:44:02','2021-01-16 16:44:02'),(19,2,5,1,'2021-01-16 16:44:02','2021-01-16 16:44:02'),(20,2,6,1,'2021-01-16 16:44:02','2021-01-16 16:44:02'),(21,2,14,1,'2021-01-16 16:44:02','2021-01-16 16:44:02'),(22,2,15,1,'2021-01-16 16:44:02','2021-01-16 16:44:02'),(23,1,18,1,'2021-01-17 22:28:49','2021-01-17 22:28:49'),(25,1,19,1,'2021-01-17 23:02:27','2021-01-17 23:02:27');
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
  `domain` varchar(50) DEFAULT NULL COMMENT '域名',
  `role_id` bigint(20) DEFAULT '3' COMMENT '角色id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq号',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0/禁止 1/正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,NULL,NULL,1,'admin','b2f0c414a5ffb6944c424bc18ae3a8a0','123@qq.com',NULL,NULL,1,'2021-01-11 00:43:06','2021-01-11 00:43:52'),(4,3,NULL,2,'user1','b2f0c414a5ffb6944c424bc18ae3a8a0','user1@qq.com',NULL,NULL,1,'2021-01-13 00:28:34','2021-01-14 22:20:38'),(6,3,NULL,3,'consumer','bdc230da901e913ca8c3588bd812bb8c','consumer@qq.com',NULL,NULL,1,'2021-02-09 19:52:19','2021-02-09 20:14:21');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-24  0:33:07
