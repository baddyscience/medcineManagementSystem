/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 17/11/2024 22:20:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 142 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (40, '111', '111', '111', '111', '111', '111', 'http://localhost:5000/file/download/111/QQ图片20240627185101.jpg', '管理员');
INSERT INTO `user` VALUES (128, 'pmckinney', 'A(i8Zbk(WDmd', 'Jacob Foster', '(455)766-2923x52110', 'erica87@example.org', '8180 Amber Points Suite 063\nWest Andrea, SC 64275', 'https://dummyimage.com/232x298', '用户');
INSERT INTO `user` VALUES (129, 'walkeredward', 'le6c3XFz+Mf4', 'Kimberly Wade', '001-908-785-6231x2804', 'christopher57@example.org', '9637 Allen Circle Apt. 295\nAnnafurt, ND 85708', 'https://picsum.photos/651/680', '用户');
INSERT INTO `user` VALUES (130, 'wilsonrobin', '_XVtn0dq60OI', 'Christopher Gomez', '861.219.4371x262', 'ltaylor@example.org', '8356 Coffey Shores Apt. 036\nStaceystad, AR 28199', 'https://picsum.photos/846/181', '用户');
INSERT INTO `user` VALUES (131, 'jennifer35', 'U5D1MBjzqCl^', 'Kristen Daniels', '8862228657', 'scochran@example.org', '718 Stephanie Dam Apt. 977\nLake Kathleen, UT 61434', 'https://placekitten.com/219/44', '用户');
INSERT INTO `user` VALUES (132, 'andersonmark', 'F3ICQyY7$1b&', 'Victoria White', '(947)920-0086x304', 'dennis44@example.net', '274 Robinson Lodge\nRobinsonport, NC 12171', 'https://picsum.photos/448/54', '用户');
INSERT INTO `user` VALUES (138, '666', '666', NULL, NULL, NULL, NULL, NULL, '用户');
INSERT INTO `user` VALUES (140, '131', '131', NULL, NULL, NULL, NULL, NULL, '管理员');

SET FOREIGN_KEY_CHECKS = 1;
