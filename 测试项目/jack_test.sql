/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : localhost:3306
 Source Schema         : jack_test

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : 65001

 Date: 13/03/2019 15:09:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fk_admin
-- ----------------------------
DROP TABLE IF EXISTS `fk_admin`;
CREATE TABLE `fk_admin`  (
  `admid` int(50) NOT NULL AUTO_INCREMENT,
  `admname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`admid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_author
-- ----------------------------
DROP TABLE IF EXISTS `t_author`;
CREATE TABLE `t_author`  (
  `autid` int(50) NOT NULL AUTO_INCREMENT,
  `autname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autsex` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`autid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_author
-- ----------------------------
INSERT INTO `t_author` VALUES (1, '鲁迅', '男');
INSERT INTO `t_author` VALUES (2, '冰心', '女');
INSERT INTO `t_author` VALUES (3, '萧鼎', '男');
INSERT INTO `t_author` VALUES (4, '余华', '男');
INSERT INTO `t_author` VALUES (5, '小小余', '男');

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `bid` int(50) NOT NULL AUTO_INCREMENT,
  `bname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `stid` int(50) NULL DEFAULT NULL,
  `autid` int(50) NULL DEFAULT NULL,
  PRIMARY KEY (`bid`) USING BTREE,
  INDEX `ehdo54`(`stid`) USING BTREE,
  INDEX `xdty18`(`autid`) USING BTREE,
  CONSTRAINT `xdty18` FOREIGN KEY (`autid`) REFERENCES `t_author` (`autid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ehdo54` FOREIGN KEY (`stid`) REFERENCES `t_stort` (`stid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, '《诛仙》', 23.3, 1, 3);
INSERT INTO `t_book` VALUES (2, '《活着》', 45.7, 2, 4);
INSERT INTO `t_book` VALUES (3, '《朝花夕拾》', 23.5, 2, 1);
INSERT INTO `t_book` VALUES (4, '《斯人独憔悴》', 44.4, 2, 2);
INSERT INTO `t_book` VALUES (5, '444', 444, 2, 3);
INSERT INTO `t_book` VALUES (6, '666', 666, 2, 3);
INSERT INTO `t_book` VALUES (7, '777', 777, 4, 2);
INSERT INTO `t_book` VALUES (8, '888', 888, 1, 3);
INSERT INTO `t_book` VALUES (9, '999', 999, 2, 4);
INSERT INTO `t_book` VALUES (10, '书名1', 45.7, 1, 1);

-- ----------------------------
-- Table structure for t_stort
-- ----------------------------
DROP TABLE IF EXISTS `t_stort`;
CREATE TABLE `t_stort`  (
  `stid` int(50) NOT NULL AUTO_INCREMENT,
  `stname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_stort
-- ----------------------------
INSERT INTO `t_stort` VALUES (1, '网络小说');
INSERT INTO `t_stort` VALUES (2, '文学名著');
INSERT INTO `t_stort` VALUES (3, '恐怖悬疑');
INSERT INTO `t_stort` VALUES (4, '儿童读物');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(50) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Sex` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
