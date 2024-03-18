-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shoppingcart
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `description` varchar(255) NOT NULL,
  `settingId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_contact_user` (`userId`),
  KEY `FK_contact_setting` (`settingId`),
  CONSTRAINT `FK_contact_setting` FOREIGN KEY (`settingId`) REFERENCES `setting` (`id`),
  CONSTRAINT `FK_contact_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedbacks`
--

DROP TABLE IF EXISTS `feedbacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedbacks` (
  `id` int NOT NULL,
  `pId` int NOT NULL,
  `userId` int NOT NULL,
  `description` varchar(2000) NOT NULL,
  `rate` int NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_FeedBacks_product` (`pId`),
  KEY `FK_FeedBacks_user` (`userId`),
  KEY `FK_feedbacks_status` (`status`),
  CONSTRAINT `FK_FeedBacks_product` FOREIGN KEY (`pId`) REFERENCES `product` (`pid`),
  CONSTRAINT `FK_feedbacks_status` FOREIGN KEY (`status`) REFERENCES `setting` (`id`),
  CONSTRAINT `FK_FeedBacks_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedbacks`
--

LOCK TABLES `feedbacks` WRITE;
/*!40000 ALTER TABLE `feedbacks` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedbacks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetail`
--

DROP TABLE IF EXISTS `orderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetail` (
  `odId` varchar(50) NOT NULL,
  `orderId` varchar(45) DEFAULT NULL,
  `productId` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`odId`),
  KEY `FK_orderDetail_orders` (`orderId`),
  KEY `FK_orderDetail_product1` (`productId`),
  CONSTRAINT `FK_orderDetail_orders` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderid`),
  CONSTRAINT `FK_orderDetail_product1` FOREIGN KEY (`productId`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetail`
--

LOCK TABLES `orderdetail` WRITE;
/*!40000 ALTER TABLE `orderdetail` DISABLE KEYS */;
INSERT INTO `orderdetail` VALUES ('OD123','ORD123',9,2,100.00),('OD124','ORD123',5,1,75.00),('OD125','ORD124',6,3,150.00),('OD126','ORD125',7,1,25.00),('OD127','ORD126',8,2,300.00);
/*!40000 ALTER TABLE `orderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderid` varchar(45) NOT NULL,
  `time` datetime NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateBy` int DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `payment` varchar(50) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`orderid`),
  KEY `fk_userId` (`userId`),
  KEY `FK_orders_status` (`status`),
  CONSTRAINT `FK_orders_status` FOREIGN KEY (`status`) REFERENCES `setting` (`id`),
  CONSTRAINT `fk_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('ORD123','2024-03-11 17:00:00','user1@example.com','2024-03-11 17:05:00',6,7,50.00,'1234567890','123 Street, City','Credit Card',9),('ORD124','2024-03-11 17:15:00','user2@example.com','2024-03-11 17:20:00',6,8,75.00,'2345678901','456 Avenue, Town','PayPal',9),('ORD125','2024-03-11 17:30:00','user3@example.com','2024-03-11 17:35:00',6,9,100.00,'3456789012','789 Road, Village','Cash on Delivery',10),('ORD126','2024-03-11 17:45:00','user4@example.com','2024-03-11 17:50:00',6,7,25.00,'4567890123','987 Lane, Countryside','Credit Card',11),('ORD127','2024-03-11 18:00:00','user5@example.com','2024-03-11 18:05:00',6,8,150.00,'5678901234','654 Boulevard, Suburb','PayPal',8);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `pname` varchar(100) NOT NULL,
  `pinfo` varchar(350) DEFAULT NULL,
  `pprice` decimal(12,2) DEFAULT NULL,
  `pquantity` int DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `sale` decimal(2,2) DEFAULT NULL,
  `settingId` int NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `FK_product_setting` (`settingId`),
  KEY `fk_status_idx` (`status`),
  CONSTRAINT `FK_product_setting` FOREIGN KEY (`settingId`) REFERENCES `setting` (`id`),
  CONSTRAINT `fk_status` FOREIGN KEY (`status`) REFERENCES `setting` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (5,'T-Shirt','Cotton T-Shirt',20.00,100,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/309816/xiaomi-13t-pro-xanh-thumb-600x600.jpg',0.10,7,13),(6,'Sneakers','Leather Sneakers',80.00,30,'http://localhost:9999/SourceShop/images/1710168682668_acer-aspire-7-gaming600x600.jpg',0.00,6,12),(7,'Sneakers','Leather Sneakers',80.00,30,'http://localhost:9999/SourceShop/ProductController',0.00,5,12),(8,'Dress Shirt','Cotton Dress Shirt',40.00,75,'http://localhost:9999/SourceShop/ProductController',0.00,5,12),(9,'Skirt','Polyester Skirt',35.00,60,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/60/290326/op-lung-magsafe-iphone-14-pro-max-nhua-trong-apple-mpu73-thumb-600x600.jpg',0.00,6,12),(10,'Jacket','Leather Jacket',100.00,25,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/60/290326/op-lung-magsafe-iphone-14-pro-max-nhua-trong-apple-mpu73-thumb-600x600.jpg',0.00,7,12),(11,'Dress','Silk Dress',70.00,40,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/7264/231852/elio-el028-02-nu-thumb-fix-600x600.jpg',0.10,6,12),(12,'Blouse','Cotton Blouse',30.00,45,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/311120/vivo-y36-xanh-thumbnew-600x600.jpg',0.10,5,12),(13,'Shorts','Denim Shorts',25.00,80,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/44/288199/lenovo-ideapad-5-pro-14iap7-i7-82sh002svn-ab-thumb-600x600.jpg',0.10,7,12),(14,'Sweater','Wool Sweater',60.00,55,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/7077/306530/befit-watch-fit-hong-tn-600x600.jpg',0.10,3,12),(15,'Jeans',' aaaaaaaaaaaaaaa',5011.00,50,'http://localhost:9999/SourceShop/images/1710168509914_acer-aspire-7-gaming600x600.jpg',0.00,6,12),(16,'Sneakers','Leather Sneakers',80.00,30,'http://localhost:9999/SourceShop/images/1710168682668_acer-aspire-7-gaming600x600.jpg',0.00,6,12);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES (1,'Admin','Role','Active'),(2,'Customer','Role','Active'),(3,'Mobile','Category','Active'),(5,'TV','Category','Active'),(6,'Ipad','Category','Active'),(7,'Laptop','Category','Active'),(8,'New','Status','Active'),(9,'Cancelled','Status','Active'),(10,'Shipping','Status','Active'),(11,'Paid','Status','Active'),(12,'Active','Product','Active'),(13,'InActive','Product','Active'),(14,'Samsung','Category','Active');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `name` varchar(50) NOT NULL,
  `mobile` bigint NOT NULL,
  `address` varchar(250) NOT NULL,
  `pincode` int NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` int NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_role_idx` (`role`),
  KEY `FK_user_status` (`status`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role`) REFERENCES `setting` (`id`),
  CONSTRAINT `FK_user_status` FOREIGN KEY (`status`) REFERENCES `setting` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (6,'user1@example.com','User 1',1234567890,'123 Street, City',12345,'password1',1,12),(7,'user2@example.com','User 2',2345678901,'456 Avenue, Town',23456,'password2',2,12),(8,'user3@example.com','User 3',3456789012,'789 Road, Village',34567,'password3',2,12),(9,'user4@example.com','User 4',4567890123,'987 Lane, Countryside',45678,'password4',2,12);
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

-- Dump completed on 2024-03-13 20:16:38
