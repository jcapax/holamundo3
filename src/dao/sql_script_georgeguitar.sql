/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  georgeguitar
 * Created: 16-08-2017
 */






-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 16-08-2017 a las 12:56:00
-- Versión del servidor: 10.0.30-MariaDB-0+deb8u2
-- Versión de PHP: 5.6.30-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `pacheco_sales_2`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuraciones`
--

CREATE TABLE IF NOT EXISTS `configuraciones` (
`id_configuracion` tinyint(20) unsigned NOT NULL,
  `ruta_visor_pdf` text COLLATE utf32_spanish_ci,
  `ruta_destino_archivos_pdf` text COLLATE utf32_spanish_ci,
  `solo_guadar_archivos_pdf` tinyint(1) DEFAULT '0',
  `ruta_programas_pg` text COLLATE utf32_spanish_ci
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf32 COLLATE=utf32_spanish_ci;

--
-- Volcado de datos para la tabla `configuraciones`
--

INSERT INTO `configuraciones` (`id_configuracion`, `ruta_visor_pdf`, `ruta_destino_archivos_pdf`, `solo_guadar_archivos_pdf`, `ruta_programas_pg`) VALUES
(11, '/home/georgeguitar/MusE/untitled_2.med', '/home/georgeguitar/NetBeansProjects/Almacenes', 0, '/home/georgeguitar/NetBeansProjects/Almacenes');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `configuraciones`
--
ALTER TABLE `configuraciones`
 ADD PRIMARY KEY (`id_configuracion`), ADD UNIQUE KEY `id_configuracion` (`id_configuracion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `configuraciones`
--
ALTER TABLE `configuraciones`
MODIFY `id_configuracion` tinyint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;