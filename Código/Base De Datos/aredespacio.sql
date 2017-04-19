/*
Navicat MySQL Data Transfer

Source Server         : MySQL Server
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : aredespacio

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-04-18 23:59:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for alumno
-- ----------------------------
DROP TABLE IF EXISTS `alumno`;
CREATE TABLE `alumno` (
  `idAlumno` int(11) NOT NULL AUTO_INCREMENT,
  `matricula` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `genero` varchar(45) NOT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `movil` varchar(45) NOT NULL,
  `domicilo` varchar(45) NOT NULL,
  `cp` varchar(45) DEFAULT NULL,
  `ciudad` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `foto` longblob,
  `fechaRegistro` date NOT NULL,
  `fechaBaja` date DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'Alta',
  `motivo` text,
  PRIMARY KEY (`idAlumno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for asistencia
-- ----------------------------
DROP TABLE IF EXISTS `asistencia`;
CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL AUTO_INCREMENT,
  `idClase` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idAsistencia`),
  KEY `idClase` (`idClase`),
  CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`idClase`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for clase
-- ----------------------------
DROP TABLE IF EXISTS `clase`;
CREATE TABLE `clase` (
  `idClase` int(11) NOT NULL AUTO_INCREMENT,
  `idGrupo` int(11) NOT NULL,
  `idAlumno` int(11) NOT NULL,
  PRIMARY KEY (`idClase`),
  UNIQUE KEY `idClase` (`idClase`) USING BTREE,
  KEY `idGrupo` (`idGrupo`) USING BTREE,
  KEY `idAlumno` (`idAlumno`) USING BTREE,
  CONSTRAINT `clase_ibfk_1` FOREIGN KEY (`idAlumno`) REFERENCES `alumno` (`idAlumno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `clase_ibfk_2` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`idGrupo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for contador
-- ----------------------------
DROP TABLE IF EXISTS `contador`;
CREATE TABLE `contador` (
  `idContador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `contador` int(11) NOT NULL,
  PRIMARY KEY (`idContador`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for danza
-- ----------------------------
DROP TABLE IF EXISTS `danza`;
CREATE TABLE `danza` (
  `idDanza` int(11) NOT NULL AUTO_INCREMENT,
  `tipoDanza` varchar(45) NOT NULL,
  PRIMARY KEY (`idDanza`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for grupo
-- ----------------------------
DROP TABLE IF EXISTS `grupo`;
CREATE TABLE `grupo` (
  `idGrupo` int(11) NOT NULL AUTO_INCREMENT,
  `salon` varchar(10) NOT NULL,
  `idMaestro` int(11) NOT NULL,
  `idDanza` int(11) NOT NULL,
  `nivel` varchar(45) NOT NULL,
  `inicioDeGrupo` date NOT NULL,
  `finDeGrupo` date DEFAULT NULL,
  PRIMARY KEY (`idGrupo`),
  KEY `idMaestro` (`idMaestro`),
  KEY `idDanza` (`idDanza`),
  CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`idMaestro`) REFERENCES `maestro` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `grupo_ibfk_2` FOREIGN KEY (`idDanza`) REFERENCES `danza` (`idDanza`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for horario
-- ----------------------------
DROP TABLE IF EXISTS `horario`;
CREATE TABLE `horario` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `idGrupo` int(11) NOT NULL,
  `dia` varchar(10) NOT NULL,
  `hora` varchar(12) NOT NULL,
  PRIMARY KEY (`idHorario`),
  KEY `idGrupo` (`idGrupo`),
  CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`idGrupo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for maestro
-- ----------------------------
DROP TABLE IF EXISTS `maestro`;
CREATE TABLE `maestro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `noDeColaborador` varchar(12) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `foto` longblob,
  `fechaDeNacimiento` date NOT NULL,
  `genero` tinyint(1) NOT NULL,
  `correoElectronico` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `telefonoMovil` varchar(45) NOT NULL,
  `domicilio` varchar(45) NOT NULL,
  `ciudad` varchar(45) NOT NULL,
  `codigoPostal` varchar(45) DEFAULT NULL,
  `estado` varchar(45) NOT NULL,
  `fechaDeRegistro` date NOT NULL,
  `deshabilitado` tinyint(1) NOT NULL,
  `fechaDeDeshabilitacion` date DEFAULT NULL,
  `motivoDeDeshabilitacion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
