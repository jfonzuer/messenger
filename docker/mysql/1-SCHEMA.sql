CREATE DATABASE  IF NOT EXISTS `messenger` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `messenger`;
-- MySQL dump 10.13  Distrib 5.7.18, for Linux (x86_64)
--
-- Host: localhost    Database: messenger
-- ------------------------------------------------------
-- Server version	5.7.18-0ubuntu0.16.04.1

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
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK61s10j47qhdc5eud84utvh8pr` (`country_id`),
  CONSTRAINT `FK61s10j47qhdc5eud84utvh8pr` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'Alsace',1),(2,'Aquitaine',1),(3,'Auvergne',1),(4,'Basse Normandie',1),(5,'Bourgogne',1),(6,'Bretagne',1),(7,'Centre',1),(8,'Champagne-Ardenne',1),(9,'Corse',1),(10,'Franche-Comté',1),(11,'Haute-Normandie',1),(12,'Ile-de-France',1),(13,'Languedoc-Roussilon',1),(14,'Limousin',1),(15,'Lorraine',1),(16,'Midi-Pyrénées',1),(17,'Nord-Pas-de-Calais',1),(18,'Pays de la Loire',1),(19,'Picardie',1),(20,'Poitou-Charentes',1),(21,'Provence-Alpes-Côtes-d\'Azur',1),(22,'Guadeloupe',1),(23,'Martinique',1),(24,'Guyane',1),(25,'La Réunion',1),(26,'Mayotte',1),(27,'Anvers',2),(28,'Brabant',2),(29,'Flamande',2),(30,'Flandre Occidentale',2),(31,'Flandre Orientale',2),(32,'Hainaut',2),(33,'Liège',2),(34,'Limbourg',2),(35,'Luxembourg',2),(36,'Namur',2),(37,'Guttland',3),(38,'Oesling',3),(39,'Appenzell',4),(40,'Argovie',4),(41,'Bâle',4),(42,'Berne',4),(43,'Fribourg',4),(44,'Genève',4),(45,'Glaris',4),(46,'Grison',4),(47,'Jura',4),(48,'Lucerne',4),(49,'Neufchâtel',4),(50,'Nidwald',4),(51,'Obwald',4),(52,'Saint-Gall',4),(53,'Schaffhouse',4),(54,'Schwytz',4),(55,'Soleure',4),(56,'Tessin',4),(57,'Thurgovie',4),(58,'Uri',4),(59,'Valais',4),(60,'Vaud',4),(61,'Zoug',4),(62,'Zurich',4);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation`
--

DROP TABLE IF EXISTS `conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conversation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_deleted_by_user_one` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted_by_user_two` tinyint(1) NOT NULL DEFAULT '0',
  `is_read_by_user_one` tinyint(1) NOT NULL DEFAULT '0',
  `is_read_by_user_two` tinyint(1) NOT NULL DEFAULT '0',
  `last_modified` datetime DEFAULT NULL,
  `preview` varchar(255) DEFAULT NULL,
  `user_one_id` bigint(20) DEFAULT NULL,
  `user_two_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfef3bcwvdyf054tr06g00e3fd` (`user_one_id`),
  KEY `FK427vf1uhow5x2fm5qkbgf113l` (`user_two_id`),
  CONSTRAINT `FK427vf1uhow5x2fm5qkbgf113l` FOREIGN KEY (`user_two_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKfef3bcwvdyf054tr06g00e3fd` FOREIGN KEY (`user_one_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
INSERT INTO `conversation` VALUES (1,0,0,0,0,'2017-05-21 14:33:41','conversation 1',1,2),(2,0,0,0,0,'2000-04-02 09:30:00','conversation 2',1,3);
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flag` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'fr','France'),(2,'be','Belgique'),(3,'lu','Luxembourg'),(4,'ch','Suisse');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fetish`
--

DROP TABLE IF EXISTS `fetish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fetish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fetish`
--

