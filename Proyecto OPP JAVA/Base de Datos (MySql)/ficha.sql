-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 09-12-2014 a las 21:10:41
-- Versión del servidor: 5.5.40-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `ficha`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PACIENTES`
--

CREATE TABLE IF NOT EXISTS `PACIENTES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RUT` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `NOMBRE` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `APELLIDOS` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `TELEFONO` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `EDAD` int(11) NOT NULL,
  `DIRECCION` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `ENFERMEDADES` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `INSUMOS` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `DIAGNOSTICO` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `ALCOHOL` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `CIGARRO` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `PESO` int(11) DEFAULT NULL,
  `ESTATURA` int(11) DEFAULT NULL,
  `ESPECIALISTA` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `RUT` (`RUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `USUARIOS`
--

CREATE TABLE IF NOT EXISTS `USUARIOS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `APELLIDOS` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `USUARIO` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `PASS` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `TIPO` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `FOTO` longblob,
  `PATH` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `usuario` (`USUARIO`),
  KEY `id` (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `USUARIOS`
--

INSERT INTO `USUARIOS` (`ID`, `NOMBRE`, `APELLIDOS`, `USUARIO`, `PASS`, `TIPO`, `FOTO`, `PATH`) VALUES
(1, 'Pedro', 'Perez', 'pedro', 'asdf', 'Administrador', NULL, NULL),
(2, 'Juan', 'Rojas', 'juan', 'asdf', 'Basico', NULL, '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
