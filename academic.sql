/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


CREATE DATABASE IF NOT EXISTS `academic`;
USE `academic`;


-- 禁用外键检查
SET foreign_key_checks = 0;

-- 删除已存在的表，如果存在的话
DROP TABLE IF EXISTS `researcher`;
DROP TABLE IF EXISTS `article`;
DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `award`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `portal`;
DROP TABLE IF EXISTS `patent`;
DROP TABLE IF EXISTS `reviewrecords`;
DROP TABLE IF EXISTS `message`;


-- 启用外键检查
SET foreign_key_checks = 1;

-- academic.patent definition

CREATE TABLE `patent` (
  `patent_id` int NOT NULL AUTO_INCREMENT,
  `patent_name` text NOT NULL,
  `patent_type` varchar(20) NOT NULL,
  `application_num` varchar(20) DEFAULT NULL,
  `publication_num` varchar(20) DEFAULT NULL,
  `authorization_num` varchar(20) DEFAULT NULL,
  `application_date` datetime DEFAULT NULL,
  `publication_date` datetime DEFAULT NULL,
  `authorization_date` datetime DEFAULT NULL,
  `main_claim` text,
  `abstract_text` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `applicants` text,
  `inventors_id` text,
  `field_of_research` varchar(20) NOT NULL,
  `views` int DEFAULT NULL,
  PRIMARY KEY (`patent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- academic.researcher definition

CREATE TABLE `researcher` (
  `researcher_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `field_of_research` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `article_ids` text,
  `patent_ids` text,
  `project_ids` text,
  `award_ids` text,
  `institution` varchar(50) DEFAULT NULL,
  `awards` text,
  `claimed` tinyint(1) DEFAULT '0',
  `url` varchar(256) NOT NULL DEFAULT '0',
  PRIMARY KEY (`researcher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- academic.article definition

CREATE TABLE `article` (
  `article_id` int NOT NULL AUTO_INCREMENT COMMENT '论文编号',
  `article_name` text NOT NULL COMMENT '论文的名称',
  `doi` text COMMENT 'DOI',
  `abstract_text` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '摘要',
  `keywords` text COMMENT '关键词',
  `researcher_id` int DEFAULT NULL COMMENT '作者',
  `field_of_research` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属领域',
  `publish_time` datetime DEFAULT NULL COMMENT '发表时间',
  `category_num` varchar(20) DEFAULT NULL COMMENT '分类号',
  `pages` int DEFAULT NULL COMMENT '页数',
  `views` int DEFAULT NULL COMMENT '查看次数',
  `source` text COMMENT '来源',
  `references_ids` text COMMENT '经处理后存储的引用文献ID串',
  `url` varchar(256) NOT NULL DEFAULT '0',
  `researcher_url` varchar(256) NOT NULL DEFAULT '0',
  PRIMARY KEY (`article_id`),
  KEY `fk_researcher_id` (`researcher_id`),
  CONSTRAINT `fk_researcher_id` FOREIGN KEY (`researcher_id`) REFERENCES `researcher` (`researcher_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- academic.award definition

CREATE TABLE `award` (
  `award_id` int NOT NULL AUTO_INCREMENT COMMENT '奖项编号',
  `award_name` text NOT NULL COMMENT '奖项名称',
  `award_type` text COMMENT '奖项类型',
  `award_date` date DEFAULT NULL COMMENT '获奖时间',
  `winner_id` int DEFAULT NULL COMMENT '获奖者ID',
  `award_description` text COMMENT '奖项介绍',
  PRIMARY KEY (`award_id`),
  KEY `fk_winner` (`winner_id`),
  CONSTRAINT `fk_winner` FOREIGN KEY (`winner_id`) REFERENCES `researcher` (`researcher_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='奖项表';


-- academic.project definition

CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT COMMENT '项目编号',
  `project_name` text NOT NULL COMMENT '项目名称',
  `project_type` text COMMENT '项目类型',
  `start_date` date DEFAULT NULL COMMENT '项目开始日期',
  `end_date` date DEFAULT NULL COMMENT '项目结束日期',
  `funding_sources` text COMMENT '资金来源',
  `field_of_research` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '研究领域',
  `investigator_id` int DEFAULT NULL COMMENT '主要负责人ID',
  `participants_id` text COMMENT '经过处理的参与者ID串',
  `project_description` longtext COMMENT '项目描述',
  `project_status` text COMMENT '项目状态',
  PRIMARY KEY (`project_id`),
  KEY `fk_investigator_id` (`investigator_id`),
  CONSTRAINT `fk_investigator_id` FOREIGN KEY (`investigator_id`) REFERENCES `researcher` (`researcher_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储项目信息';


-- academic.`user` definition

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `date_joined` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `phone_number` char(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'user',
  `science_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`user_name`),
  KEY `fk_science_id` (`science_id`),
  CONSTRAINT `fk_science_id` FOREIGN KEY (`science_id`) REFERENCES `researcher` (`researcher_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- academic.portal definition

CREATE TABLE `portal` (
  `portal_id` int NOT NULL AUTO_INCREMENT COMMENT '门户编号',
  `is_claimed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已被认领',
  `belong_user_id` int DEFAULT NULL COMMENT '记录门户主人的ID，无人认领时为空',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `claimed_time` datetime DEFAULT NULL COMMENT '认领时间，无人认领时为空',
  `administrator_id` int DEFAULT NULL COMMENT '处理门户认领审核的管理员ID，无人认领时为空',
  `Science_id` int DEFAULT NULL COMMENT '连接对应的科研人员信息',
  PRIMARY KEY (`portal_id`),
  KEY `fk_belong_user_id` (`belong_user_id`),
  KEY `fk_administrator_id` (`administrator_id`),
  KEY `fk_science_id_2` (`Science_id`),
  CONSTRAINT `fk_administrator_id` FOREIGN KEY (`administrator_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_belong_user_id` FOREIGN KEY (`belong_user_id`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_science_id_2` FOREIGN KEY (`Science_id`) REFERENCES `researcher` (`researcher_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- academic.ReviewRecords definition

CREATE TABLE `ReviewRecords` (
  `Record_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '审核记录编号',
  `creater_id` INT NOT NULL COMMENT '发起者ID',
  `Reviewer_id` INT NOT NULL COMMENT '审核者ID',
  `Review_date` DATETIME NOT NULL COMMENT '审核时间',
  `Request_type` VARCHAR(20) NOT NULL COMMENT '请求类型',
  `action` VARCHAR(10) NOT NULL COMMENT '操作结果',
  `coment` TEXT COMMENT '审核评论',
  FOREIGN KEY (`creater_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`Reviewer_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审核记录存储';


-- academic.Message definition

CREATE TABLE `Message` (
  `message_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '消息编号',
  `sender_id` INT COMMENT '发送方ID',
  `receiver_id` INT COMMENT '接收方ID',
  `Category` VARCHAR(20) COMMENT '消息类别',
  `title` VARCHAR(50) COMMENT '标题',
  `content` LONGTEXT COMMENT '内容',
  `created_time` DATETIME NOT NULL COMMENT '创建时间',
  `updated_time` DATETIME COMMENT '更新时间',
  `if_confirmed` BOOLEAN COMMENT '是否被接收方确认接受',
  FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`),
  FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学术成果分享平台消息存储';


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;