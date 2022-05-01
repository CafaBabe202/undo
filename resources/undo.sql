-- MySQL dump 10.13  Distrib 8.0.28, for Linux (x86_64)
--
-- Host: localhost    Database: undo
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.20.04.3

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

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `summary` varchar(256) DEFAULT NULL,
  `records_id` varchar(64) DEFAULT NULL,
  `like` int DEFAULT '0',
  `visit` int DEFAULT '0',
  `clazz_id` int DEFAULT '0',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_private` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `article_title_index` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (76,17,'第一篇笔记','这是第一篇测试笔记','6263562f9039cc241b09bbc4',2,92,24,'2022-04-23 01:28:16','2022-04-27 12:14:11',1),(77,17,'MySQL笔记','这是MySQL的笔记，记载了从数据库到JDBC的详细内容，横适合小白的学习','626358af9039cc241b09bbee',1,1824,24,'2022-04-23 01:38:55','2022-05-01 00:28:52',0),(78,17,'Java集合框架','这里详细总结了Java集合框架中各种类的声明方式，通过这篇文章，可以很容易查看各个集合类的声明方式','626359589039cc241b09bbf4',1,12,24,'2022-04-23 01:41:44','2022-04-23 01:41:44',0),(79,17,'SSMTP协议','这是自创的一种服务器状态监控协议，但由于时间问题，并没能实现，很是可惜','626359b69039cc241b09bbf7',0,8,25,'2022-04-23 01:43:19','2022-04-24 01:35:30',0),(81,17,'ddd','aaaa','6264f9960eeef343768dbcfa',1,22,25,'2022-04-24 07:17:42','2022-04-24 07:17:42',0),(82,18,'测试','测试','62673fa7faae0a7eb10b68cc',0,1,28,'2022-04-26 00:41:12','2022-04-26 00:41:12',0),(83,19,'这是标题','标题','62673feefaae0a7eb10b68cf',0,1,29,'2022-04-26 00:42:23','2022-04-26 00:42:23',0),(84,20,'Java','这是摘要','6267403afaae0a7eb10b68d2',1,4,30,'2022-04-26 00:43:39','2022-04-26 00:43:39',0),(85,21,'Java','Demo1','62674139faae0a7eb10b68d5',0,3,32,'2022-04-26 00:47:53','2022-04-26 00:47:53',0),(86,22,'C','Demo2','62674154faae0a7eb10b68d8',1,3,33,'2022-04-26 00:48:21','2022-04-26 00:48:21',0),(87,23,'Spring','Demo3','62674186faae0a7eb10b68db',0,0,34,'2022-04-26 00:49:11','2022-04-26 00:49:11',0),(88,25,'Java','4','6267423ffaae0a7eb10b68df',16,159,36,'2022-04-26 00:52:16','2022-04-26 00:52:16',0),(89,24,'Java','getArticleVisitTop','62674300c6b3443fdb93a875',0,0,35,'2022-04-26 00:55:29','2022-04-26 00:55:29',0),(90,26,'Python','DemoDemo','62674340c6b3443fdb93a878',0,5,37,'2022-04-26 00:56:32','2022-04-26 00:56:32',0),(91,27,'python','DemoDemo','62674368c6b3443fdb93a87b',0,1,38,'2022-04-26 00:57:12','2022-04-26 00:57:12',0),(93,29,'eqde','fffff','6267b8e173dde90573059024',0,6,39,'2022-04-26 09:18:26','2022-04-26 09:18:26',0),(94,29,'小红帽和大灰狼的故事','---admin','6267c14a73dde9057305907d',0,1,40,'2022-04-26 09:54:18','2022-04-26 09:54:18',0),(95,29,'鸡汤来喽','啊哈哈哈哈哈','6267c32473dde905730590af',0,2,39,'2022-04-26 10:02:12','2022-04-26 10:02:12',0),(96,17,'标题','测试','626c8d502da7194c5cbdcf54',0,3,24,'2022-04-30 01:13:52','2022-04-30 01:13:52',0),(97,17,'Demo','Demo','626dd6e07dfe7c7a69a8ec29',0,1,25,'2022-05-01 00:40:01','2022-05-01 00:40:01',0);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` VALUES (24,17,'笔记'),(25,17,'面试总结'),(28,18,'Demo'),(29,19,'Demo'),(30,20,'Demo'),(32,21,'未命名1'),(33,22,'2'),(34,23,'未命名3'),(35,24,'未命名4'),(36,25,'未命名5'),(37,26,'getArticleVisitTop'),(38,27,'未命名6'),(39,29,'e'),(40,29,'x'),(77,17,'Demo');
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `id` varchar(128) NOT NULL,
  `user_id` int NOT NULL,
  `name` varchar(128) NOT NULL,
  `size` mediumtext NOT NULL,
  `md5` varchar(32) NOT NULL,
  `create_time` varchar(16) DEFAULT NULL,
  `is_private` tinyint(1) DEFAULT '1',
  `is_review` tinyint(1) DEFAULT '0',
  UNIQUE KEY `file_pk` (`id`),
  KEY `file_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES ('00000011130304BC6B496FE97E29A3AE67EC30670000018059B48DD7',17,'黄小琥 - 没那么简单.flac','33110286','130304bc6b496fe97e29a3ae67ec3067','2022-04-24 11:54',1,0),('0000001142BD293121C5D2131067A28ED9C681080000018054094E78',17,'1.333=42bd293121c5d2131067a28ed9c68108.jpg','4045301','42bd293121c5d2131067a28ed9c68108','2022-04-23 09:28',0,0),('000000116F2BE8786F5CA67345B145E4033D405B0000018054F0F444',17,'陈奕迅 - 放.flac','25039961','6f2be8786f5ca67345b145e4033d405b','2022-04-23 13:41',0,0),('00000011C257ADD9013A8BD1810C1DFDDA30532500000180540D1E95',17,'阿YueYue - 云与海.flac','27955183','c257add9013a8bd1810c1dfdda305325','2022-04-23 09:33',0,0),('00000011D1D8020C6D689D5D9E8798C43CAB10B400000180780799F5',17,'d1d8020c6d689d5d9e8798c43cab10b4','392837','d1d8020c6d689d5d9e8798c43cab10b4','2022-04-30 09:13',0,0),('00000011E50A2DB6124B743793CA978F6DFC19EC000001807D10033B',17,'G.E.M.邓紫棋 - 句号.flac','26556787','e50a2db6124b743793ca978f6dfc19ec','2022-05-01 08:40',1,0),('00000011E50A2DB6124B743793CA978F6DFC19EC000001807D103E54',17,'G.E.M.邓紫棋 - 句号.flac','26556787','e50a2db6124b743793ca978f6dfc19ec','2022-05-01 08:40',1,0),('000000124D12012236CFE273B0F39D21D627B2B30000018063C866F3',18,'Diana Boncheva - Beethoven Virus.flac','28026459','4d12012236cfe273b0f39d21d627b2b3','2022-04-26 10:51',1,0),('000000124D12012236CFE273B0F39D21D627B2B30000018063C87872',18,'Diana Boncheva - Beethoven Virus.flac','28026459','4d12012236cfe273b0f39d21d627b2b3','2022-04-26 10:52',1,0),('0000001277B48895FBECFD23D3E6AB03C59ABCF90000018063C7DC64',18,'Demo','1496775','77b48895fbecfd23d3e6ab03c59abcf9','2022-04-26 10:51',1,0),('0000001D0A04567D05CF1138A42BB1E2C9E954B900000180652E2735',29,'问卷调查html.rar','24887','0a04567d05cf1138a42bb1e2c9e954b9','2022-04-26 17:22',1,0),('0000001D4FD9FDF71C6D2AC7FF394799222A42CD00000180651B986A',29,'831582 (1).jpg','4812921','4fd9fdf71c6d2ac7ff394799222a42cd','2022-04-26 17:02',1,0),('0000001D68259315910E16F2B3DCB035D323FD2200000180650956E8',29,'工作分配方案.xlsx','10210','68259315910e16f2b3dcb035d323fd22','2022-04-26 16:42',1,0),('0000001DA42859B9562516CC2AD1F0F052EBA529000001806527B32A',29,'htmlpublisher.hpi','72832','a42859b9562516cc2ad1f0f052eba529','2022-04-26 17:15',0,0),('0000001DD0F517158EF299CFA457B9E6C9B8213C000001806512B46C',29,'Git-2.36.0-64-bit.exe','49371696','d0f517158ef299cfa457b9e6c9b8213c','2022-04-26 16:52',0,0);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good`
