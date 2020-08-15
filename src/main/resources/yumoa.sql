/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : yumoa

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-08-14 19:05:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for yum_department
-- ----------------------------
DROP TABLE IF EXISTS `yum_department`;
CREATE TABLE `yum_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` int(5) NOT NULL COMMENT '部门编码',
  `dept_name` varchar(10) NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父部门id',
  `dept_num` int(5) NOT NULL DEFAULT '0' COMMENT '部门人数',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `modified_date` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0:正常,1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for yum_menu
-- ----------------------------
DROP TABLE IF EXISTS `yum_menu`;
CREATE TABLE `yum_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` int(5) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(10) NOT NULL COMMENT '目录名称',
  `path` varchar(30) NOT NULL COMMENT '菜单路径',
  `parent_id` bigint(20) NOT NULL COMMENT '父级菜单',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用0:否,1:是',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `modified_date` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除0:正常,1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for yum_role
-- ----------------------------
DROP TABLE IF EXISTS `yum_role`;
CREATE TABLE `yum_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` int(5) NOT NULL COMMENT '角色编号',
  `role_name` varchar(10) NOT NULL COMMENT '角色名称',
  `role_num` int(5) NOT NULL DEFAULT '0' COMMENT '角色人数',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_date` datetime DEFAULT NULL COMMENT '创建日期',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `modified_date` datetime DEFAULT NULL COMMENT '修改日期',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除0:正常1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for yum_user
-- ----------------------------
DROP TABLE IF EXISTS `yum_user`;
CREATE TABLE `yum_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` int(5) NOT NULL COMMENT '员工编号',
  `username` varchar(10) NOT NULL DEFAULT '' COMMENT '用户名称',
  `mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `avatar` varchar(200) DEFAULT NULL COMMENT '用户头像',
  `gender` char(1) NOT NULL COMMENT '用户性别 0:男,1:女',
  `age` tinyint(2) NOT NULL COMMENT '年龄',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `dept_id` bigint(20) NOT NULL COMMENT '所在部门主键',
  `role_id` bigint(20) NOT NULL COMMENT '所属角色主键',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `modified_date` datetime DEFAULT NULL COMMENT '修改时间',
  `id_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0:正常,1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
