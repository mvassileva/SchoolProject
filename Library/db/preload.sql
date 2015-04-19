-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	5.6.24-log

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
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,NULL,'1960-01-01 00:00:00',NULL,'Frank','Tsui'),(2,NULL,'1775-01-16 00:00:00','1817-01-18 00:00:00','Jane','Austen'),(3,NULL,'1926-01-28 00:00:00',NULL,'Harper','Lee'),(4,NULL,'1896-01-24 00:00:00','1940-01-21 00:00:00','F. Scott','Fitzgerald'),(5,NULL,'1816-01-21 00:00:00','1855-01-31 00:00:00','Charlotte','Bronte'),(6,NULL,'1903-01-25 00:00:00','1950-01-21 00:00:00','George','Orwell'),(7,NULL,'1919-01-01 00:00:00','2010-01-27 00:00:00','J.D.','Salinger'),(8,NULL,'1903-01-25 00:00:00','1950-01-21 00:00:00','George','Orwell'),(9,NULL,'1818-01-30 00:00:00','1848-01-19 00:00:00','Emily','Bronte'),(10,NULL,'1832-01-29 00:00:00','1888-01-06 00:00:00','Louisa May','Alcott'),(11,NULL,'1911-01-19 00:00:00','1993-01-19 00:00:00','William','Golding');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,NULL,NULL,14,NULL,'1813-01-01 00:00:00','A-123',1,'Pride and Prejudice'),(2,NULL,NULL,14,NULL,'1960-01-01 00:00:00','A-123',1,'To Kill a Mockingbird'),(3,NULL,NULL,14,NULL,'1925-01-01 00:00:00','A-124',1,'The Great Gatsby'),(4,NULL,NULL,14,NULL,'1847-01-02 00:00:00','A-125',1,'Jane Eyre'),(5,NULL,NULL,14,NULL,'1948-01-01 00:00:00','B-133',1,'1984'),(6,NULL,NULL,14,NULL,'1951-01-01 00:00:00','C-13',1,'The Catcher in the Rye'),(7,NULL,NULL,14,NULL,'1945-01-01 00:00:00','D-234',1,'Animal Farm'),(8,NULL,NULL,14,NULL,'1847-01-01 00:00:00','E-223',1,'Wuthering Heights'),(9,NULL,NULL,14,NULL,'1868-01-01 00:00:00','F-312',1,'Little Women'),(10,NULL,NULL,14,NULL,'1954-01-01 00:00:00','G-349',1,'Lord of the Flies');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_authors`
--

LOCK TABLES `book_authors` WRITE;
/*!40000 ALTER TABLE `book_authors` DISABLE KEYS */;
INSERT INTO `book_authors` VALUES (1,2),(2,3),(3,4),(4,5),(5,6),(6,7),(7,8),(8,9),(9,10),(10,11);
/*!40000 ALTER TABLE `book_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_waiting_users`
--

LOCK TABLES `book_waiting_users` WRITE;
/*!40000 ALTER TABLE `book_waiting_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_waiting_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'\0',0,NULL,NULL,NULL,NULL,0,'9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08',NULL,'test1'),(2,'',12,'1963-01-11 00:00:00','test12@admin.com','Bob','Jones',0,'a98ec5c5044800c88e862f007b98d89815fc40ca155d6ce7909530d792e909ce',0,'test12'),(3,'',4,'1983-01-29 00:00:00','test12@patron.com','Stephen','Jones',2,'9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08',4,'sJones'),(4,'',8,'1981-01-29 00:00:00','test12@librarian.com','Stephen','Brown',0,'9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08',1,'sBrown');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users_books`
--

LOCK TABLES `users_books` WRITE;
/*!40000 ALTER TABLE `users_books` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_books` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-19 11:31:39
