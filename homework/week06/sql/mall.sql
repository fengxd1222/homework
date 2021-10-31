/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 31/10/2021 21:58:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_brand
-- ----------------------------
DROP TABLE IF EXISTS `mall_brand`;
CREATE TABLE `mall_brand`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌名称',
  `image` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌图片地址',
  `letter` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌的首字母',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '品牌表，一个品牌下有多个商品，一对多关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_category
-- ----------------------------
DROP TABLE IF EXISTS `mall_category`;
CREATE TABLE `mall_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类Id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) NOT NULL COMMENT '父分类Id (顶级类目填0)',
  `is_parent` tinyint(1) NOT NULL COMMENT '是否为父节点 (0-否，1-是)',
  `sort` tinyint(2) NOT NULL COMMENT '排序指数，越小越靠前',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '数据更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `key_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品类目表，类目和商品是一对多关系，类目与品牌是多对多关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_category_brand
-- ----------------------------
DROP TABLE IF EXISTS `mall_category_brand`;
CREATE TABLE `mall_category_brand`  (
  `category_id` bigint(20) NOT NULL COMMENT '商品类目id',
  `brand_id` bigint(20) NOT NULL COMMENT '品牌id',
  PRIMARY KEY (`category_id`, `brand_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类和品牌的中间表，两者是多对多关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_commodity
-- ----------------------------
DROP TABLE IF EXISTS `mall_commodity`;
CREATE TABLE `mall_commodity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `brand_id` bigint(20) NOT NULL COMMENT '品牌id',
  `title` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '' COMMENT '商品标题',
  `description` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '' COMMENT '商品描述,规格参数等复杂的详细信息可以保存为html放入es或缓存',
  `is_deleted` tinyint(1) UNSIGNED ZEROFILL NOT NULL COMMENT '逻辑删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_commodity_stock
-- ----------------------------
DROP TABLE IF EXISTS `mall_commodity_stock`;
CREATE TABLE `mall_commodity_stock`  (
  `commodity_id` bigint(20) NOT NULL COMMENT '商品id',
  `area_id` bigint(20) NOT NULL COMMENT '地区id',
  `remaining_inventory` int(10) NOT NULL DEFAULT 0 COMMENT '商品剩余库存',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`commodity_id`, `area_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_customer_addr
-- ----------------------------
DROP TABLE IF EXISTS `mall_customer_addr`;
CREATE TABLE `mall_customer_addr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT 'customer_login表的自增ID',
  `post_code` bigint(20) NOT NULL COMMENT '邮编',
  `province` bigint(20) NOT NULL COMMENT '地区表中省份的ID',
  `city` bigint(20) NOT NULL COMMENT '地区表中城市的ID',
  `district` bigint(20) NOT NULL COMMENT '地区表中的区ID',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '具体的地址门牌号',
  `is_default` tinyint(4) NOT NULL COMMENT '是否默认',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_customer_info
-- ----------------------------
DROP TABLE IF EXISTS `mall_customer_info`;
CREATE TABLE `mall_customer_info`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT 'loginID',
  `customer_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户真实姓名',
  `identity_card_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '证件类型：1 身份证，2 军官证，3 护照',
  `identity_card_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `mobile_phone` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '手机号',
  `customer_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `user_point` int(11) NOT NULL DEFAULT 0 COMMENT '用户积分',
  `register_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '会员生日',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_customer_login
-- ----------------------------
DROP TABLE IF EXISTS `mall_customer_login`;
CREATE TABLE `mall_customer_login`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户登录名',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'md5加密的密码',
  `user_stats` tinyint(4) NOT NULL DEFAULT 1 COMMENT '用户状态',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_order
-- ----------------------------
DROP TABLE IF EXISTS `mall_order`;
CREATE TABLE `mall_order`  (
  `id` bigint(20) NOT NULL COMMENT '订单表 id',
  `customer_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_info_id` bigint(20) NOT NULL COMMENT '订单详细信息id',
  `customer_addr_id` bigint(20) NOT NULL COMMENT '配送地址id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_order_info
-- ----------------------------
DROP TABLE IF EXISTS `mall_order_info`;
CREATE TABLE `mall_order_info`  (
  `id` bigint(20) NOT NULL COMMENT '订单详细信息id',
  `commodity_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `area_id` bigint(20) NULL DEFAULT NULL COMMENT '商品来源（仓库,退货用）',
  `count` int(10) NULL DEFAULT NULL COMMENT '数量',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `is_preferential` tinyint(1) NULL DEFAULT NULL COMMENT '是否优惠',
  `preferential_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠力度（便宜了多少钱）',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mall_stock_area
-- ----------------------------
DROP TABLE IF EXISTS `mall_stock_area`;
CREATE TABLE `mall_stock_area`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地区id',
  `post_code` bigint(20) NOT NULL COMMENT '邮编',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '地区(仓库)名称',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地区（仓库）表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
