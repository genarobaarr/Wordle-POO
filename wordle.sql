-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: wordle
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `victories` int DEFAULT '0',
  `defeats` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin123',0,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `words`
--

DROP TABLE IF EXISTS `words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `words` (
  `id` int NOT NULL AUTO_INCREMENT,
  `word` varchar(5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `words_UNIQUE` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `words`
--

LOCK TABLES `words` WRITE;
/*!40000 ALTER TABLE `words` DISABLE KEYS */;
INSERT INTO `words` VALUES (44,'abajo'),(73,'abeto'),(236,'abono'),(508,'abrir'),(74,'actor'),(525,'adobo'),(75,'aguas'),(76,'agudo'),(499,'ahora'),(77,'alado'),(78,'albas'),(313,'alega'),(364,'almas'),(79,'altar'),(521,'altas'),(522,'altos'),(524,'amiga'),(1,'amigo'),(531,'antes'),(520,'apaga'),(519,'apago'),(518,'apego'),(534,'apoyo'),(292,'araña'),(241,'arena'),(370,'arete'),(452,'armas'),(375,'asnos'),(80,'atizo'),(81,'avala'),(527,'ayuda'),(82,'bache'),(281,'bafle'),(83,'balas'),(477,'balsa'),(395,'banca'),(51,'banda'),(61,'barco'),(161,'barro'),(450,'batir'),(507,'beber'),(84,'bicho'),(314,'bingo'),(85,'bizco'),(13,'blusa'),(404,'bocas'),(419,'bolas'),(420,'bolos'),(480,'bolsa'),(481,'bolso'),(478,'botas'),(26,'brazo'),(318,'breve'),(343,'brote'),(502,'buche'),(86,'buena'),(19,'bueno'),(403,'buque'),(87,'busca'),(88,'cabra'),(89,'cajas'),(90,'calar'),(91,'calca'),(526,'caldo'),(532,'calle'),(293,'calma'),(105,'calor'),(103,'calvo'),(470,'camas'),(92,'camba'),(469,'cameo'),(25,'campo'),(495,'canto'),(93,'caras'),(485,'carga'),(484,'cargo'),(42,'carro'),(62,'carta'),(472,'casar'),(22,'casas'),(94,'catar'),(474,'cavar'),(158,'cazar'),(368,'cebra'),(95,'cejas'),(41,'cerdo'),(297,'cerro'),(453,'chapa'),(233,'chavo'),(96,'chile'),(101,'china'),(511,'choco'),(97,'ciego'),(7,'cielo'),(324,'cifra'),(201,'cinco'),(98,'cines'),(99,'cisne'),(100,'citas'),(107,'clara'),(108,'claro'),(102,'clavo'),(242,'cobre'),(363,'coche'),(390,'colar'),(116,'colas'),(106,'colon'),(382,'color'),(471,'comas'),(326,'comer'),(104,'coral'),(109,'corea'),(110,'corro'),(304,'corto'),(111,'cosas'),(392,'coser'),(112,'costo'),(305,'crema'),(115,'cruda'),(113,'crudo'),(423,'cubos'),(55,'cuero'),(114,'curar'),(117,'dados'),(118,'dagas'),(120,'daños'),(268,'dardo'),(442,'darse'),(119,'datos'),(123,'decir'),(243,'dedos'),(121,'dejar'),(122,'denso'),(310,'dicha'),(307,'dicho'),(355,'disco'),(238,'doble'),(398,'doler'),(401,'donde'),(394,'dorar'),(483,'dotar'),(124,'dotes'),(498,'duelo'),(362,'dulce'),(125,'dunas'),(353,'durar'),(126,'duros'),(128,'echos'),(127,'ellos'),(244,'enano'),(129,'enoja'),(312,'enojo'),(323,'entra'),(322,'entre'),(354,'erizo'),(130,'error'),(255,'esqui'),(294,'extra'),(131,'fallo'),(476,'falsa'),(475,'falso'),(133,'falto'),(269,'fango'),(20,'feliz'),(132,'feria'),(435,'fideo'),(321,'final'),(134,'finca'),(425,'finos'),(5,'flama'),(308,'flujo'),(245,'focas'),(399,'fonda'),(376,'forma'),(63,'fresa'),(17,'fruta'),(388,'fruto'),(295,'fuego'),(358,'fumar'),(135,'gafas'),(136,'galas'),(237,'gallo'),(137,'ganas'),(290,'ganso'),(138,'gases'),(3,'gatos'),(246,'gente'),(27,'girar'),(342,'globo'),(510,'golpe'),(365,'gomas'),(36,'gordo'),(139,'gorro'),(254,'grano'),(140,'grave'),(141,'grita'),(235,'guano'),(316,'guias'),(317,'guion'),(344,'habas'),(142,'hacer'),(145,'hasta'),(143,'heces'),(428,'hecho'),(247,'herir'),(144,'hielo'),(449,'hijas'),(448,'hijos'),(64,'hojas'),(500,'horas'),(430,'horno'),(400,'hotel'),(464,'hueco'),(45,'huevo'),(28,'humor'),(311,'humos'),(256,'ideal'),(147,'ideas'),(146,'india'),(65,'islas'),(288,'jabon'),(289,'jamon'),(332,'jarra'),(345,'jarro'),(424,'jaspe'),(270,'jaula'),(148,'jefas'),(149,'jerga'),(66,'joven'),(257,'juego'),(29,'jugar'),(150,'julio'),(240,'karma'),(333,'kilos'),(258,'koala'),(380,'ladra'),(30,'largo'),(496,'latas'),(497,'latir'),(379,'lavar'),(46,'leche'),(271,'lejos'),(248,'lento'),(325,'letra'),(432,'libra'),(259,'libre'),(4,'libro'),(334,'lirio'),(372,'lista'),(371,'listo'),(58,'llama'),(50,'llave'),(516,'lotes'),(10,'luces'),(451,'lugar'),(283,'lunar'),(301,'lunes'),(447,'macho'),(446,'madre'),(249,'magia'),(406,'malas'),(151,'malos'),(11,'mango'),(505,'manos'),(336,'manta'),(335,'manto'),(456,'marca'),(59,'marea'),(157,'matar'),(486,'mejor'),(152,'menos'),(31,'mesas'),(416,'metal'),(153,'metro'),(396,'milla'),(492,'misas'),(306,'modos'),(57,'mojar'),(154,'moler'),(272,'momia'),(155,'monte'),(156,'morir'),(408,'motel'),(454,'mover'),(39,'mujer'),(260,'mundo'),(159,'nacer'),(160,'nadar'),(331,'nariz'),(163,'naves'),(164,'necio'),(373,'negro'),(38,'nieve'),(32,'niñez'),(273,'ninja'),(40,'niños'),(261,'nivel'),(12,'noche'),(340,'norte'),(162,'notas'),(67,'nubes'),(202,'nueve'),(299,'obeso'),(165,'obras'),(250,'obvio'),(274,'ocaso'),(166,'ocios'),(341,'oeste'),(378,'ojera'),(315,'ojito'),(350,'ojiva'),(68,'olivo'),(167,'ollas'),(168,'ondas'),(169,'onzas'),(426,'opaca'),(377,'oreja'),(366,'osito'),(33,'otoño'),(438,'otros'),(170,'ovulo'),(300,'pacto'),(445,'padre'),(171,'palas'),(459,'palos'),(329,'panal'),(465,'paños'),(43,'panza'),(444,'papas'),(37,'papel'),(374,'pardo'),(488,'pasea'),(487,'paseo'),(327,'pasos'),(414,'pasta'),(23,'pasto'),(291,'patos'),(251,'peces'),(504,'pecho'),(328,'pedal'),(172,'pedir'),(52,'pegar'),(463,'peine'),(174,'pelea'),(173,'pelos'),(461,'penas'),(175,'peras'),(2,'perro'),(468,'pesar'),(176,'pesos'),(262,'piano'),(357,'picar'),(460,'pilas'),(177,'pinto'),(458,'pisos'),(422,'pista'),(421,'pisto'),(234,'plata'),(53,'plato'),(14,'playa'),(320,'plaza'),(275,'plomo'),(16,'pluma'),(178,'poder'),(410,'polar'),(411,'polos'),(515,'potro'),(440,'puede'),(24,'pulga'),(349,'pulpo'),(386,'punta'),(48,'punto'),(467,'queda'),(179,'quedo'),(180,'quema'),(34,'queso'),(276,'quito'),(337,'rabia'),(263,'radio'),(35,'ramas'),(183,'ratas'),(184,'redes'),(181,'reloj'),(185,'remar'),(186,'renos'),(187,'renta'),(490,'rezar'),(277,'rival'),(397,'robar'),(286,'robot'),(8,'rojas'),(302,'rojos'),(359,'rolar'),(455,'ropas'),(389,'rosal'),(513,'rubia'),(182,'rubio'),(49,'rueda'),(351,'ruina'),(60,'rumor'),(188,'sabio'),(189,'sacar'),(191,'salir'),(264,'salsa'),(21,'salto'),(18,'salud'),(192,'sanar'),(285,'santa'),(284,'santo'),(195,'secar'),(190,'selva'),(417,'seres'),(194,'serio'),(200,'siete'),(6,'silla'),(402,'sirve'),(409,'sitio'),(196,'sobar'),(69,'soles'),(439,'solos'),(197,'sonar'),(193,'sopas'),(198,'subir'),(199,'sucio'),(352,'sudar'),(367,'suela'),(457,'suelo'),(54,'surco'),(252,'sutil'),(203,'tabla'),(204,'tacos'),(436,'talla'),(384,'tallo'),(205,'tapas'),(70,'tarde'),(413,'tarta'),(206,'tazas'),(287,'tecla'),(418,'telas'),(339,'temas'),(278,'temor'),(207,'tener'),(429,'teñir'),(482,'tenso'),(208,'terco'),(319,'texto'),(47,'tigre'),(209,'tipos'),(210,'tiras'),(493,'tocar'),(433,'tomar'),(212,'tonos'),(211,'tonto'),(213,'torpe'),(360,'torta'),(441,'traga'),(503,'trago'),(15,'traje'),(434,'trapo'),(412,'trato'),(214,'trote'),(279,'unico'),(253,'unido'),(265,'usina'),(71,'vacas'),(443,'vagar'),(215,'vagos'),(216,'valor'),(266,'vapor'),(427,'vario'),(217,'velas'),(218,'vemos'),(219,'venas'),(407,'venta'),(9,'verde'),(220,'vigas'),(221,'vinos'),(282,'visto'),(280,'vital'),(222,'vivir'),(56,'vocal'),(223,'volar'),(309,'volts'),(224,'votar'),(303,'weber'),(72,'yates'),(514,'yegua'),(225,'yemas'),(226,'yendo'),(227,'yenes'),(267,'yerba'),(298,'yerro'),(228,'yogur'),(296,'zanja'),(383,'zarza'),(232,'zonas'),(229,'zorro'),(239,'zumos'),(231,'zurda'),(230,'zurdo');
/*!40000 ALTER TABLE `words` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-05 16:41:51
