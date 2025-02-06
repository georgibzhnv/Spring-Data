-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: football
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coaches`
--

DROP TABLE IF EXISTS `coaches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coaches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `salary` decimal(10,2) NOT NULL DEFAULT '0.00',
  `coach_level` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coaches`
--

LOCK TABLES `coaches` WRITE;
/*!40000 ALTER TABLE `coaches` DISABLE KEYS */;
INSERT INTO `coaches` VALUES (1,'Lionel','Messi',400000.00,6),(2,'Cristiano','Ronaldo',380000.00,9),(3,'Sergio','Ramos',240000.00,6);
/*!40000 ALTER TABLE `coaches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `countries` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` VALUES (1,'USA'),(2,'Germany'),(3,'Brazil'),(4,'France'),(5,'Italy'),(6,'Spain'),(7,'Argentina'),(8,'England'),(9,'Japan'),(10,'Canada');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `age` int NOT NULL,
  `position` char(1) NOT NULL,
  `salary` decimal(10,2) NOT NULL,
  `hire_date` datetime NOT NULL,
  `skills_data_id` int NOT NULL,
  `team_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_skills_players` (`skills_data_id`),
  KEY `fk_teams_players` (`team_id`),
  CONSTRAINT `fk_skills_players` FOREIGN KEY (`skills_data_id`) REFERENCES `skills_data` (`id`),
  CONSTRAINT `fk_teams_players` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (1,'John','Doe',28,'F',120000.50,'2015-06-20 10:00:00',1,1),(2,'Thomas','Müller',32,'M',150000.75,'2010-04-15 12:00:00',2,2),(3,'Carlos','Silva',25,'D',90000.00,'2020-01-10 08:30:00',3,3),(4,'Lionel','Messi',36,'F',200000.00,'2004-10-16 15:00:00',4,7),(5,'Cristiano','Ronaldo',39,'F',190000.00,'2003-08-12 17:00:00',5,6),(6,'Neymar','Jr.',31,'F',180000.00,'2013-06-03 12:30:00',6,4),(7,'Sergio','Ramos',37,'D',120000.00,'2005-09-01 10:15:00',7,6),(8,'Kylian','Mbappe',25,'F',150000.00,'2016-12-01 16:45:00',8,4),(9,'Harry','Kane',30,'F',130000.00,'2011-04-10 14:20:00',9,8),(10,'Alphonso','Davies',23,'D',110000.00,'2019-01-05 11:00:00',10,10);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players_coaches`
--

DROP TABLE IF EXISTS `players_coaches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players_coaches` (
  `player_id` int DEFAULT NULL,
  `coach_id` int DEFAULT NULL,
  KEY `fk_players_coaches` (`player_id`),
  KEY `fk_coaches_players` (`coach_id`),
  CONSTRAINT `fk_coaches_players` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`id`),
  CONSTRAINT `fk_players_coaches` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players_coaches`
--

LOCK TABLES `players_coaches` WRITE;
/*!40000 ALTER TABLE `players_coaches` DISABLE KEYS */;
/*!40000 ALTER TABLE `players_coaches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills_data`
--

DROP TABLE IF EXISTS `skills_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dribbling` int NOT NULL,
  `pace` int NOT NULL,
  `passing` int NOT NULL,
  `shooting` int NOT NULL,
  `speed` int NOT NULL,
  `strenght` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills_data`
--

LOCK TABLES `skills_data` WRITE;
/*!40000 ALTER TABLE `skills_data` DISABLE KEYS */;
INSERT INTO `skills_data` VALUES (1,85,80,78,79,82,80),(2,90,85,88,84,86,78),(3,75,70,65,73,74,85),(4,88,84,83,86,89,76),(5,70,78,72,68,77,82),(6,95,88,92,89,94,85),(7,60,65,58,62,64,70),(8,82,87,85,83,88,81),(9,78,80,77,79,81,79),(10,65,75,68,70,73,74);
/*!40000 ALTER TABLE `skills_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stadiums`
--

DROP TABLE IF EXISTS `stadiums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stadiums` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `capacity` int NOT NULL,
  `town_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_towns_stadiums` (`town_id`),
  CONSTRAINT `fk_towns_stadiums` FOREIGN KEY (`town_id`) REFERENCES `towns` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stadiums`
--

LOCK TABLES `stadiums` WRITE;
/*!40000 ALTER TABLE `stadiums` DISABLE KEYS */;
INSERT INTO `stadiums` VALUES (1,'MetLife Stadium',82500,1),(2,'Olympiastadion',74000,4),(3,'Maracanã',78838,3),(4,'Parc des Princes',48000,5),(5,'Stadio Olimpico',70000,6),(6,'Santiago Bernabéu',81000,7),(7,'La Bombonera',49000,8),(8,'Wembley',90000,9),(9,'Tokyo Dome',55000,9),(10,'BMO Field',30000,10);
/*!40000 ALTER TABLE `stadiums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teams` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `established` date NOT NULL,
  `fan_base` bigint NOT NULL DEFAULT '0',
  `stadium_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_teams_stadiums` (`stadium_id`),
  CONSTRAINT `fk_teams_stadiums` FOREIGN KEY (`stadium_id`) REFERENCES `stadiums` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,'Giants','1925-08-01',5000000,1),(2,'Bayern Munich','1900-02-27',10000000,2),(3,'Flamengo','1895-11-17',7000000,3),(4,'PSG','1970-08-12',8000000,4),(5,'AS Roma','1927-07-22',6000000,5),(6,'Real Madrid','1902-03-06',12000000,6),(7,'Boca Juniors','1905-04-03',9000000,7),(8,'Arsenal','1886-10-01',8500000,8),(9,'Tokyo Tigers','1955-04-18',4000000,9),(10,'Toronto FC','2006-05-11',3000000,10);
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `towns`
--

DROP TABLE IF EXISTS `towns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `towns` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `country_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_countries_towns` (`country_id`),
  CONSTRAINT `fk_countries_towns` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `towns`
--

LOCK TABLES `towns` WRITE;
/*!40000 ALTER TABLE `towns` DISABLE KEYS */;
INSERT INTO `towns` VALUES (1,'New York',1),(2,'Berlin',2),(3,'Rio de Janeiro',3),(4,'Munich',2),(5,'Paris',4),(6,'Rome',5),(7,'Madrid',6),(8,'Buenos Aires',7),(9,'London',8),(10,'Toronto',10);
/*!40000 ALTER TABLE `towns` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-02 17:03:22
