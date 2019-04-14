/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : jack_test

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2019-04-08 14:53:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `accid` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `money` int(50) DEFAULT NULL,
  PRIMARY KEY (`accid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'jack', '1000');
INSERT INTO `account` VALUES ('2', 'lucy', '1000');

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admid` int(50) NOT NULL AUTO_INCREMENT,
  `admname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`admid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for `author`
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `autid` int(50) NOT NULL AUTO_INCREMENT,
  `autname` varchar(50) DEFAULT NULL,
  `autsex` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`autid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of author
-- ----------------------------
INSERT INTO `author` VALUES ('1', '您', '男');
INSERT INTO `author` VALUES ('2', '你', '女');

-- ----------------------------
-- Table structure for `stort`
-- ----------------------------
DROP TABLE IF EXISTS `stort`;
CREATE TABLE `stort` (
  `stid` int(50) NOT NULL AUTO_INCREMENT,
  `stname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`stid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stort
-- ----------------------------
INSERT INTO `stort` VALUES ('1', '去');
INSERT INTO `stort` VALUES ('2', '额');

-- ----------------------------
-- Table structure for `t_book`
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book` (
  `bid` varchar(50) NOT NULL,
  `bname` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `stid` int(50) DEFAULT NULL,
  `autid` int(50) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  KEY `useu91` (`stid`),
  KEY `mgpw76` (`autid`),
  CONSTRAINT `mgpw76` FOREIGN KEY (`autid`) REFERENCES `author` (`autid`),
  CONSTRAINT `useu91` FOREIGN KEY (`stid`) REFERENCES `stort` (`stid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES ('book2', '3', '33', '2', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(50) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) DEFAULT NULL,
  `ssex` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Lucy', '女', '1234');
INSERT INTO `user` VALUES ('2', 'Jack', '男', '1111');
INSERT INTO `user` VALUES ('3', 'Karl', '男', '1111');
INSERT INTO `user` VALUES ('8', 'Abby', '女', '1111');
INSERT INTO `user` VALUES ('9', 'Lydia', '女', '1111');
INSERT INTO `user` VALUES ('10', 'Nance', '男', '1111');
INSERT INTO `user` VALUES ('11', 'Janet', '女', '1111');
INSERT INTO `user` VALUES ('12', 'Cook', '男', '1111');
