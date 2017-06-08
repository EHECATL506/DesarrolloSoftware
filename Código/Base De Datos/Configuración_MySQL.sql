mysql -u root -p

create database aredespacio;
use aredespacio;

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `asistencia`;
CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL AUTO_INCREMENT,
  `idClase` int(11) NOT NULL,
  `asistio` bit(1) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`idAsistencia`),
  KEY `idClase` (`idClase`),
  CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`idClase`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `clase`;
CREATE TABLE `clase` (
  `idClase` int(11) NOT NULL AUTO_INCREMENT,
  `idGrupo` int(11) NOT NULL,
  `idAlumno` int(11) NOT NULL,
  `fechaRegistro` date NOT NULL,
  PRIMARY KEY (`idClase`),
  UNIQUE KEY `idClase` (`idClase`) USING BTREE,
  KEY `idGrupo` (`idGrupo`) USING BTREE,
  KEY `idAlumno` (`idAlumno`) USING BTREE,
  CONSTRAINT `clase_ibfk_1` FOREIGN KEY (`idAlumno`) REFERENCES `alumno` (`idAlumno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `clase_ibfk_2` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`idGrupo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `danza`;
CREATE TABLE `danza` (
  `idDanza` int(11) NOT NULL AUTO_INCREMENT,
  `tipoDanza` varchar(45) NOT NULL,
  PRIMARY KEY (`idDanza`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `egreso`;
CREATE TABLE `egreso` (
  `idEgreso` int(11) NOT NULL AUTO_INCREMENT,
  `monto` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `motivo` text NOT NULL,
  `IdMaestro` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEgreso`),
  KEY `IdMaestro` (`IdMaestro`),
  CONSTRAINT `egreso_ibfk_1` FOREIGN KEY (`IdMaestro`) REFERENCES `maestro` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `grupo`;
CREATE TABLE `grupo` (
  `idGrupo` int(11) NOT NULL AUTO_INCREMENT,
  `salon` varchar(10) NOT NULL,
  `idMaestro` int(11) NOT NULL,
  `idDanza` int(11) NOT NULL,
  `nivel` varchar(45) NOT NULL,
  `inicioDeGrupo` date NOT NULL,
  `finDeGrupo` date DEFAULT NULL,
  `costo` int(11) NOT NULL,
  `porcentaje` int(11) NOT NULL,
  PRIMARY KEY (`idGrupo`),
  KEY `idMaestro` (`idMaestro`),
  KEY `idDanza` (`idDanza`),
  CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`idMaestro`) REFERENCES `maestro` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `grupo_ibfk_2` FOREIGN KEY (`idDanza`) REFERENCES `danza` (`idDanza`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `horario`;
CREATE TABLE `horario` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `idGrupo` int(11) NOT NULL,
  `dia` varchar(10) NOT NULL,
  `hora` varchar(12) NOT NULL,
  PRIMARY KEY (`idHorario`),
  KEY `idGrupo` (`idGrupo`),
  CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`idGrupo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `pago`;
CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL AUTO_INCREMENT,
  `idPromocion` int(11) DEFAULT NULL,
  `folio` varchar(12) DEFAULT NULL,
  `idClase` int(11) NOT NULL,
  `descuento` float NOT NULL,
  `abono` float NOT NULL,
  `fechaPago` date NOT NULL,
  `status` varchar(10) NOT NULL,
  `tipoDePago` varchar(32) NOT NULL,
  PRIMARY KEY (`idPago`),
  KEY `idClase` (`idClase`),
  KEY `idPromocion` (`idPromocion`),
  CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`idClase`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pago_ibfk_2` FOREIGN KEY (`idPromocion`) REFERENCES `promocion` (`idPromocion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `promocion`;
CREATE TABLE `promocion` (
  `idPromocion` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(20) NOT NULL,
  `descripcion` varchar(60) NOT NULL,
  `descuento` float NOT NULL,
  PRIMARY KEY (`idPromocion`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
