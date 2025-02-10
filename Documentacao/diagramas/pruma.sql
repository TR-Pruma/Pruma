CREATE DATABASE  IF NOT EXISTS `pruma` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pruma`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pruma
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
-- Table structure for table `anexo`
--

DROP TABLE IF EXISTS `anexo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anexo` (
  `anexo_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `tipo_anexo` varchar(15) DEFAULT NULL,
  `caminho_arquivo` text,
  PRIMARY KEY (`anexo_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `anexo_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anexo`
--

LOCK TABLES `anexo` WRITE;
/*!40000 ALTER TABLE `anexo` DISABLE KEYS */;
/*!40000 ALTER TABLE `anexo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assinatura_digital`
--

DROP TABLE IF EXISTS `assinatura_digital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assinatura_digital` (
  `assinatura_id` int NOT NULL AUTO_INCREMENT,
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `documento_id` int DEFAULT NULL,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`assinatura_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  KEY `documento_id` (`documento_id`),
  CONSTRAINT `assinatura_digital_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `assinatura_digital_ibfk_2` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `assinatura_digital_ibfk_3` FOREIGN KEY (`documento_id`) REFERENCES `documento` (`documento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assinatura_digital`
--

LOCK TABLES `assinatura_digital` WRITE;
/*!40000 ALTER TABLE `assinatura_digital` DISABLE KEYS */;
/*!40000 ALTER TABLE `assinatura_digital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `atividade`
--

DROP TABLE IF EXISTS `atividade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `atividade` (
  `atividade_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `descricao` text,
  `status` varchar(15) DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`atividade_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `atividade_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `atividade`
--

LOCK TABLES `atividade` WRITE;
/*!40000 ALTER TABLE `atividade` DISABLE KEYS */;
/*!40000 ALTER TABLE `atividade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria` (
  `auditoria_id` int NOT NULL AUTO_INCREMENT,
  `cliente_cpf` mediumtext,
  `tipo_usuario` int DEFAULT NULL,
  `acao` text,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`auditoria_id`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `auditoria_ibfk_1` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria`
--

LOCK TABLES `auditoria` WRITE;
/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avaliacao`
--

DROP TABLE IF EXISTS `avaliacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avaliacao` (
  `avaliacao_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `cliente_cpf` bigint DEFAULT NULL,
  `nota` decimal(2,1) DEFAULT NULL,
  `comentario` text,
  PRIMARY KEY (`avaliacao_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  CONSTRAINT `avaliacao_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `avaliacao_ibfk_2` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avaliacao`
--

LOCK TABLES `avaliacao` WRITE;
/*!40000 ALTER TABLE `avaliacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `avaliacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `categoria_id` int NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`categoria_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checklist`
--

DROP TABLE IF EXISTS `checklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checklist` (
  `checklist_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`checklist_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `checklist_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checklist`
--

LOCK TABLES `checklist` WRITE;
/*!40000 ALTER TABLE `checklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `checklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `cliente_cpf` bigint NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Telefone` bigint NOT NULL,
  `Id_endereco` int DEFAULT NULL,
  `Senha` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`cliente_cpf`),
  KEY `Id_endereco` (`Id_endereco`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`Id_endereco`) REFERENCES `endereco` (`id_endereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente_tipo`
--

DROP TABLE IF EXISTS `cliente_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente_tipo` (
  `id_cliente_tipo` int NOT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `descricao_cliente` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cliente_tipo`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `cliente_tipo_ibfk_1` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `FKg54tgic68gjm3f6nqyvpdcepf` FOREIGN KEY (`tipo_usuario`) REFERENCES `usuario_tipo` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente_tipo`
--

LOCK TABLES `cliente_tipo` WRITE;
/*!40000 ALTER TABLE `cliente_tipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente_tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comunicacao`
--

DROP TABLE IF EXISTS `comunicacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunicacao` (
  `comunicacao_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `id_cliente` bigint DEFAULT NULL,
  `tipo_remetente` varchar(15) DEFAULT NULL,
  `mensagem` text,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`comunicacao_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `comunicacao_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `comunicacao_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`cliente_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunicacao`
--

LOCK TABLES `comunicacao` WRITE;
/*!40000 ALTER TABLE `comunicacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `comunicacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comunicacao_aux`
--

DROP TABLE IF EXISTS `comunicacao_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comunicacao_aux` (
  `comunicacao_id` int NOT NULL,
  `tipo_mensagem` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`comunicacao_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comunicacao_aux`
--

LOCK TABLES `comunicacao_aux` WRITE;
/*!40000 ALTER TABLE `comunicacao_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `comunicacao_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cronograma`
--

DROP TABLE IF EXISTS `cronograma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cronograma` (
  `cronograma_id` int NOT NULL,
  `projeto_id` int DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`cronograma_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `cronograma_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cronograma`
--

LOCK TABLES `cronograma` WRITE;
/*!40000 ALTER TABLE `cronograma` DISABLE KEYS */;
/*!40000 ALTER TABLE `cronograma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento` (
  `documento_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `id_tipo_documento` int DEFAULT NULL,
  `caminho_arquivo` varchar(50) DEFAULT NULL,
  `data_upload` date DEFAULT NULL,
  PRIMARY KEY (`documento_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `id_tipo_documento` (`id_tipo_documento`),
  CONSTRAINT `documento_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `documento_ibfk_2` FOREIGN KEY (`id_tipo_documento`) REFERENCES `tipo_documento` (`id_tipo_documento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento`
--

LOCK TABLES `documento` WRITE;
/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `empresa_cnpj` bigint NOT NULL,
  `razao_social` varchar(50) NOT NULL,
  `nome_fantasia` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `telefone` bigint NOT NULL,
  `Id_endereco` int DEFAULT NULL,
  PRIMARY KEY (`empresa_cnpj`),
  KEY `Id_endereco` (`Id_endereco`),
  CONSTRAINT `empresa_ibfk_1` FOREIGN KEY (`Id_endereco`) REFERENCES `endereco` (`id_endereco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco` (
  `id_endereco` int NOT NULL AUTO_INCREMENT,
  `id_lougradouro` int DEFAULT NULL,
  `rua` varchar(50) DEFAULT NULL,
  `numero` int DEFAULT NULL,
  `complemento` varchar(15) DEFAULT NULL,
  `bairro` varchar(30) DEFAULT NULL,
  `cidade` varchar(30) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `pais` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_endereco`),
  KEY `id_lougradouro` (`id_lougradouro`),
  CONSTRAINT `endereco_ibfk_1` FOREIGN KEY (`id_lougradouro`) REFERENCES `lougradouro` (`id_lougradouro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipamento`
--

DROP TABLE IF EXISTS `equipamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipamento` (
  `equipamento_id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  `id_status` int DEFAULT NULL,
  PRIMARY KEY (`equipamento_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `equipamento_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `status_equipamento` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipamento`
--

LOCK TABLES `equipamento` WRITE;
/*!40000 ALTER TABLE `equipamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipamento_projeto`
--

DROP TABLE IF EXISTS `equipamento_projeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipamento_projeto` (
  `equipamento_id` int DEFAULT NULL,
  `projeto_id` int DEFAULT NULL,
  `data_alocacao` date DEFAULT NULL,
  `data_devolucao` date DEFAULT NULL,
  KEY `equipamento_id` (`equipamento_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `equipamento_projeto_ibfk_1` FOREIGN KEY (`equipamento_id`) REFERENCES `equipamento` (`equipamento_id`),
  CONSTRAINT `equipamento_projeto_ibfk_2` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipamento_projeto`
--

LOCK TABLES `equipamento_projeto` WRITE;
/*!40000 ALTER TABLE `equipamento_projeto` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipamento_projeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipamento_projeto_aux`
--

DROP TABLE IF EXISTS `equipamento_projeto_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipamento_projeto_aux` (
  `equipamento_id` int DEFAULT NULL,
  `projeto_id` int DEFAULT NULL,
  `data_alocacao` date DEFAULT NULL,
  `data_devolucao` date DEFAULT NULL,
  KEY `equipamento_id` (`equipamento_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `equipamento_projeto_aux_ibfk_1` FOREIGN KEY (`equipamento_id`) REFERENCES `equipamento` (`equipamento_id`),
  CONSTRAINT `equipamento_projeto_aux_ibfk_2` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipamento_projeto_aux`
--

LOCK TABLES `equipamento_projeto_aux` WRITE;
/*!40000 ALTER TABLE `equipamento_projeto_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipamento_projeto_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fase_cronograma`
--

DROP TABLE IF EXISTS `fase_cronograma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fase_cronograma` (
  `fase_id` int NOT NULL,
  `cronograma_id` int DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`fase_id`),
  KEY `cronograma_id` (`cronograma_id`),
  CONSTRAINT `fase_cronograma_ibfk_1` FOREIGN KEY (`cronograma_id`) REFERENCES `cronograma` (`cronograma_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fase_cronograma`
--

LOCK TABLES `fase_cronograma` WRITE;
/*!40000 ALTER TABLE `fase_cronograma` DISABLE KEYS */;
/*!40000 ALTER TABLE `fase_cronograma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `mensagem` text,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `feedback_ibfk_3` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornecedor` (
  `fornecedor_id` int NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `cnpj` varchar(255) DEFAULT NULL,
  `contato` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fornecedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_localizacao`
--

DROP TABLE IF EXISTS `historico_localizacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historico_localizacao` (
  `historico_id` int NOT NULL AUTO_INCREMENT,
  `profissional_cpf` bigint DEFAULT NULL,
  `projeto_id` int DEFAULT NULL,
  `localizacao` varchar(50) DEFAULT NULL,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`historico_id`),
  KEY `profissional_cpf` (`profissional_cpf`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `historico_localizacao_ibfk_1` FOREIGN KEY (`profissional_cpf`) REFERENCES `profissional_de_base` (`profissional_cpf`),
  CONSTRAINT `historico_localizacao_ibfk_2` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_localizacao`
--

LOCK TABLES `historico_localizacao` WRITE;
/*!40000 ALTER TABLE `historico_localizacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `historico_localizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagem_projeto`
--

DROP TABLE IF EXISTS `imagem_projeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imagem_projeto` (
  `imagem_id` int NOT NULL,
  `projeto_id` int DEFAULT NULL,
  `caminho_arquivo` varchar(255) DEFAULT NULL,
  `descricao` text,
  `data_upload` date DEFAULT NULL,
  PRIMARY KEY (`imagem_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `imagem_projeto_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagem_projeto`
--

LOCK TABLES `imagem_projeto` WRITE;
/*!40000 ALTER TABLE `imagem_projeto` DISABLE KEYS */;
/*!40000 ALTER TABLE `imagem_projeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspecao`
--

DROP TABLE IF EXISTS `inspecao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inspecao` (
  `inspecao_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `tecnico_id` bigint DEFAULT NULL,
  `descricao` text,
  `data_inspecao` date DEFAULT NULL,
  `resultado` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`inspecao_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `tecnico_id` (`tecnico_id`),
  CONSTRAINT `inspecao_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `inspecao_ibfk_2` FOREIGN KEY (`tecnico_id`) REFERENCES `profissional_de_base` (`profissional_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspecao`
--

LOCK TABLES `inspecao` WRITE;
/*!40000 ALTER TABLE `inspecao` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspecao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insumo`
--

DROP TABLE IF EXISTS `insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumo` (
  `insumo_id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` text,
  `unidade_medida` varchar(15) DEFAULT NULL,
  `custo` float DEFAULT NULL,
  PRIMARY KEY (`insumo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insumo`
--

LOCK TABLES `insumo` WRITE;
/*!40000 ALTER TABLE `insumo` DISABLE KEYS */;
/*!40000 ALTER TABLE `insumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insumo_fornecedor`
--

DROP TABLE IF EXISTS `insumo_fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumo_fornecedor` (
  `insumo_id` int DEFAULT NULL,
  `fornecedor_id` int DEFAULT NULL,
  `preco` decimal(10,2) DEFAULT NULL,
  KEY `insumo_id` (`insumo_id`),
  KEY `fornecedor_id` (`fornecedor_id`),
  CONSTRAINT `insumo_fornecedor_ibfk_1` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`insumo_id`),
  CONSTRAINT `insumo_fornecedor_ibfk_2` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedor` (`fornecedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insumo_fornecedor`
--

LOCK TABLES `insumo_fornecedor` WRITE;
/*!40000 ALTER TABLE `insumo_fornecedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `insumo_fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insumo_fornecedor_aux`
--

DROP TABLE IF EXISTS `insumo_fornecedor_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumo_fornecedor_aux` (
  `insumo_id` int NOT NULL,
  `fornecedor_id` int DEFAULT NULL,
  `preco` float DEFAULT NULL,
  PRIMARY KEY (`insumo_id`),
  KEY `fornecedor_id` (`fornecedor_id`),
  CONSTRAINT `insumo_fornecedor_aux_ibfk_1` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedor` (`fornecedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insumo_fornecedor_aux`
--

LOCK TABLES `insumo_fornecedor_aux` WRITE;
/*!40000 ALTER TABLE `insumo_fornecedor_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `insumo_fornecedor_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_checklist`
--

DROP TABLE IF EXISTS `item_checklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_checklist` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `checklist_id` int DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `checklist_id` (`checklist_id`),
  CONSTRAINT `item_checklist_ibfk_1` FOREIGN KEY (`checklist_id`) REFERENCES `checklist` (`checklist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_checklist`
--

LOCK TABLES `item_checklist` WRITE;
/*!40000 ALTER TABLE `item_checklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_checklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_orcamento`
--

DROP TABLE IF EXISTS `item_orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_orcamento` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `orcamento_id` int DEFAULT NULL,
  `descricao` text,
  `quantidade` int DEFAULT NULL,
  `valor_unitario` float DEFAULT NULL,
  `valor_total` float DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `orcamento_id` (`orcamento_id`),
  CONSTRAINT `item_orcamento_ibfk_1` FOREIGN KEY (`orcamento_id`) REFERENCES `orcamento` (`orcamento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_orcamento`
--

LOCK TABLES `item_orcamento` WRITE;
/*!40000 ALTER TABLE `item_orcamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lembrete`
--

DROP TABLE IF EXISTS `lembrete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lembrete` (
  `lembrete_id` int NOT NULL AUTO_INCREMENT,
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `mensagem` text,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`lembrete_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `lembrete_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `lembrete_ibfk_2` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lembrete`
--

LOCK TABLES `lembrete` WRITE;
/*!40000 ALTER TABLE `lembrete` DISABLE KEYS */;
/*!40000 ALTER TABLE `lembrete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_alteracao`
--

DROP TABLE IF EXISTS `log_alteracao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_alteracao` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `log_alteracao_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `log_alteracao_ibfk_2` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `log_alteracao_ibfk_3` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_alteracao`
--

LOCK TABLES `log_alteracao` WRITE;
/*!40000 ALTER TABLE `log_alteracao` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_alteracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_alteracao_aux`
--

DROP TABLE IF EXISTS `log_alteracao_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_alteracao_aux` (
  `log_id` int NOT NULL,
  `tipo_alteracao` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_alteracao_aux`
--

LOCK TABLES `log_alteracao_aux` WRITE;
/*!40000 ALTER TABLE `log_alteracao_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_alteracao_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logradouro`
--

DROP TABLE IF EXISTS `logradouro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logradouro` (
  `id_logradouro` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_logradouro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logradouro`
--

LOCK TABLES `logradouro` WRITE;
/*!40000 ALTER TABLE `logradouro` DISABLE KEYS */;
/*!40000 ALTER TABLE `logradouro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lougradouro`
--

DROP TABLE IF EXISTS `lougradouro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lougradouro` (
  `id_lougradouro` int NOT NULL AUTO_INCREMENT,
  `Tipo` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_lougradouro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lougradouro`
--

LOCK TABLES `lougradouro` WRITE;
/*!40000 ALTER TABLE `lougradouro` DISABLE KEYS */;
/*!40000 ALTER TABLE `lougradouro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `material_id` int NOT NULL AUTO_INCREMENT,
  `descricao` text,
  `quantidade` int DEFAULT NULL,
  `custo_unitario` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_utilizado`
--

DROP TABLE IF EXISTS `material_utilizado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material_utilizado` (
  `material_id` int DEFAULT NULL,
  `atividade_id` int DEFAULT NULL,
  `quantidade_utilizada` int DEFAULT NULL,
  KEY `material_id` (`material_id`),
  KEY `atividade_id` (`atividade_id`),
  CONSTRAINT `material_utilizado_ibfk_1` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`),
  CONSTRAINT `material_utilizado_ibfk_2` FOREIGN KEY (`atividade_id`) REFERENCES `atividade` (`atividade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_utilizado`
--

LOCK TABLES `material_utilizado` WRITE;
/*!40000 ALTER TABLE `material_utilizado` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_utilizado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensagem_instantanea`
--

DROP TABLE IF EXISTS `mensagem_instantanea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensagem_instantanea` (
  `mensagem_id` int NOT NULL AUTO_INCREMENT,
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `destinatario_id` mediumtext,
  `tipo_destinatario` varchar(15) DEFAULT NULL,
  `conteudo` text,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`mensagem_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `mensagem_instantanea_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `mensagem_instantanea_ibfk_2` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensagem_instantanea`
--

LOCK TABLES `mensagem_instantanea` WRITE;
/*!40000 ALTER TABLE `mensagem_instantanea` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagem_instantanea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensagem_instantanea_aux`
--

DROP TABLE IF EXISTS `mensagem_instantanea_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensagem_instantanea_aux` (
  `mensagem_id` int NOT NULL,
  `tipo_mensagem` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`mensagem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensagem_instantanea_aux`
--

LOCK TABLES `mensagem_instantanea_aux` WRITE;
/*!40000 ALTER TABLE `mensagem_instantanea_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagem_instantanea_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notificacao`
--

DROP TABLE IF EXISTS `notificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificacao` (
  `notificacao_id` int NOT NULL,
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `mensagem` varchar(255) DEFAULT NULL,
  `data_hora` datetime DEFAULT NULL,
  `lida` bit(1) DEFAULT NULL,
  PRIMARY KEY (`notificacao_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `notificacao_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `notificacao_ibfk_2` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificacao`
--

LOCK TABLES `notificacao` WRITE;
/*!40000 ALTER TABLE `notificacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `obra`
--

DROP TABLE IF EXISTS `obra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `obra` (
  `obra_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `descricao` text,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`obra_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `obra_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obra`
--

LOCK TABLES `obra` WRITE;
/*!40000 ALTER TABLE `obra` DISABLE KEYS */;
/*!40000 ALTER TABLE `obra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orcamento`
--

DROP TABLE IF EXISTS `orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orcamento` (
  `orcamento_id` int NOT NULL,
  `projeto_id` int DEFAULT NULL,
  `empresa_cnpj` bigint DEFAULT NULL,
  `valor` decimal(18,2) DEFAULT NULL,
  `data_envio` date DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`orcamento_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `empresa_cnpj` (`empresa_cnpj`),
  CONSTRAINT `orcamento_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `orcamento_ibfk_2` FOREIGN KEY (`empresa_cnpj`) REFERENCES `empresa` (`empresa_cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orcamento`
--

LOCK TABLES `orcamento` WRITE;
/*!40000 ALTER TABLE `orcamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagamento` (
  `pagamento_id` int NOT NULL AUTO_INCREMENT,
  `orcamento_id` int DEFAULT NULL,
  `valor` decimal(18,2) DEFAULT NULL,
  `data_pagamento` date DEFAULT NULL,
  `forma_pagamento` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`pagamento_id`),
  KEY `orcamento_id` (`orcamento_id`),
  CONSTRAINT `pagamento_ibfk_1` FOREIGN KEY (`orcamento_id`) REFERENCES `orcamento` (`orcamento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagamento`
--

LOCK TABLES `pagamento` WRITE;
/*!40000 ALTER TABLE `pagamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissao_usuario`
--

DROP TABLE IF EXISTS `permissao_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissao_usuario` (
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `permissao` varchar(15) DEFAULT NULL,
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `permissao_usuario_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `permissao_usuario_ibfk_2` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissao_usuario`
--

LOCK TABLES `permissao_usuario` WRITE;
/*!40000 ALTER TABLE `permissao_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissao_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissao_usuario_aux`
--

DROP TABLE IF EXISTS `permissao_usuario_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissao_usuario_aux` (
  `cliente_cpf` bigint DEFAULT NULL,
  `tipo_usuario` int DEFAULT NULL,
  `permissao` varchar(15) DEFAULT NULL,
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `tipo_usuario` (`tipo_usuario`),
  CONSTRAINT `permissao_usuario_aux_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `permissao_usuario_aux_ibfk_2` FOREIGN KEY (`tipo_usuario`) REFERENCES `tipo_usuario` (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissao_usuario_aux`
--

LOCK TABLES `permissao_usuario_aux` WRITE;
/*!40000 ALTER TABLE `permissao_usuario_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissao_usuario_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pinterest_interacao`
--

DROP TABLE IF EXISTS `pinterest_interacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pinterest_interacao` (
  `interacao_id` int NOT NULL AUTO_INCREMENT,
  `cliente_cpf` bigint DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `descricao` text,
  `data_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`interacao_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  CONSTRAINT `pinterest_interacao_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pinterest_interacao`
--

LOCK TABLES `pinterest_interacao` WRITE;
/*!40000 ALTER TABLE `pinterest_interacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `pinterest_interacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pinterest_interacao_aux`
--

DROP TABLE IF EXISTS `pinterest_interacao_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pinterest_interacao_aux` (
  `interacao_id` int DEFAULT NULL,
  `tipo_interacao` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pinterest_interacao_aux`
--

LOCK TABLES `pinterest_interacao_aux` WRITE;
/*!40000 ALTER TABLE `pinterest_interacao_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `pinterest_interacao_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pos_obra`
--

DROP TABLE IF EXISTS `pos_obra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pos_obra` (
  `pos_obra_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `descricao` text,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`pos_obra_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `pos_obra_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_obra`
--

LOCK TABLES `pos_obra` WRITE;
/*!40000 ALTER TABLE `pos_obra` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_obra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pre_obra`
--

DROP TABLE IF EXISTS `pre_obra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pre_obra` (
  `pre_obra_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `descricao` text,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`pre_obra_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `pre_obra_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pre_obra`
--

LOCK TABLES `pre_obra` WRITE;
/*!40000 ALTER TABLE `pre_obra` DISABLE KEYS */;
/*!40000 ALTER TABLE `pre_obra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profissional_de_base`
--

DROP TABLE IF EXISTS `profissional_de_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profissional_de_base` (
  `profissional_cpf` bigint NOT NULL,
  `id_cliente` bigint DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `telefone` bigint DEFAULT NULL,
  `tipo` varchar(15) DEFAULT NULL,
  `atestado_antecedentes` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`profissional_cpf`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `profissional_de_base_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`cliente_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profissional_de_base`
--

LOCK TABLES `profissional_de_base` WRITE;
/*!40000 ALTER TABLE `profissional_de_base` DISABLE KEYS */;
/*!40000 ALTER TABLE `profissional_de_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projeto`
--

DROP TABLE IF EXISTS `projeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projeto` (
  `projeto_id` int NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `descricao` text,
  `cliente_cpf` bigint DEFAULT NULL,
  `empresa_cnpj` bigint DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`projeto_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `empresa_cnpj` (`empresa_cnpj`),
  CONSTRAINT `projeto_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `projeto_ibfk_2` FOREIGN KEY (`empresa_cnpj`) REFERENCES `empresa` (`empresa_cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projeto`
--

LOCK TABLES `projeto` WRITE;
/*!40000 ALTER TABLE `projeto` DISABLE KEYS */;
/*!40000 ALTER TABLE `projeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projeto_categoria`
--

DROP TABLE IF EXISTS `projeto_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projeto_categoria` (
  `projeto_id` int DEFAULT NULL,
  `categoria_id` int DEFAULT NULL,
  KEY `projeto_id` (`projeto_id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `projeto_categoria_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `projeto_categoria_ibfk_2` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`categoria_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projeto_categoria`
--

LOCK TABLES `projeto_categoria` WRITE;
/*!40000 ALTER TABLE `projeto_categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `projeto_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projeto_categoria_aux`
--

DROP TABLE IF EXISTS `projeto_categoria_aux`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projeto_categoria_aux` (
  `projeto_id` int DEFAULT NULL,
  `categoria_id` int DEFAULT NULL,
  KEY `projeto_id` (`projeto_id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `projeto_categoria_aux_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `projeto_categoria_aux_ibfk_2` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`categoria_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projeto_categoria_aux`
--

LOCK TABLES `projeto_categoria_aux` WRITE;
/*!40000 ALTER TABLE `projeto_categoria_aux` DISABLE KEYS */;
/*!40000 ALTER TABLE `projeto_categoria_aux` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projeto_profissional`
--

DROP TABLE IF EXISTS `projeto_profissional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projeto_profissional` (
  `projeto_id` int DEFAULT NULL,
  `profissional_cpf` bigint DEFAULT NULL,
  `data_entrada` date DEFAULT NULL,
  `data_saida` date DEFAULT NULL,
  KEY `projeto_id` (`projeto_id`),
  KEY `profissional_cpf` (`profissional_cpf`),
  CONSTRAINT `projeto_profissional_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `projeto_profissional_ibfk_2` FOREIGN KEY (`profissional_cpf`) REFERENCES `profissional_de_base` (`profissional_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projeto_profissional`
--

LOCK TABLES `projeto_profissional` WRITE;
/*!40000 ALTER TABLE `projeto_profissional` DISABLE KEYS */;
/*!40000 ALTER TABLE `projeto_profissional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relatorio`
--

DROP TABLE IF EXISTS `relatorio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relatorio` (
  `relatorio_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `tipo_relatorio` varchar(20) DEFAULT NULL,
  `conteudo` varchar(255) DEFAULT NULL,
  `data_geracao` date DEFAULT NULL,
  PRIMARY KEY (`relatorio_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `relatorio_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relatorio`
--

LOCK TABLES `relatorio` WRITE;
/*!40000 ALTER TABLE `relatorio` DISABLE KEYS */;
/*!40000 ALTER TABLE `relatorio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisicao_material`
--

DROP TABLE IF EXISTS `requisicao_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requisicao_material` (
  `requisicao_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `material_id` int DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `data_requisicao` date DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `custo_unitario` float DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`requisicao_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `material_id` (`material_id`),
  CONSTRAINT `requisicao_material_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `requisicao_material_ibfk_2` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisicao_material`
--

LOCK TABLES `requisicao_material` WRITE;
/*!40000 ALTER TABLE `requisicao_material` DISABLE KEYS */;
/*!40000 ALTER TABLE `requisicao_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitacao_mudanca`
--

DROP TABLE IF EXISTS `solicitacao_mudanca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitacao_mudanca` (
  `solicitacao_id` int NOT NULL,
  `projeto_id` int DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `status_solicitacao_id` int DEFAULT NULL,
  `data_solicitacao` date DEFAULT NULL,
  `data_resposta` date DEFAULT NULL,
  PRIMARY KEY (`solicitacao_id`),
  KEY `projeto_id` (`projeto_id`),
  KEY `status_solicitacao_id` (`status_solicitacao_id`),
  CONSTRAINT `solicitacao_mudanca_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`),
  CONSTRAINT `solicitacao_mudanca_ibfk_2` FOREIGN KEY (`status_solicitacao_id`) REFERENCES `status_solicitacao` (`status_solicitacao_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitacao_mudanca`
--

LOCK TABLES `solicitacao_mudanca` WRITE;
/*!40000 ALTER TABLE `solicitacao_mudanca` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitacao_mudanca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_equipamento`
--

DROP TABLE IF EXISTS `status_equipamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_equipamento` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_equipamento`
--

LOCK TABLES `status_equipamento` WRITE;
/*!40000 ALTER TABLE `status_equipamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `status_equipamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_solicitacao`
--

DROP TABLE IF EXISTS `status_solicitacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_solicitacao` (
  `status_solicitacao_id` int NOT NULL,
  `descricao_solicitacao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`status_solicitacao_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_solicitacao`
--

LOCK TABLES `status_solicitacao` WRITE;
/*!40000 ALTER TABLE `status_solicitacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `status_solicitacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subcontrato`
--

DROP TABLE IF EXISTS `subcontrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subcontrato` (
  `subcontrato_id` int NOT NULL AUTO_INCREMENT,
  `cliente_cpf` bigint DEFAULT NULL,
  `projeto_id` int DEFAULT NULL,
  `descricao` text,
  `valor` float DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  PRIMARY KEY (`subcontrato_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `subcontrato_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `subcontrato_ibfk_2` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcontrato`
--

LOCK TABLES `subcontrato` WRITE;
/*!40000 ALTER TABLE `subcontrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `subcontrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tecnico_de_obras`
--

DROP TABLE IF EXISTS `tecnico_de_obras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tecnico_de_obras` (
  `tecnico_id` int NOT NULL AUTO_INCREMENT,
  `cliente_cpf` bigint DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `telefone` bigint DEFAULT NULL,
  `empresa_cnpj` bigint DEFAULT NULL,
  PRIMARY KEY (`tecnico_id`),
  KEY `cliente_cpf` (`cliente_cpf`),
  KEY `empresa_cnpj` (`empresa_cnpj`),
  CONSTRAINT `tecnico_de_obras_ibfk_1` FOREIGN KEY (`cliente_cpf`) REFERENCES `cliente` (`cliente_cpf`),
  CONSTRAINT `tecnico_de_obras_ibfk_2` FOREIGN KEY (`empresa_cnpj`) REFERENCES `empresa` (`empresa_cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tecnico_de_obras`
--

LOCK TABLES `tecnico_de_obras` WRITE;
/*!40000 ALTER TABLE `tecnico_de_obras` DISABLE KEYS */;
/*!40000 ALTER TABLE `tecnico_de_obras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `termo_garantia`
--

DROP TABLE IF EXISTS `termo_garantia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `termo_garantia` (
  `termo_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `descricao` text,
  `data_emissao` date DEFAULT NULL,
  `validade` int DEFAULT NULL,
  PRIMARY KEY (`termo_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `termo_garantia_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `termo_garantia`
--

LOCK TABLES `termo_garantia` WRITE;
/*!40000 ALTER TABLE `termo_garantia` DISABLE KEYS */;
/*!40000 ALTER TABLE `termo_garantia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_documento`
--

DROP TABLE IF EXISTS `tipo_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_documento` (
  `id_tipo_documento` int NOT NULL,
  `descricao_documento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_documento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_documento`
--

LOCK TABLES `tipo_documento` WRITE;
/*!40000 ALTER TABLE `tipo_documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_usuario`
--

DROP TABLE IF EXISTS `tipo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_usuario` (
  `tipo_usuario` int NOT NULL,
  `descricao_usuario` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_usuario`
--

LOCK TABLES `tipo_usuario` WRITE;
/*!40000 ALTER TABLE `tipo_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_tipo`
--

DROP TABLE IF EXISTS `usuario_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_tipo` (
  `tipo_usuario` int NOT NULL AUTO_INCREMENT,
  `descricao_usuario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tipo_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_tipo`
--

LOCK TABLES `usuario_tipo` WRITE;
/*!40000 ALTER TABLE `usuario_tipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_projeto`
--

DROP TABLE IF EXISTS `video_projeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_projeto` (
  `video_id` int NOT NULL AUTO_INCREMENT,
  `projeto_id` int DEFAULT NULL,
  `caminho_arquivo` varchar(255) DEFAULT NULL,
  `descricao` text,
  `data_upload` date DEFAULT NULL,
  PRIMARY KEY (`video_id`),
  KEY `projeto_id` (`projeto_id`),
  CONSTRAINT `video_projeto_ibfk_1` FOREIGN KEY (`projeto_id`) REFERENCES `projeto` (`projeto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_projeto`
--

LOCK TABLES `video_projeto` WRITE;
/*!40000 ALTER TABLE `video_projeto` DISABLE KEYS */;
/*!40000 ALTER TABLE `video_projeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'pruma'
--

--
-- Dumping routines for database 'pruma'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-10 13:01:18
