-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: mysql.cs.bangor.ac.uk    Database: eeu918
-- ------------------------------------------------------
-- Server version	5.6.35

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
-- Table structure for table `TEST`
--

DROP TABLE IF EXISTS `TEST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TEST` (
  `TestID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) DEFAULT NULL,
  `Mark` int(3) NOT NULL,
  `TestDate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TestID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TEST`
--

LOCK TABLES `TEST` WRITE;
/*!40000 ALTER TABLE `TEST` DISABLE KEYS */;
/*!40000 ALTER TABLE `TEST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(8) NOT NULL,
  `Password` varchar(60) NOT NULL,
  `UserType` enum('STUDENT','INSTRUCTOR','ADMINISTRATOR') NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WORD`
--

DROP TABLE IF EXISTS `WORD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WORD` (
  `WordID` int(11) NOT NULL AUTO_INCREMENT,
  `English` varchar(25) NOT NULL,
  `Welsh` varchar(25) NOT NULL,
  `WelshGender` varchar(25) NOT NULL,
  PRIMARY KEY (`WordID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WORD`
--

LOCK TABLES `WORD` WRITE;
/*!40000 ALTER TABLE `WORD` DISABLE KEYS */;
INSERT INTO `WORD` VALUES (1,'man','dyn','male'),(2,'man','dyn','male'),(3,'father','tad','male'),(4,'brother','brawd','male'),(5,'uncle','ewythr','male'),(6,'bull','tarw','male'),(7,'horse','ceffyl','male'),(8,'rooster','ceiliog','male'),(9,'ram','maharen','male'),(10,'dog','ci','male'),(11,'son','mab','male'),(12,'grandfather','tad-cu','male'),(13,'christmas','y nadolig','male'),(14,'easter','y pasg','male'),(15,'rain','glaw','male'),(16,'snow','eira','male'),(17,'fire','tan','male'),(18,'coffee','coffi','male'),(19,'beer','cwrw','male'),(20,'husband','gwr','male'),(22,'woman','dynes','female'),(23,'daughter','merch','female'),(24,'girl','merch','female'),(25,'mother','mam','female'),(26,'cow','buwch','female'),(27,'sister','buwch','female'),(28,'mare','caseg','female'),(29,'ewe','mamog','female'),(30,'hen','iar','female'),(31,'army','byddin','female'),(32,'wife','gwraig','female'),(33,'female teacher','athrawes','female'),(34,'easter','y pasg','female'),(35,'oak tree','derwen','female'),(36,'birch tree','bedwen','female'),(37,'snowden','yr wyddfa','female'),(38,'dress','ffrog','female'),(39,'glove','maneg','female'),(40,'nile river','nil','female');
/*!40000 ALTER TABLE `WORD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'eeu918'
--

--
-- Dumping routines for database 'eeu918'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-20  0:46:36