LOCK TABLES `fetish` WRITE;
/*!40000 ALTER TABLE `fetish` DISABLE KEYS */;
INSERT INTO `fetish` VALUES (1,'Ballbusting'),(2,'Bondage'),(3,'Chasteté'),(4,'Cuckolding'),(5,'Face-sitting'),(6,'Féminisation'),(7,'Foot-worship'),(8,'Gode-ceinture'),(9,'Humiliation'),(10,'Scato'),(11,'Uro'),(12,'Whipping & Caning');
/*!40000 ALTER TABLE `fetish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_number` int(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlxnnh0ir05khn8iu9tgwh1yyk` (`user_id`),
  CONSTRAINT `FKlxnnh0ir05khn8iu9tgwh1yyk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,1,'profile.png',1),(2,1,'profile.png',2),(3,1,'profile.png',3),(4,1,'profile.png',4);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `is_deleted_by_user_one` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted_by_user_two` tinyint(1) NOT NULL DEFAULT '0',
  `sent_date_time` datetime NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `conversation_id` bigint(20) DEFAULT NULL,
  `source_id` bigint(20) DEFAULT NULL,
  `user_one_id` bigint(20) DEFAULT NULL,
  `user_two_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6yskk3hxw5sklwgi25y6d5u1l` (`conversation_id`),
  KEY `FK1940g3yb2gdgrpwlrfutunvl2` (`source_id`),
  KEY `FK9mut02090s7weouc0l17gov9p` (`user_one_id`),
  KEY `FKip8kh8gdpj9fn9l75f0mmc77c` (`user_two_id`),
  CONSTRAINT `FK1940g3yb2gdgrpwlrfutunvl2` FOREIGN KEY (`source_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK6yskk3hxw5sklwgi25y6d5u1l` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  CONSTRAINT `FK9mut02090s7weouc0l17gov9p` FOREIGN KEY (`user_one_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKip8kh8gdpj9fn9l75f0mmc77c` FOREIGN KEY (`user_two_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'Salut',0,0,'2017-05-21 14:33:41','TEXT',NULL,1,1,1,2),(2,'Salut ça va ?',0,0,'2017-05-21 14:33:41','TEXT',NULL,1,2,1,2),(3,'Parfait et toi gros frère ?',0,0,'2017-05-21 14:33:41','TEXT',NULL,1,1,1,2),(4,'Parfaitement oklm bro ',0,0,'2017-05-21 14:33:41','TEXT',NULL,1,2,1,2);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expiry_date` date DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id`),
  CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activated` bit(1) NOT NULL,
  `birth_date` date NOT NULL,
  `description` text NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `is_blocked` bit(1) NOT NULL,
  `last_activity_datetime` datetime NOT NULL,
  `last_password_reset_date` datetime NOT NULL,
  `last_report_date` date NOT NULL,
  `last_subscription_check` datetime DEFAULT NULL,
  `notify_message` bit(1) NOT NULL,
  `notify_visit` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `reported_as_fake` bigint(20) NOT NULL,
  `subscription_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `area_id` bigint(20) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `last_message_by_id` bigint(20) DEFAULT NULL,
  `last_visited_by_id` bigint(20) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK952enf48r0kesdubmttrmntw` (`area_id`),
  KEY `FKge8lxibk9q3wf206s600otk61` (`country_id`),
  KEY `FKn0c37h9ymn1hruh4171nqvpdb` (`last_message_by_id`),
  KEY `FKhak4bnquslu1x4byb0rcims3h` (`last_visited_by_id`),
  KEY `FKckj8l43x0udru7w2hrk3no9mv` (`type_id`),
  CONSTRAINT `FK952enf48r0kesdubmttrmntw` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`),
  CONSTRAINT `FKckj8l43x0udru7w2hrk3no9mv` FOREIGN KEY (`type_id`) REFERENCES `user_type` (`id`),
  CONSTRAINT `FKge8lxibk9q3wf206s600otk61` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FKhak4bnquslu1x4byb0rcims3h` FOREIGN KEY (`last_visited_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKn0c37h9ymn1hruh4171nqvpdb` FOREIGN KEY (`last_message_by_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'','1990-01-01','je suis le membre 1','jack.fonzuer@yahoo.fr','','\0','2017-05-21 14:33:41','2017-05-20 10:47:01','2017-05-20',NULL,'','','$2a$10$vIzlHP6bYc307rxp3JznWOaVaBADBdRsLbgncjDd6g/Y76nY7o8Nu',0,NULL,'jack',1,1,NULL,NULL,1),(2,'','1988-03-29','je suis le membre 2','u1@gmail.com','','\0','2017-05-21 14:33:41','2017-05-20 10:47:01','2017-05-20',NULL,'','','$2a$10$CDnsTAxWVjomexja.nK/feEghPnAlMMkLMOWZaAoNo/2LNgQxitB2',10,NULL,'user2',28,2,NULL,NULL,2),(3,'','1988-01-01','je suis le membre 3','u2@gmail.com','','\0','2017-05-21 14:33:41','2017-05-20 10:47:01','2017-05-20',NULL,'','','$2a$10$fjiGp75NgY17F5u1RejkjeLyJrcIQmAffFpYY6WFVxWLYnlAF97my',15,NULL,'member3',37,3,NULL,NULL,1),(4,'','1988-01-01','je suis le membre 4','u3@gmail.com','','\0','2017-05-21 14:33:41','2017-05-20 10:47:01','2017-05-20','2017-05-20 14:33:41','','','$2a$10$PCMh39VYOFCnRILk2ukzhu6KbsW67Z390mG1uR/1FS1TSJ2MWR6Gq',20,'Hr551C2QDhJT4baIQ','member4',1,1,NULL,NULL,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_blocked_users`
--

DROP TABLE IF EXISTS `user_blocked_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_blocked_users` (
  `user_id` bigint(20) NOT NULL,
  `blocked_users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`blocked_users_id`),
  KEY `FKdalh7bfc8m46286xk6cpmkj9c` (`blocked_users_id`),
  CONSTRAINT `FKdalh7bfc8m46286xk6cpmkj9c` FOREIGN KEY (`blocked_users_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrfu5nmyf90ym7iv09rvduv86p` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_blocked_users`
--

LOCK TABLES `user_blocked_users` WRITE;
/*!40000 ALTER TABLE `user_blocked_users` DISABLE KEYS */;
INSERT INTO `user_blocked_users` VALUES (1,2);
/*!40000 ALTER TABLE `user_blocked_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_fetishes`
--

DROP TABLE IF EXISTS `user_fetishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_fetishes` (
  `user_id` bigint(20) NOT NULL,
  `fetishes_id` bigint(20) NOT NULL,
  KEY `FK7wj5sxlp1all0a2xv3oawlvx0` (`fetishes_id`),
  KEY `FKpireh9y3q5qmisj1b51oqas74` (`user_id`),
  CONSTRAINT `FK7wj5sxlp1all0a2xv3oawlvx0` FOREIGN KEY (`fetishes_id`) REFERENCES `fetish` (`id`),
  CONSTRAINT `FKpireh9y3q5qmisj1b51oqas74` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_fetishes`
--

LOCK TABLES `user_fetishes` WRITE;
/*!40000 ALTER TABLE `user_fetishes` DISABLE KEYS */;
INSERT INTO `user_fetishes` VALUES (1,1),(1,1);
/*!40000 ALTER TABLE `user_fetishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'ROLE_USER',1),(2,'ROLE_ADMIN',1),(3,'ROLE_PREMIUM',1),(4,'ROLE_USER',2),(5,'ROLE_USER',3),(6,'ROLE_USER',4),(7,'ROLE_PREMIUM',4);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_type` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'Dominatrice'),(2,'Soumis');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visit`
--

DROP TABLE IF EXISTS `visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_seen_by_visited` tinyint(1) NOT NULL DEFAULT '0',
  `visited_date` date NOT NULL,
  `visited_id` bigint(20) DEFAULT NULL,
  `visitor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgpafdy45fcmduhn988jgsyr4p` (`visited_id`),
  KEY `FK4ctwq895n2xk7vvdqfcqu28w9` (`visitor_id`),
  CONSTRAINT `FK4ctwq895n2xk7vvdqfcqu28w9` FOREIGN KEY (`visitor_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKgpafdy45fcmduhn988jgsyr4p` FOREIGN KEY (`visited_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit`
--

LOCK TABLES `visit` WRITE;
/*!40000 ALTER TABLE `visit` DISABLE KEYS */;
INSERT INTO `visit` VALUES (1,0,'2017-05-21',1,2),(2,0,'2017-05-21',1,3),(3,0,'2017-05-21',1,2);
/*!40000 ALTER TABLE `visit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-21 14:36:14
