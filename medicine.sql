/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : localhost:3306
 Source Schema         : medicine

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 24/09/2024 17:39:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agency
-- ----------------------------
DROP TABLE IF EXISTS `agency`;
CREATE TABLE `agency`  (
  `ano` int(0) NOT NULL AUTO_INCREMENT,
  `aname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `asex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `aphone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `aremark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ano`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `cno` int(0) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `csex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `cage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `caddress` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `cphone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `csymptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `mno` int(0) NULL DEFAULT NULL,
  `ano` int(0) NULL DEFAULT NULL,
  `cdate` datetime(6) NULL DEFAULT NULL,
  `cremark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`cno`) USING BTREE,
  INDEX `ano`(`ano`) USING BTREE,
  INDEX `mno`(`mno`) USING BTREE,
  CONSTRAINT `ano` FOREIGN KEY (`ano`) REFERENCES `agency` (`ano`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mno` FOREIGN KEY (`mno`) REFERENCES `medicine` (`mno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `mno` int(0) NOT NULL AUTO_INCREMENT,
  `mname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `mmode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `mefficacy` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`mno`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES (1, '1', '1', '1');
INSERT INTO `medicine` VALUES (3, '1', '1', '1');
INSERT INTO `medicine` VALUES (4, '2', '2', '2');

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

-- ----------------------------
-- Procedure structure for remove_underscores_from_columns
-- ----------------------------
DROP PROCEDURE IF EXISTS `remove_underscores_from_columns`;
delimiter ;;
CREATE PROCEDURE `remove_underscores_from_columns`(IN zqudatacollectordb VARCHAR(255))
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE current_table VARCHAR(255);
    DECLARE current_column VARCHAR(255);
    DECLARE new_column_name VARCHAR(255);
    DECLARE cur1 CURSOR FOR 
        SELECT table_name, column_name 
        FROM information_schema.columns 
        WHERE table_schema = db_name;
        
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur1;

    read_loop: LOOP
        FETCH cur1 INTO current_table, current_column;
        IF done THEN
            LEAVE read_loop;
        END IF;

        SET new_column_name = REPLACE(current_column, '_', '');

        IF new_column_name != current_column THEN
            SET @sql = CONCAT('ALTER TABLE `', current_table, '` CHANGE `', current_column, '` `', new_column_name, '` ', 
                              (SELECT column_type 
                               FROM information_schema.columns 
                               WHERE table_schema = db_name
                               AND table_name = current_table 
                               AND column_name = current_column), ';');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

    END LOOP;

    CLOSE cur1;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