--

DROP TABLE IF EXISTS `good`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good` (
  `article_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good`
--

LOCK TABLES `good` WRITE;
/*!40000 ALTER TABLE `good` DISABLE KEYS */;
INSERT INTO `good` VALUES (88,17),(77,29),(92,17),(78,29),(86,17),(84,17);
/*!40000 ALTER TABLE `good` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `say`
--

DROP TABLE IF EXISTS `say`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `say` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `article_id` int DEFAULT NULL,
  `content` varchar(128) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `say`
--

LOCK TABLES `say` WRITE;
/*!40000 ALTER TABLE `say` DISABLE KEYS */;
INSERT INTO `say` VALUES (1,17,76,'Demo','2022-04-26 10:34:59'),(2,17,76,'Demo','2022-04-26 10:36:05'),(3,17,76,'Demo','2022-04-26 10:36:22'),(4,17,76,'这是评论','2022-04-26 10:46:21'),(5,17,76,'这还是评论','2022-04-26 10:46:57'),(6,18,76,'这是我的评论','2022-04-26 10:47:59'),(7,18,81,'这个文章那个真好','2022-04-26 10:50:17'),(8,18,81,'这个文章真和','2022-04-26 10:50:59'),(9,17,76,'dfasd','2022-04-26 10:59:15'),(10,17,88,'ping','2022-04-26 11:16:46'),(11,17,88,'Demo','2022-04-26 11:47:56'),(12,29,90,'hhhhh','2022-04-26 16:40:50'),(13,29,92,'lallalalala嗯哦亲我饿额顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶12鹅鹅鹅饿呃呃呃呃呃呃呃呃呃呃呃呃呃呃呃','2022-04-26 16:45:53'),(14,29,92,'1','2022-04-26 16:45:58'),(15,29,92,'   ','2022-04-26 16:46:00'),(16,29,92,'   ','2022-04-26 16:46:02'),(17,29,92,'  ','2022-04-26 16:46:07'),(18,29,76,'c测试','2022-04-26 17:14:03'),(19,17,77,'这个文章写的真好','2022-04-26 17:41:16'),(20,17,77,'真好','2022-04-26 17:41:22'),(21,17,77,'更好了','2022-04-26 17:41:27'),(22,29,88,'kkkk','2022-04-26 17:53:16'),(23,29,94,'好','2022-04-26 17:54:34'),(24,17,86,'Demo','2022-04-26 17:57:00'),(25,29,79,'好','2022-04-26 17:57:40'),(26,17,78,'Demo','2022-04-26 18:01:34'),(27,17,78,'Demo','2022-04-26 18:01:48'),(28,17,78,'zheshi1pinglun1','2022-04-26 18:01:55'),(29,29,84,'emo','2022-04-26 18:13:12'),(30,29,93,'ddd','2022-04-26 18:14:20'),(31,29,85,'hhhh','2022-04-26 18:14:52'),(32,17,90,'没有东西爱','2022-04-27 20:05:17'),(33,17,90,'为啥没有东西','2022-04-27 20:10:37'),(34,17,77,'😀 😃 😄 😁 😆 😅 😂 🤣 🥲 ','2022-04-27 20:12:38'),(35,17,78,'ceshi','2022-04-30 09:07:50'),(36,17,77,'全球全球','2022-05-01 08:36:26');
/*!40000 ALTER TABLE `say` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `avatar` varchar(64) DEFAULT NULL,
  `sign` varchar(64) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_email_uindex` (`email`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (17,'Cafebabae','Admin@123','498861397@qq.com','/userApi/avatar/get/f0cf178ec9dac880b147490e98284c4b','这是签名1111','2022-04-23 01:26:04',0),(18,'1','11111','111@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(19,'hahah','11111','112@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(20,'一号用户','11111','113@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(21,'二号用户','11111','114@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(22,'三号用户','11111','115@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(23,'四号用户','11111','116@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(24,'五号用户','11111','117@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(25,'六号用户','11111','118@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(26,'七号用户','11111','119@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(27,'八号用户','11111','1110@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(28,'九号用户','11111','1111@qq.com','/userApi/avatar/get/8305be4c40f3afb1be15318257be5bb3','Demo','2022-04-23 01:26:04',0),(29,'改名1了！1','123','1917947626@qq.com','/userApi/avatar/get/68ebe50ab4a28a9ae04d35ae602eaf4f','123456789009876','2022-04-26 08:40:44',0);
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

-- Dump completed on 2022-05-01  6:22:38
