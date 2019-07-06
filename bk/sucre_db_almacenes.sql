-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 06-07-2019 a las 13:16:12
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sucre_db_almacenes`
--

DELIMITER $$
--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `f_nombre_unidad_medida` (`_id` TINYINT) RETURNS VARCHAR(35) CHARSET utf8mb4 COLLATE utf8mb4_spanish2_ci begin
  declare nombre_unidad_medida varchar(35);
  
  set nombre_unidad_medida = (select descripcion  
  from unidad_medida
  where id = _id);
return nombre_unidad_medida;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `arqueo`
--

CREATE TABLE `arqueo` (
  `id` smallint(6) NOT NULL,
  `fecha_apertura` datetime NOT NULL,
  `fecha_cierre` datetime DEFAULT NULL,
  `caja_inicial` decimal(13,4) DEFAULT NULL,
  `importe_cierre` decimal(13,4) DEFAULT NULL,
  `estado` char(1) CHARACTER SET utf8 DEFAULT NULL,
  `id_terminal` tinyint(4) NOT NULL,
  `id_lugar` tinyint(4) NOT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) CHARACTER SET utf8 DEFAULT 'sys'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `arqueo`
--

INSERT INTO `arqueo` (`id`, `fecha_apertura`, `fecha_cierre`, `caja_inicial`, `importe_cierre`, `estado`, `id_terminal`, `id_lugar`, `fecha_hora_registro`, `usuario`) VALUES
(1, '2019-06-18 10:27:06', '2019-06-18 11:47:39', '100.0000', '-5765.5000', 'C', 2, 2, '2019-06-18 14:27:06', 'juan'),
(2, '2019-06-18 11:58:05', '2019-06-20 00:29:43', '200.0000', '236.0000', 'C', 2, 2, '2019-06-18 15:58:05', 'juan'),
(3, '2019-06-20 01:57:43', '2019-06-20 10:48:09', '200.0000', '204.5000', 'C', 2, 2, '2019-06-20 05:57:43', 'juan'),
(4, '2019-07-05 11:10:32', NULL, '100.0000', NULL, 'A', 2, 2, '2019-07-05 15:10:32', 'juan');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caja`
--

CREATE TABLE `caja` (
  `id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `importe` decimal(13,4) DEFAULT NULL,
  `id_transaccion` bigint(20) DEFAULT NULL,
  `nro_cobro` int(11) DEFAULT NULL,
  `nro_pago` int(11) DEFAULT NULL,
  `estado` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `id_arqueo` int(11) DEFAULT NULL,
  `fechahoraregistro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `caja`
--

INSERT INTO `caja` (`id`, `fecha`, `importe`, `id_transaccion`, `nro_cobro`, `nro_pago`, `estado`, `id_arqueo`, `fechahoraregistro`, `usuario`) VALUES
(1, '2019-06-18', '5870.0000', 1, 0, 0, 'C', 1, '2019-06-18 15:06:22', 'juan'),
(2, '2019-06-18', '22.5000', 3, 0, 0, 'C', 1, '2019-06-18 15:25:22', 'juan'),
(3, '2019-06-18', '4.5000', 5, 0, 0, 'C', 1, '2019-06-18 15:39:11', 'juan'),
(4, '2019-06-18', '22.5000', 7, 0, 0, 'C', 1, '2019-06-18 15:39:48', 'juan'),
(6, '2019-06-20', '36.0000', 11, 0, 0, 'C', 2, '2019-06-20 04:28:37', 'juan'),
(7, '2019-06-20', '4.5000', 13, 0, 0, 'C', 3, '2019-06-20 05:58:06', 'juan'),
(8, '2019-07-05', '1000.0000', 15, 0, 0, 'A', NULL, '2019-07-05 15:12:53', 'juan'),
(9, '2019-07-05', '4.5000', 17, 0, 0, 'A', NULL, '2019-07-05 15:13:36', 'juan'),
(10, '2019-07-05', '165.0000', 15, 0, 0, 'A', NULL, '2019-07-05 15:15:49', 'juan'),
(11, '2019-07-05', '1000.0000', 19, 0, 0, 'A', NULL, '2019-07-05 15:24:09', 'juan'),
(12, '2019-07-05', '1000.0000', 19, 0, 0, 'A', NULL, '2019-07-05 15:24:18', 'juan'),
(13, '2019-07-05', '330.0000', 19, 0, 0, 'A', NULL, '2019-07-05 15:24:29', 'juan'),
(14, '2019-07-05', '1165.0000', 21, 0, 0, 'A', NULL, '2019-07-05 15:47:18', 'juan');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente_proveedor`
--

CREATE TABLE `cliente_proveedor` (
  `id` int(11) NOT NULL,
  `tipo` char(1) COLLATE utf8mb4_spanish2_ci NOT NULL DEFAULT '0',
  `cedula_identidad` varchar(10) COLLATE utf8mb4_spanish2_ci NOT NULL DEFAULT '0',
  `nombre_completo` varchar(70) COLLATE utf8mb4_spanish2_ci NOT NULL DEFAULT '0',
  `razon_social` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL DEFAULT '0',
  `nit` bigint(20) NOT NULL DEFAULT '0',
  `direccion` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL DEFAULT '0',
  `telefonos` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL DEFAULT '0',
  `otros_datos` longtext COLLATE utf8mb4_spanish2_ci,
  `estado` char(1) COLLATE utf8mb4_spanish2_ci NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `cliente_proveedor`
--

INSERT INTO `cliente_proveedor` (`id`, `tipo`, `cedula_identidad`, `nombre_completo`, `razon_social`, `nit`, `direccion`, `telefonos`, `otros_datos`, `estado`) VALUES
(2, 'C', '3770080', 'LIMBERG JUSTINIANO', 'LA SOLUCION', 3770080010, 'AV LA BARRANCA 163', '77086093', 'TELEFONO FIJO 32868877, PERSONAL HABILITADAS PARA COMPRAS TATIANA NUÑEZ', 'V'),
(3, 'C', '4577252', 'MARIA TATIANA NUÑEZ', 'FOXCOMPUTERED SRL', 214912029, 'AV. LA  BARRANCA CALLE LEONOR RIBERA 163', '76030490', 'OFICINA: CALLE COBIJA N° 695, FOXCOMPUTERED SRL, EMPRESA DE SERVICIOS INFORMATICOS', 'V');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `id_transaccion` int(11) NOT NULL,
  `id_proveedor` int(11) DEFAULT NULL,
  `nombre` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `tipo_compra` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `factura` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuraciones`
--

CREATE TABLE `configuraciones` (
  `id_configuracion` tinyint(20) UNSIGNED NOT NULL,
  `ruta_visor_pdf` text COLLATE utf8mb4_spanish2_ci,
  `ruta_destino_archivos_pdf` text COLLATE utf8mb4_spanish2_ci,
  `solo_guadar_archivos_pdf` tinyint(1) DEFAULT '0',
  `ruta_programas_pg` text COLLATE utf8mb4_spanish2_ci,
  `ruta_excel` varchar(70) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `tiempo_anulacion_transaccion` int(11) DEFAULT NULL,
  `impresion_directa_factura` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `configuraciones`
--

INSERT INTO `configuraciones` (`id_configuracion`, `ruta_visor_pdf`, `ruta_destino_archivos_pdf`, `solo_guadar_archivos_pdf`, `ruta_programas_pg`, `ruta_excel`, `tiempo_anulacion_transaccion`, `impresion_directa_factura`) VALUES
(1, '/Users/jcapax/Desktop', '/Users/jcapax/Desktop', 0, '/Users/jcapax/Desktop', '/Users/jcapax/Desktop', 3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credito`
--

CREATE TABLE `credito` (
  `id_transaccion` int(11) NOT NULL,
  `id_cliente_proveedor` int(11) NOT NULL,
  `detalle` varchar(100) COLLATE utf8mb4_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `credito`
--

INSERT INTO `credito` (`id_transaccion`, `id_cliente_proveedor`, `detalle`) VALUES
(7, 3, ''),
(9, 3, ''),
(15, 3, 'PAGARA MANANA LA PRIMERA CUOTA'),
(19, 2, 'DE TODAS TODAS'),
(21, 2, 'QWQW');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_transaccion`
--

CREATE TABLE `detalle_transaccion` (
  `id` bigint(20) NOT NULL,
  `id_transaccion` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `id_unidad_medida` int(11) DEFAULT NULL,
  `cantidad` decimal(9,2) DEFAULT NULL,
  `valor_unitario` decimal(13,4) DEFAULT NULL,
  `valor_total` decimal(13,4) DEFAULT NULL,
  `tipo_valor` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `detalle_transaccion`
--

INSERT INTO `detalle_transaccion` (`id`, `id_transaccion`, `id_producto`, `id_unidad_medida`, `cantidad`, `valor_unitario`, `valor_total`, `tipo_valor`) VALUES
(1, 1, 3, 4, '10.00', '4.5000', '45.0000', 'N'),
(2, 1, 2, 1, '5.00', '1165.0000', '5825.0000', 'N'),
(3, 2, 3, 4, '10.00', '4.5000', '45.0000', 'N'),
(4, 2, 2, 1, '5.00', '1165.0000', '5825.0000', 'N'),
(5, 3, 3, 4, '5.00', '4.5000', '22.5000', 'N'),
(6, 4, 3, 4, '5.00', '4.5000', '22.5000', 'N'),
(7, 5, 3, 4, '1.00', '4.5000', '4.5000', 'N'),
(8, 6, 3, 4, '1.00', '4.5000', '4.5000', 'N'),
(9, 7, 3, 4, '5.00', '4.5000', '22.5000', 'N'),
(10, 8, 3, 4, '5.00', '4.5000', '22.5000', 'N'),
(11, 9, 3, 4, '1.00', '1.0000', '1.0000', 'N'),
(12, 10, 3, 4, '1.00', '1.0000', '1.0000', 'N'),
(13, 11, 3, 4, '8.00', '4.5000', '36.0000', 'N'),
(14, 12, 3, 4, '8.00', '4.5000', '36.0000', 'N'),
(15, 13, 3, 4, '1.00', '4.5000', '4.5000', 'N'),
(16, 14, 3, 4, '1.00', '4.5000', '4.5000', 'N'),
(17, 15, 2, 1, '1.00', '1165.0000', '1165.0000', 'N'),
(18, 16, 2, 1, '1.00', '1165.0000', '1165.0000', 'N'),
(19, 17, 3, 4, '1.00', '4.5000', '4.5000', 'N'),
(20, 18, 3, 4, '1.00', '4.5000', '4.5000', 'N'),
(21, 19, 2, 1, '2.00', '1165.0000', '2330.0000', 'N'),
(22, 20, 2, 1, '2.00', '1165.0000', '2330.0000', 'N'),
(23, 21, 2, 1, '1.00', '1165.0000', '1165.0000', 'N'),
(24, 22, 2, 1, '1.00', '1165.0000', '1165.0000', 'N');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dosificacion`
--

CREATE TABLE `dosificacion` (
  `id` tinyint(4) UNSIGNED NOT NULL,
  `llave_dosificacion` varchar(150) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `fecha` date NOT NULL,
  `id_sucursal` int(10) UNSIGNED NOT NULL,
  `nro_autorizacion` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `nro_inicio_factura` int(10) UNSIGNED NOT NULL,
  `fecha_limite_emision` date NOT NULL,
  `pie_factura` varchar(300) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `estado` tinyint(1) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `dosificacion`
--

INSERT INTO `dosificacion` (`id`, `llave_dosificacion`, `fecha`, `id_sucursal`, `nro_autorizacion`, `nro_inicio_factura`, `fecha_limite_emision`, `pie_factura`, `estado`) VALUES
(2, '23432453425324234234#@$#$%^@#$!@#$#54r345345', '2019-06-24', 3, '123123123', 1, '2019-07-05', 'nada mas simple nada mas simple', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrega_transaccion`
--

CREATE TABLE `entrega_transaccion` (
  `id_entrega_transaccion` int(11) NOT NULL,
  `id_transaccion` int(11) NOT NULL,
  `nro_entrega` int(11) NOT NULL,
  `nro_recepcion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `entrega_transaccion`
--

INSERT INTO `entrega_transaccion` (`id_entrega_transaccion`, `id_transaccion`, `nro_entrega`, `nro_recepcion`) VALUES
(2, 1, 0, 0),
(4, 3, 0, 0),
(6, 5, 0, 0),
(8, 7, 0, 0),
(10, 9, 0, 0),
(12, 11, 0, 0),
(14, 13, 0, 0),
(16, 15, 0, 0),
(18, 17, 0, 0),
(20, 19, 0, 0),
(22, 21, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equivalencia`
--

CREATE TABLE `equivalencia` (
  `id` int(11) NOT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `id_unidad_medida` int(11) DEFAULT NULL,
  `id_unidad_medida_equivalente` int(11) DEFAULT NULL,
  `equivalencia` double DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura_compra`
--

CREATE TABLE `factura_compra` (
  `id` int(10) UNSIGNED NOT NULL,
  `especificacion` tinyint(3) UNSIGNED NOT NULL,
  `fecha_factura` date NOT NULL,
  `nit` bigint(20) UNSIGNED NOT NULL,
  `razón_social` varchar(85) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `nro_factura` bigint(20) UNSIGNED NOT NULL,
  `nro_dui` varchar(15) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `nro_autorizacion` varchar(30) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `importe_total` decimal(10,4) NOT NULL,
  `importe_no_sujeto` decimal(10,4) NOT NULL,
  `sub_total` decimal(10,4) NOT NULL,
  `descuentos` decimal(10,4) NOT NULL,
  `importe_base_credito_fiscal` decimal(10,4) NOT NULL,
  `credito_fiscal` decimal(10,4) NOT NULL,
  `código_control` varchar(15) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `tipo_compra` tinyint(4) DEFAULT '1',
  `id_transaccion` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura_venta`
--

CREATE TABLE `factura_venta` (
  `id` int(10) UNSIGNED NOT NULL,
  `id_sucursal` int(10) UNSIGNED DEFAULT NULL,
  `especificacion` tinyint(3) UNSIGNED NOT NULL,
  `correlativo_sucursal` int(10) UNSIGNED NOT NULL,
  `fecha_factura` date NOT NULL,
  `nro_factura` bigint(20) UNSIGNED NOT NULL,
  `nro_autorizacion` varchar(30) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `estado` char(1) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `nit` bigint(20) UNSIGNED NOT NULL,
  `razon_social` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `importe_total` double(13,4) NOT NULL,
  `importe_ice` decimal(13,4) NOT NULL,
  `importe_exportaciones` decimal(13,4) NOT NULL,
  `importe_ventas_tasa_cero` decimal(13,4) NOT NULL,
  `importe_sub_total` decimal(13,4) NOT NULL,
  `importe_rebajas` decimal(13,4) NOT NULL,
  `importe_base_debito_fiscal` decimal(13,4) NOT NULL,
  `debito_fiscal` decimal(13,4) NOT NULL,
  `codigo_control` varchar(15) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `id_transaccion` int(10) UNSIGNED NOT NULL,
  `fecha_limite_emision` date NOT NULL,
  `codigo_qr` longblob,
  `id_dosificacion` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `factura_venta`
--

INSERT INTO `factura_venta` (`id`, `id_sucursal`, `especificacion`, `correlativo_sucursal`, `fecha_factura`, `nro_factura`, `nro_autorizacion`, `estado`, `nit`, `razon_social`, `importe_total`, `importe_ice`, `importe_exportaciones`, `importe_ventas_tasa_cero`, `importe_sub_total`, `importe_rebajas`, `importe_base_debito_fiscal`, `debito_fiscal`, `codigo_control`, `id_transaccion`, `fecha_limite_emision`, `codigo_qr`, `id_dosificacion`) VALUES
(1, 1, 1, 1, '2019-06-20', 1, '118401700002092', 'V', 123456, 'OSCAR MARTINEZ', 36.0000, '0.0000', '0.0000', '0.0000', '36.0000', '0.0000', '36.0000', '4.6800', '20-15-4B-98', 11, '2018-11-27', NULL, 1),
(2, 3, 1, 1, '2019-07-05', 1, '123123123', 'V', 0, 'SIN NOMBRE', 4.5000, '0.0000', '0.0000', '0.0000', '4.5000', '0.0000', '4.5000', '0.5850', 'C6-B8-64-97', 17, '2019-07-05', NULL, 2),
(3, 1, 1, 1, '2019-07-05', 2, '123123123', 'V', 0, 'SIN NOMBRE', 22.5000, '0.0000', '0.0000', '0.0000', '22.5000', '0.0000', '22.5000', '2.9250', '88-47-16-86', 7, '2019-07-05', NULL, 1),
(4, 1, 1, 1, '2019-07-05', 3, '123123123', 'V', 0, 'SIN NOMBRE', 1165.0000, '0.0000', '0.0000', '0.0000', '1165.0000', '0.0000', '1165.0000', '151.4500', 'E2-64-0F-C9', 15, '2019-07-05', NULL, 1),
(5, 1, 1, 1, '2019-07-05', 4, '123123123', 'V', 3655453, 'JUAN CARLOS PORCEL', 2330.0000, '0.0000', '0.0000', '0.0000', '2330.0000', '0.0000', '2330.0000', '302.9000', '71-05-38-F0', 19, '2019-07-05', NULL, 1),
(6, 3, 1, 1, '2019-07-05', 5, '123123123', 'V', 0, 'SIN NOMBRE', 1165.0000, '0.0000', '0.0000', '0.0000', '1165.0000', '0.0000', '1165.0000', '151.4500', '02-F0-2F-10', 21, '2019-07-05', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `familia`
--

CREATE TABLE `familia` (
  `id` tinyint(4) NOT NULL,
  `nombre_familia` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `orden` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `familia_producto`
--

CREATE TABLE `familia_producto` (
  `id` smallint(6) NOT NULL,
  `id_familia` tinyint(4) DEFAULT NULL,
  `id_producto` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE `lugar` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(30) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `tipo_lugar` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `lugar`
--

INSERT INTO `lugar` (`id`, `descripcion`, `tipo_lugar`, `fecha_hora_registro`, `usuario`) VALUES
(2, 'CENTRAL CHIRIGUANO', NULL, '2018-07-21 20:09:23', 'sis');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`id`, `descripcion`, `fecha_hora_registro`, `usuario`) VALUES
(1, 'HP', '2018-07-21 20:11:58', 'SYS'),
(2, 'LENOVO', '2018-07-21 20:12:07', 'SYS'),
(3, 'INTEL', '2018-07-21 20:12:12', 'SYS'),
(4, 'EPSON', '2018-07-21 20:12:20', 'SYS'),
(5, 'CANON', '2018-09-08 20:38:25', 'SYS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permisos`
--

CREATE TABLE `permisos` (
  `id_permiso` bigint(20) UNSIGNED NOT NULL,
  `nombre_permiso` varchar(40) COLLATE utf8mb4_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `permisos`
--

INSERT INTO `permisos` (`id_permiso`, `nombre_permiso`) VALUES
(1, 'menu_procesos'),
(2, 'menu_configuracion'),
(3, 'menu_administracion'),
(4, 'menu_informes'),
(5, 'menu_procesos_ventas'),
(6, 'menu_configuracion_marcas'),
(7, 'menu_configuracion_procedencia'),
(8, 'menu_configuracion_rubros'),
(9, 'menu_configuracion_unidad_medida'),
(10, 'menu_configuracion_productos'),
(11, 'menu_ventas'),
(12, 'menu_compras');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id` int(11) NOT NULL,
  `clase_persona` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `nit` varchar(12) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `nombre` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `tipo_persona` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `nombre_representante` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `lugar` varchar(12) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `direccion` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `telefono` varchar(12) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `celular` varchar(10) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `limite_credito` double DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plan_pago`
--

CREATE TABLE `plan_pago` (
  `id` smallint(6) NOT NULL,
  `id_transaccion` bigint(20) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `importe_pagar` double DEFAULT NULL,
  `estado` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `procedencia`
--

CREATE TABLE `procedencia` (
  `id` smallint(6) NOT NULL,
  `descripcion` varchar(30) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `procedencia`
--

INSERT INTO `procedencia` (`id`, `descripcion`, `fecha_hora_registro`, `usuario`) VALUES
(2, 'CHINA', '2018-07-21 20:12:37', 'SYS'),
(3, 'BRASIL', '2018-07-21 20:12:44', 'SYS'),
(4, 'CHILE', '2018-07-21 20:12:48', 'SYS'),
(5, 'USA', '2018-09-08 20:38:52', 'SYS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `clase_producto` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `código_barras` varchar(35) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `id_rubro_producto` int(11) DEFAULT NULL,
  `id_marca` int(11) DEFAULT NULL,
  `id_procedencia` smallint(6) DEFAULT NULL,
  `descripcion` varchar(100) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `tipo_cuenta` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `estado` varchar(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `imagen` blob,
  `recargo` smallint(6) DEFAULT '0',
  `control_stock` smallint(6) DEFAULT '0',
  `caducidad` tinyint(4) NOT NULL DEFAULT '0',
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `clase_producto`, `código_barras`, `id_rubro_producto`, `id_marca`, `id_procedencia`, `descripcion`, `tipo_cuenta`, `estado`, `imagen`, `recargo`, `control_stock`, `caducidad`, `fecha_hora_registro`, `usuario`) VALUES
(2, NULL, NULL, 2, 1, 4, 'IMPRESORA EPSON L395', NULL, 'V', NULL, 0, 1, 0, '2018-07-21 20:16:27', 'juan'),
(3, NULL, NULL, 2, 1, 2, 'CABLE UTP CAT 5 CAJA 305 MTS', NULL, 'V', NULL, 0, 1, 1, '2018-07-21 20:20:04', 'juan'),
(4, NULL, NULL, 2, 3, 2, 'COOLERS VENTILADORES DE CASE', NULL, 'V', NULL, 0, 0, 0, '2018-07-21 20:23:47', 'juan'),
(5, NULL, NULL, 2, 2, 2, 'PORTATIL CORE I5 4GB RAM 1 TB DISCO', NULL, 'V', NULL, 0, 1, 0, '2018-07-21 20:31:30', 'juan'),
(6, NULL, NULL, 2, 5, 5, 'MULTIFUNCIONAL IR 1024J', NULL, 'V', NULL, 0, 1, 0, '2018-09-08 20:44:14', 'juan'),
(7, 'M', NULL, NULL, NULL, NULL, 'APORTE PERSONAL', 'I', 'V', NULL, 0, 0, 0, '2018-09-08 21:31:25', 'sis'),
(8, 'M', NULL, NULL, NULL, NULL, 'USO DE BAÑO ', 'I', 'V', NULL, 0, 0, 0, '2018-09-08 21:32:12', 'sis'),
(9, 'M', NULL, NULL, NULL, NULL, 'PAGO DE SERVICIOS ', 'E', 'V', NULL, 0, 0, 0, '2018-09-08 21:33:35', 'sis'),
(10, 'M', NULL, NULL, NULL, NULL, 'PAGO DE BENEFICIOS EMPLEADOS', 'E', 'V', NULL, 0, 0, 0, '2018-09-08 21:35:08', 'sis'),
(11, NULL, NULL, 4, 1, 2, 'ELECTRODOS', NULL, 'V', NULL, 0, 1, 1, '2019-07-04 21:39:47', 'juan');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id` smallint(6) NOT NULL,
  `nit_proveedor` varchar(15) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `nombre_proveedor` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `razón_social_proveedor` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `tipo_productos_venta` varchar(100) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `lugar_proveedor` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `direccion` varchar(100) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `telefonos` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` tinyint(4) NOT NULL,
  `nombre_rol` varchar(25) COLLATE utf8mb4_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `nombre_rol`) VALUES
(1, 'administrador'),
(2, 'empleado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles_permisos`
--

CREATE TABLE `roles_permisos` (
  `id_rol_permiso` bigint(20) UNSIGNED NOT NULL,
  `id_rol` int(11) NOT NULL,
  `id_permiso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `roles_permisos`
--

INSERT INTO `roles_permisos` (`id_rol_permiso`, `id_rol`, `id_permiso`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 2, 1),
(12, 2, 11),
(13, 1, 11),
(14, 2, 12),
(15, 1, 12),
(16, 1, 13),
(17, 2, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rubro_producto`
--

CREATE TABLE `rubro_producto` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `id_padre` int(11) NOT NULL DEFAULT '-1',
  `id_imagen` smallint(6) DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `rubro_producto`
--

INSERT INTO `rubro_producto` (`id`, `descripcion`, `id_padre`, `id_imagen`, `fecha_hora_registro`, `usuario`) VALUES
(2, 'TECNOLOGIA', -1, NULL, '2018-07-21 20:13:06', 'SYS'),
(3, 'ELECTRODOMESTICO', -1, NULL, '2018-07-21 20:13:17', 'SYS'),
(4, 'FERRETERIA', -1, NULL, '2018-07-21 20:13:25', 'SYS'),
(5, 'TELEFONIA Y REDES', -1, NULL, '2018-09-08 20:39:31', 'SYS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `id` int(10) UNSIGNED NOT NULL,
  `nit_sucursal` varchar(15) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `nombre_sucursal` varchar(45) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `direccion` varchar(100) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `tipo_productos` text COLLATE utf8mb4_spanish2_ci NOT NULL,
  `id_lugar` tinyint(4) DEFAULT NULL,
  `actividad_economica` varchar(145) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `estado` tinyint(3) UNSIGNED NOT NULL,
  `sucursal_actividad` varchar(45) COLLATE utf8mb4_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`id`, `nit_sucursal`, `nombre_sucursal`, `direccion`, `tipo_productos`, `id_lugar`, `actividad_economica`, `estado`, `sucursal_actividad`) VALUES
(1, '37123456', 'CASA MATRIZ', '3 ER ANILLO COMERCIAL CHIRIGUANO PASILLO 4 LOCAL 112', 'COMPUTADORAS, PORTATILES E IMPRESORAS', 2, 'VENTA Y DISTRIBUCION DE EQUIPOS INFORMATICOS', 1, 'INSUMOS INFORMATICOS'),
(2, '123', 'QW', 'QQ', 'QW', 2, 'QQ', 1, 'qw'),
(3, '123123', 'WW', 'WW', '', 2, 'WW', 1, 'ww');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `terminal`
--

CREATE TABLE `terminal` (
  `id` int(11) NOT NULL,
  `id_lugar` int(11) NOT NULL,
  `descripcion` varchar(50) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `terminal`
--

INSERT INTO `terminal` (`id`, `id_lugar`, `descripcion`, `fecha_hora_registro`, `usuario`) VALUES
(1, 2, 'TatianaNunez', '2018-07-21 20:10:43', 'SYS'),
(2, 2, 'jcporcel.local', '2018-07-21 21:00:01', 'SYS'),
(3, 2, 'xp32-PC', '2018-07-21 21:23:50', 'SYS'),
(4, 2, 'sids-me', '2018-09-07 14:25:40', 'SYS'),
(5, 2, 'sistemas', '2018-09-11 15:47:37', 'SYS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_transaccion`
--

CREATE TABLE `tipo_transaccion` (
  `id` int(11) NOT NULL,
  `tipo_movimiento` int(11) DEFAULT NULL,
  `clase_movimiento` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `descripcion` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `tipo_transaccion`
--

INSERT INTO `tipo_transaccion` (`id`, `tipo_movimiento`, `clase_movimiento`, `descripcion`, `fecha_hora_registro`, `usuario`) VALUES
(1, -1, 'D', 'COMPRA', '2017-07-14 04:52:23', 'SIS'),
(2, 1, 'D', 'VENTA', '2017-07-14 04:52:48', 'SIS'),
(3, 1, 'D', 'PEDIDO', '2017-07-14 04:53:24', 'SIS'),
(4, -1, 'D', 'EGRESO', '2017-07-14 04:53:51', 'SIS'),
(5, 1, 'D', 'INGRESO', '2017-07-14 04:53:51', 'SIS'),
(6, 1, 'D', 'SALDO INICIAL', '2017-07-14 04:54:24', 'SIS'),
(7, 1, 'M', 'RECEPCION', '2017-07-14 04:54:52', 'SIS'),
(8, -1, 'M', 'ENTREGA', '2017-07-14 04:54:52', 'SIS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transaccion`
--

CREATE TABLE `transaccion` (
  `id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `id_tipo_transaccion` int(11) DEFAULT NULL,
  `nro_tipo_transaccion` int(11) DEFAULT NULL,
  `id_lugar` int(11) DEFAULT NULL,
  `id_terminal` int(11) DEFAULT NULL,
  `tipo_movimiento` int(11) DEFAULT NULL,
  `estado` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `descripcion_transaccion` varchar(45) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `transaccion`
--

INSERT INTO `transaccion` (`id`, `fecha`, `id_tipo_transaccion`, `nro_tipo_transaccion`, `id_lugar`, `id_terminal`, `tipo_movimiento`, `estado`, `descripcion_transaccion`, `fecha_hora_registro`, `usuario`) VALUES
(1, '2019-06-18', 1, 1, 2, 2, -1, 'C', 'JUAN', '2019-06-18 15:06:22', 'juan'),
(2, '2019-06-18', 7, 1, 2, 2, 1, 'C', 'JUAN', '2019-06-18 15:06:22', 'juan'),
(3, '2019-06-18', 1, 2, 2, 2, -1, 'C', 'CARLOS', '2019-06-18 15:25:22', 'juan'),
(4, '2019-06-18', 7, 2, 2, 2, 1, 'C', 'CARLOS', '2019-06-18 15:25:22', 'juan'),
(5, '2019-06-18', 2, 1, 2, 2, 1, 'C', 'SIN NOMBRE', '2019-06-18 15:39:11', 'juan'),
(6, '2019-06-18', 8, 1, 2, 2, -1, 'C', 'SIN NOMBRE', '2019-06-18 15:39:11', 'juan'),
(7, '2019-06-18', 2, 2, 2, 2, 1, 'C', 'JUAN CARLOS PORCEL', '2019-06-18 15:39:48', 'juan'),
(8, '2019-06-18', 8, 2, 2, 2, -1, 'C', 'JUAN CARLOS PORCEL', '2019-06-18 15:39:48', 'juan'),
(9, '2019-06-20', 6, 1, 2, 2, 1, 'C', 'AJUSTE DE STOCK POR DEVOLUCION PASADA', '2019-06-20 04:23:57', 'juan'),
(10, '2019-06-20', 7, 3, 2, 2, 1, 'C', 'AJUSTE DE STOCK POR DEVOLUCION PASADA', '2019-06-20 04:23:57', 'juan'),
(11, '2019-06-20', 2, 3, 2, 2, 1, 'C', 'OSCAR MARTINEZ', '2019-06-20 04:28:37', 'juan'),
(12, '2019-06-20', 8, 3, 2, 2, -1, 'C', 'OSCAR MARTINEZ', '2019-06-20 04:28:37', 'juan'),
(13, '2019-06-20', 2, 4, 2, 2, 1, 'C', 'SIN NOMBRE', '2019-06-20 05:58:06', 'juan'),
(14, '2019-06-20', 8, 4, 2, 2, -1, 'C', 'SIN NOMBRE', '2019-06-20 05:58:06', 'juan'),
(15, '2019-07-05', 3, 1, 2, 2, 1, 'A', '', '2019-07-05 15:11:02', 'juan'),
(16, '2019-07-05', 8, 5, 2, 2, -1, 'A', '', '2019-07-05 15:11:02', 'juan'),
(17, '2019-07-05', 2, 5, 2, 2, 1, 'A', 'SIN NOMBRE', '2019-07-05 15:13:36', 'juan'),
(18, '2019-07-05', 8, 6, 2, 2, -1, 'A', 'SIN NOMBRE', '2019-07-05 15:13:36', 'juan'),
(19, '2019-07-05', 3, 2, 2, 2, 1, 'A', '', '2019-07-05 15:23:40', 'juan'),
(20, '2019-07-05', 8, 7, 2, 2, -1, 'A', '', '2019-07-05 15:23:40', 'juan'),
(21, '2019-07-05', 3, 3, 2, 2, 1, 'A', '', '2019-07-05 15:47:05', 'juan'),
(22, '2019-07-05', 8, 8, 2, 2, -1, 'A', '', '2019-07-05 15:47:05', 'juan');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad_medida`
--

CREATE TABLE `unidad_medida` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `simbolo` varchar(6) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `unidad_medida`
--

INSERT INTO `unidad_medida` (`id`, `descripcion`, `simbolo`, `fecha_hora_registro`, `usuario`) VALUES
(1, 'PIEZA', 'PZA', '2018-07-21 20:13:46', 'SYS'),
(2, 'CAJA', 'CJA', '2018-07-21 20:13:56', 'SYS'),
(3, 'UNIDAD', 'UND', '2018-07-21 20:14:16', 'SYS'),
(4, 'METROS', 'MTS', '2018-09-08 20:40:17', 'SYS'),
(5, '', '', '2019-05-14 19:45:41', 'SYS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad_producto`
--

CREATE TABLE `unidad_producto` (
  `id` int(11) NOT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `id_unidad_medida` int(11) DEFAULT NULL,
  `unidad_principal` smallint(6) DEFAULT NULL,
  `stock_minimo` decimal(9,2) DEFAULT NULL,
  `precio_venta` decimal(13,4) DEFAULT NULL,
  `precio_venta_rebaja` decimal(13,4) DEFAULT NULL,
  `precio_venta_aumento` double(13,4) DEFAULT NULL,
  `precio_compra` decimal(13,4) DEFAULT NULL,
  `garantia_meses` int(11) NOT NULL DEFAULT '0',
  `actualizacion` smallint(6) DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `unidad_producto`
--

INSERT INTO `unidad_producto` (`id`, `id_producto`, `id_unidad_medida`, `unidad_principal`, `stock_minimo`, `precio_venta`, `precio_venta_rebaja`, `precio_venta_aumento`, `precio_compra`, `garantia_meses`, `actualizacion`, `fecha_hora_registro`, `usuario`) VALUES
(3, 2, 1, 1, '5.00', '1165.0000', '0.0000', 0.0000, '980.0000', 0, 1, '2018-07-21 20:18:33', 'SYS'),
(4, 3, 2, 0, '10.00', '420.0000', '0.0000', 0.0000, '310.0000', 0, 1, '2018-07-21 20:21:40', ''),
(5, 4, 1, 1, '100.00', '20.0000', '0.0000', 0.0000, '5.0000', 0, 1, '2018-07-21 20:27:24', 'SYS'),
(6, 5, 1, 1, '10.00', '6000.0000', '0.0000', 0.0000, '5000.0000', 0, 1, '2018-07-21 20:33:07', 'SYS'),
(7, 6, 1, 1, '40.00', '6250.0000', '0.0000', 0.0000, '5000.0000', 0, 1, '2018-09-08 20:47:16', 'SYS'),
(8, 3, 4, 0, '100.00', '4.5000', '0.0000', 0.0000, '3.0000', 0, 1, '2018-09-10 21:00:36', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `login` varchar(5) COLLATE utf8mb4_spanish2_ci NOT NULL,
  `nombres` varchar(25) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `apellidos` varchar(30) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `direccion` varchar(50) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `telefono` varchar(12) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `pass` varchar(40) COLLATE utf8mb4_spanish2_ci DEFAULT NULL,
  `rol` tinyint(10) DEFAULT NULL,
  `fecha_hora_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `estado` char(1) COLLATE utf8mb4_spanish2_ci DEFAULT 'v',
  `usuario_registro` varchar(5) COLLATE utf8mb4_spanish2_ci DEFAULT 'sis'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish2_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`login`, `nombres`, `apellidos`, `direccion`, `telefono`, `pass`, `rol`, `fecha_hora_registro`, `estado`, `usuario_registro`) VALUES
('ivi', NULL, NULL, NULL, NULL, 'b52053511c4b2af9a68a0ece64d99556', 2, '2019-06-20 04:50:24', 'V', 'sis'),
('juan', NULL, NULL, NULL, NULL, '302d57b3f88079c9643beb5ad275f340', 1, '2017-08-02 19:33:34', 'V', 'sis');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vencimiento`
--

CREATE TABLE `vencimiento` (
  `id` int(11) NOT NULL,
  `id_transaccion` int(11) NOT NULL,
  `id_producto` smallint(6) NOT NULL,
  `id_unidad_medida` tinyint(4) NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `cantidad` decimal(9,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `vencimiento`
--

INSERT INTO `vencimiento` (`id`, `id_transaccion`, `id_producto`, `id_unidad_medida`, `fecha_vencimiento`, `cantidad`) VALUES
(1, 2, 3, 4, '2019-06-30', '10.00'),
(2, 4, 3, 4, '2019-06-25', '5.00'),
(3, 6, 3, 4, '2019-06-25', '1.00'),
(4, 8, 3, 4, '2019-06-25', '4.00'),
(5, 8, 3, 4, '2019-06-30', '1.00'),
(6, 10, 3, 4, '2019-07-01', '1.00'),
(7, 12, 3, 4, '2019-06-30', '8.00'),
(8, 14, 3, 4, '2019-06-30', '1.00'),
(9, 18, 3, 4, '2019-07-01', '1.00');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_caja`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_caja` (
`id_transaccion` int(11)
,`fecha_caja` date
,`importe_caja` decimal(23,4)
,`estado` char(1)
,`movimiento` varchar(50)
,`descripcion_transaccion` varchar(45)
,`id_arqueo` int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_credito`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_credito` (
`id_transaccion` int(11)
,`fecha` date
,`nro_tipo_transaccion` int(11)
,`valor_total_transaccion` decimal(35,4)
,`fecha_hora_registro` timestamp
,`usuario` varchar(5)
,`descripcion` varchar(100)
,`simbolo` varchar(6)
,`cantidad` decimal(9,2)
,`valor_unitario` decimal(13,4)
,`valor_total` decimal(13,4)
,`detalle` varchar(100)
,`cedula_identidad` varchar(10)
,`nit` bigint(20)
,`nombre_completo` varchar(70)
,`razon_social` varchar(50)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_detalle_transaccion`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_detalle_transaccion` (
`id` bigint(20)
,`id_transaccion` int(11)
,`id_producto` int(11)
,`descripcion` varchar(100)
,`id_unidad_medida` int(11)
,`descripcion_medida` varchar(50)
,`simbolo` varchar(6)
,`cantidad` decimal(9,2)
,`valor_unitario` decimal(13,4)
,`valor_total` decimal(13,4)
,`tipo_valor` char(1)
,`recargo` smallint(6)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_factura`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_factura` (
`id_transaccion` int(11)
,`id_producto` int(11)
,`nombre_producto` varchar(100)
,`simbolo` varchar(6)
,`cantidad` decimal(9,2)
,`valor_unitario` decimal(13,4)
,`valor_total` decimal(13,4)
,`tipo_valor` char(1)
,`recargo` smallint(6)
,`id_sucursal` int(10) unsigned
,`especificacion` tinyint(3) unsigned
,`correlativo_sucursal` int(10) unsigned
,`fecha_factura` date
,`nro_factura` bigint(20) unsigned
,`nro_autorizacion` varchar(30)
,`estado` char(1)
,`nit` bigint(20) unsigned
,`razon_social` varchar(45)
,`importe_total` double(13,4)
,`importe_ice` decimal(13,4)
,`importe_exportaciones` decimal(13,4)
,`importe_ventas_tasa_cero` decimal(13,4)
,`importe_sub_total` decimal(13,4)
,`importe_rebajas` decimal(13,4)
,`importe_base_debito_fiscal` decimal(13,4)
,`debito_fiscal` decimal(13,4)
,`codigo_control` varchar(15)
,`fecha_limite_emision` date
,`id_dosificacion` smallint(6)
,`pie_factura` varchar(300)
,`nombre_sucursal` varchar(45)
,`actividad_economica` varchar(145)
,`nit_sucursal` varchar(15)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_lista_movimiento`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_lista_movimiento` (
`id_tipo_transaccion` int(11)
,`clase_movimiento` char(1)
,`descripcion_tipo_transaccion` varchar(50)
,`fecha` date
,`estado` char(1)
,`valor_total` decimal(35,4)
,`descripcion` varchar(100)
,`simbolo` varchar(6)
,`cantidad` decimal(31,2)
,`valor_total_producto` decimal(35,4)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_movimiento_transaccion`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_movimiento_transaccion` (
`descripcion` varchar(100)
,`simbolo` varchar(6)
,`cantidad` decimal(31,2)
,`valor_total` decimal(35,4)
,`fecha` date
,`id_tipo_transaccion` int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_pago_credito`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_pago_credito` (
`id_transaccion` int(11)
,`fecha` date
,`nro_tipo_transaccion` int(11)
,`importe_caja` decimal(35,4)
,`valor_total` decimal(35,4)
,`diferencia` decimal(36,4)
,`detalle` varchar(100)
,`cedula_identidad` varchar(10)
,`nit` bigint(20)
,`nombre_completo` varchar(70)
,`razon_social` varchar(50)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_pago_transaccion`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_pago_transaccion` (
`id_transaccion` int(11)
,`fecha` date
,`id_tipo_transaccion` int(11)
,`nro_tipo_transaccion` int(11)
,`importe_caja` decimal(35,4)
,`valor_total` decimal(35,4)
,`diferencia` decimal(36,4)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_pendiente_pago`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_pendiente_pago` (
`id_transaccion` int(11)
,`fecha` date
,`id_tipo_transaccion` int(11)
,`nro_tipo_transaccion` int(11)
,`importe_caja` decimal(35,4)
,`valor_total` decimal(35,4)
,`diferencia` decimal(36,4)
,`detalle` varchar(100)
,`nombre_completo` varchar(70)
,`razon_social` varchar(50)
,`telefonos` varchar(50)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_productos`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_productos` (
`id_unidad_producto` int(11)
,`id_producto` int(11)
,`id_rubro_producto` int(11)
,`rubro` varchar(50)
,`id_marca` int(11)
,`marca` varchar(50)
,`id_procedencia` smallint(6)
,`procedencia` varchar(30)
,`descripcion` varchar(100)
,`estado` varchar(1)
,`control_stock` smallint(6)
,`caducidad` tinyint(4)
,`id_unidad_medida` int(11)
,`nombre_unidad_medida` varchar(35)
,`unidad_principal` smallint(6)
,`nombre_unidad_principal` varchar(35)
,`stock_minimo` decimal(9,2)
,`precio_venta` decimal(13,4)
,`precio_venta_rebaja` decimal(13,4)
,`precio_venta_aumento` double(13,4)
,`precio_compra` decimal(13,4)
,`actualizacion` smallint(6)
,`garantia_meses` int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_stock`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_stock` (
`id_lugar` int(11)
,`id_producto` int(11)
,`id_unidad_medida` int(11)
,`stock` decimal(41,2)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_stock_vencimiento`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_stock_vencimiento` (
`id_lugar` int(11)
,`id_producto` smallint(6)
,`nombre_producto` varchar(100)
,`id_unidad_medida` tinyint(4)
,`nombre_unidad_medida` varchar(35)
,`fecha_vencimiento` date
,`stock` decimal(41,2)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `v_transaccion`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `v_transaccion` (
`id` int(11)
,`clase_movimiento` char(1)
,`descripcion_tipo_transaccion` varchar(50)
,`fecha` date
,`id_tipo_transaccion` int(11)
,`nro_tipo_transaccion` int(11)
,`id_lugar` int(11)
,`id_terminal` int(11)
,`tipo_movimiento` int(11)
,`estado` char(1)
,`valor_total` decimal(35,4)
,`fecha_hora_registro` timestamp
,`usuario` varchar(5)
,`descripcion_transaccion` varchar(45)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `v_caja`
--
DROP TABLE IF EXISTS `v_caja`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_caja`  AS  select `t`.`id` AS `id_transaccion`,`c`.`fecha` AS `fecha_caja`,(`c`.`importe` * `tt`.`tipo_movimiento`) AS `importe_caja`,`c`.`estado` AS `estado`,`tt`.`descripcion` AS `movimiento`,`t`.`descripcion_transaccion` AS `descripcion_transaccion`,`c`.`id_arqueo` AS `id_arqueo` from ((`caja` `c` join `transaccion` `t` on((`c`.`id_transaccion` = `t`.`id`))) join `tipo_transaccion` `tt` on((`t`.`id_tipo_transaccion` = `tt`.`id`))) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_credito`
--
DROP TABLE IF EXISTS `v_credito`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_credito`  AS  select `t`.`id` AS `id_transaccion`,`t`.`fecha` AS `fecha`,`t`.`nro_tipo_transaccion` AS `nro_tipo_transaccion`,`t`.`valor_total` AS `valor_total_transaccion`,`t`.`fecha_hora_registro` AS `fecha_hora_registro`,`t`.`usuario` AS `usuario`,`d`.`descripcion` AS `descripcion`,`d`.`simbolo` AS `simbolo`,`d`.`cantidad` AS `cantidad`,`d`.`valor_unitario` AS `valor_unitario`,`d`.`valor_total` AS `valor_total`,`c`.`detalle` AS `detalle`,`cp`.`cedula_identidad` AS `cedula_identidad`,`cp`.`nit` AS `nit`,`cp`.`nombre_completo` AS `nombre_completo`,`cp`.`razon_social` AS `razon_social` from (((`v_transaccion` `t` join `v_detalle_transaccion` `d` on((`t`.`id` = `d`.`id_transaccion`))) join `credito` `c` on((`t`.`id` = `c`.`id_transaccion`))) join `cliente_proveedor` `cp` on((`c`.`id_cliente_proveedor` = `cp`.`id`))) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_detalle_transaccion`
--
DROP TABLE IF EXISTS `v_detalle_transaccion`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_detalle_transaccion`  AS  select `d`.`id` AS `id`,`d`.`id_transaccion` AS `id_transaccion`,`d`.`id_producto` AS `id_producto`,`p`.`descripcion` AS `descripcion`,`d`.`id_unidad_medida` AS `id_unidad_medida`,`u`.`descripcion` AS `descripcion_medida`,`u`.`simbolo` AS `simbolo`,`d`.`cantidad` AS `cantidad`,`d`.`valor_unitario` AS `valor_unitario`,`d`.`valor_total` AS `valor_total`,`d`.`tipo_valor` AS `tipo_valor`,`p`.`recargo` AS `recargo` from ((`detalle_transaccion` `d` join `producto` `p` on((`d`.`id_producto` = `p`.`id`))) left join `unidad_medida` `u` on((`d`.`id_unidad_medida` = `u`.`id`))) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_factura`
--
DROP TABLE IF EXISTS `v_factura`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_factura`  AS  select `d`.`id_transaccion` AS `id_transaccion`,`d`.`id_producto` AS `id_producto`,`d`.`descripcion` AS `nombre_producto`,`d`.`simbolo` AS `simbolo`,`d`.`cantidad` AS `cantidad`,`d`.`valor_unitario` AS `valor_unitario`,`d`.`valor_total` AS `valor_total`,`d`.`tipo_valor` AS `tipo_valor`,`d`.`recargo` AS `recargo`,`f`.`id_sucursal` AS `id_sucursal`,`f`.`especificacion` AS `especificacion`,`f`.`correlativo_sucursal` AS `correlativo_sucursal`,`f`.`fecha_factura` AS `fecha_factura`,`f`.`nro_factura` AS `nro_factura`,`f`.`nro_autorizacion` AS `nro_autorizacion`,`f`.`estado` AS `estado`,`f`.`nit` AS `nit`,`f`.`razon_social` AS `razon_social`,`f`.`importe_total` AS `importe_total`,`f`.`importe_ice` AS `importe_ice`,`f`.`importe_exportaciones` AS `importe_exportaciones`,`f`.`importe_ventas_tasa_cero` AS `importe_ventas_tasa_cero`,`f`.`importe_sub_total` AS `importe_sub_total`,`f`.`importe_rebajas` AS `importe_rebajas`,`f`.`importe_base_debito_fiscal` AS `importe_base_debito_fiscal`,`f`.`debito_fiscal` AS `debito_fiscal`,`f`.`codigo_control` AS `codigo_control`,`f`.`fecha_limite_emision` AS `fecha_limite_emision`,`f`.`id_dosificacion` AS `id_dosificacion`,`o`.`pie_factura` AS `pie_factura`,`s`.`nombre_sucursal` AS `nombre_sucursal`,`s`.`actividad_economica` AS `actividad_economica`,`s`.`nit_sucursal` AS `nit_sucursal` from (((`v_detalle_transaccion` `d` join `factura_venta` `f` on((`d`.`id_transaccion` = `f`.`id_transaccion`))) join `dosificacion` `o` on((`o`.`id` = `f`.`id_dosificacion`))) join `sucursal` `s` on((`s`.`id` = `o`.`id_sucursal`))) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_lista_movimiento`
--
DROP TABLE IF EXISTS `v_lista_movimiento`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_lista_movimiento`  AS  select `t`.`id_tipo_transaccion` AS `id_tipo_transaccion`,`t`.`clase_movimiento` AS `clase_movimiento`,`t`.`descripcion_tipo_transaccion` AS `descripcion_tipo_transaccion`,`t`.`fecha` AS `fecha`,`t`.`estado` AS `estado`,`t`.`valor_total` AS `valor_total`,`d`.`descripcion` AS `descripcion`,`d`.`simbolo` AS `simbolo`,sum(`d`.`cantidad`) AS `cantidad`,sum(`d`.`valor_total`) AS `valor_total_producto` from (`v_transaccion` `t` join `v_detalle_transaccion` `d` on((`t`.`id` = `d`.`id_transaccion`))) group by `t`.`id_tipo_transaccion`,`t`.`clase_movimiento`,`t`.`descripcion_tipo_transaccion`,`t`.`fecha`,`t`.`estado`,`t`.`valor_total`,`d`.`descripcion`,`d`.`simbolo` ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_movimiento_transaccion`
--
DROP TABLE IF EXISTS `v_movimiento_transaccion`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_movimiento_transaccion`  AS  select `d`.`descripcion` AS `descripcion`,`d`.`simbolo` AS `simbolo`,sum(`d`.`cantidad`) AS `cantidad`,sum(`d`.`valor_total`) AS `valor_total`,`t`.`fecha` AS `fecha`,`t`.`id_tipo_transaccion` AS `id_tipo_transaccion` from (`v_detalle_transaccion` `d` join `transaccion` `t` on((`t`.`id` = `d`.`id_transaccion`))) group by `d`.`descripcion`,`d`.`simbolo`,`t`.`fecha`,`t`.`id_tipo_transaccion` ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_pago_credito`
--
DROP TABLE IF EXISTS `v_pago_credito`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_pago_credito`  AS  select `p`.`id_transaccion` AS `id_transaccion`,`p`.`fecha` AS `fecha`,`p`.`nro_tipo_transaccion` AS `nro_tipo_transaccion`,`p`.`importe_caja` AS `importe_caja`,`p`.`valor_total` AS `valor_total`,`p`.`diferencia` AS `diferencia`,`c`.`detalle` AS `detalle`,`cp`.`cedula_identidad` AS `cedula_identidad`,`cp`.`nit` AS `nit`,`cp`.`nombre_completo` AS `nombre_completo`,`cp`.`razon_social` AS `razon_social` from ((`v_pago_transaccion` `p` join `credito` `c` on((`p`.`id_transaccion` = `c`.`id_transaccion`))) join `cliente_proveedor` `cp` on((`c`.`id_cliente_proveedor` = `cp`.`id`))) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_pago_transaccion`
--
DROP TABLE IF EXISTS `v_pago_transaccion`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_pago_transaccion`  AS  select `vt`.`id` AS `id_transaccion`,`vt`.`fecha` AS `fecha`,`vt`.`id_tipo_transaccion` AS `id_tipo_transaccion`,`vt`.`nro_tipo_transaccion` AS `nro_tipo_transaccion`,sum(ifnull(`c`.`importe`,0)) AS `importe_caja`,`vt`.`valor_total` AS `valor_total`,(`vt`.`valor_total` - sum(ifnull(`c`.`importe`,0))) AS `diferencia` from (`v_transaccion` `vt` left join `caja` `c` on(((`c`.`id_transaccion` = `vt`.`id`) and (`vt`.`estado` <> 'n')))) where ((`vt`.`estado` <> 'n') and (`vt`.`id_tipo_transaccion` in (1,2,3,4))) group by `vt`.`valor_total`,`vt`.`fecha`,`vt`.`id`,`vt`.`id_tipo_transaccion`,`vt`.`nro_tipo_transaccion` order by `vt`.`id` ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_pendiente_pago`
--
DROP TABLE IF EXISTS `v_pendiente_pago`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_pendiente_pago`  AS  select `v`.`id_transaccion` AS `id_transaccion`,`v`.`fecha` AS `fecha`,`v`.`id_tipo_transaccion` AS `id_tipo_transaccion`,`v`.`nro_tipo_transaccion` AS `nro_tipo_transaccion`,`v`.`importe_caja` AS `importe_caja`,`v`.`valor_total` AS `valor_total`,`v`.`diferencia` AS `diferencia`,`c`.`detalle` AS `detalle`,`cp`.`nombre_completo` AS `nombre_completo`,`cp`.`razon_social` AS `razon_social`,`cp`.`telefonos` AS `telefonos` from ((`v_pago_transaccion` `v` join `credito` `c` on((`v`.`id_transaccion` = `c`.`id_transaccion`))) join `cliente_proveedor` `cp` on((`cp`.`id` = `c`.`id_cliente_proveedor`))) where (`v`.`diferencia` <> 0) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_productos`
--
DROP TABLE IF EXISTS `v_productos`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_productos`  AS  select `up`.`id` AS `id_unidad_producto`,`p`.`id` AS `id_producto`,`p`.`id_rubro_producto` AS `id_rubro_producto`,`r`.`descripcion` AS `rubro`,`p`.`id_marca` AS `id_marca`,`m`.`descripcion` AS `marca`,`p`.`id_procedencia` AS `id_procedencia`,`pr`.`descripcion` AS `procedencia`,`p`.`descripcion` AS `descripcion`,`p`.`estado` AS `estado`,`p`.`control_stock` AS `control_stock`,`p`.`caducidad` AS `caducidad`,`up`.`id_unidad_medida` AS `id_unidad_medida`,`f_nombre_unidad_medida`(`up`.`id_unidad_medida`) AS `nombre_unidad_medida`,`up`.`unidad_principal` AS `unidad_principal`,`f_nombre_unidad_medida`(`up`.`unidad_principal`) AS `nombre_unidad_principal`,`up`.`stock_minimo` AS `stock_minimo`,`up`.`precio_venta` AS `precio_venta`,`up`.`precio_venta_rebaja` AS `precio_venta_rebaja`,`up`.`precio_venta_aumento` AS `precio_venta_aumento`,`up`.`precio_compra` AS `precio_compra`,`up`.`actualizacion` AS `actualizacion`,`up`.`garantia_meses` AS `garantia_meses` from ((((`producto` `p` join `rubro_producto` `r` on((`p`.`id_rubro_producto` = `r`.`id`))) join `marca` `m` on((`p`.`id_marca` = `m`.`id`))) join `procedencia` `pr` on((`pr`.`id` = `p`.`id_procedencia`))) left join `unidad_producto` `up` on((`p`.`id` = `up`.`id_producto`))) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_stock`
--
DROP TABLE IF EXISTS `v_stock`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_stock`  AS  select `t`.`id_lugar` AS `id_lugar`,`d`.`id_producto` AS `id_producto`,`d`.`id_unidad_medida` AS `id_unidad_medida`,sum((`t`.`tipo_movimiento` * `d`.`cantidad`)) AS `stock` from (`detalle_transaccion` `d` join `transaccion` `t` on((`d`.`id_transaccion` = `t`.`id`))) where ((`t`.`id_tipo_transaccion` in (7,8)) and (`t`.`estado` <> 'n')) group by `t`.`id_lugar`,`d`.`id_producto`,`d`.`id_unidad_medida` ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_stock_vencimiento`
--
DROP TABLE IF EXISTS `v_stock_vencimiento`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_stock_vencimiento`  AS  select `t`.`id_lugar` AS `id_lugar`,`v`.`id_producto` AS `id_producto`,`p`.`descripcion` AS `nombre_producto`,`v`.`id_unidad_medida` AS `id_unidad_medida`,`p`.`nombre_unidad_medida` AS `nombre_unidad_medida`,`v`.`fecha_vencimiento` AS `fecha_vencimiento`,sum((`v`.`cantidad` * `t`.`tipo_movimiento`)) AS `stock` from ((`vencimiento` `v` join `v_productos` `p` on(((`v`.`id_producto` = `p`.`id_producto`) and (`v`.`id_unidad_medida` = `p`.`id_unidad_medida`)))) join `transaccion` `t` on((`v`.`id_transaccion` = `t`.`id`))) where (`t`.`estado` <> 'N') group by `t`.`id_lugar`,`v`.`id_producto`,`p`.`descripcion`,`v`.`id_unidad_medida`,`p`.`nombre_unidad_medida`,`v`.`fecha_vencimiento` having (sum((`v`.`cantidad` * `t`.`tipo_movimiento`)) > 0) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `v_transaccion`
--
DROP TABLE IF EXISTS `v_transaccion`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transaccion`  AS  select `t`.`id` AS `id`,`tt`.`clase_movimiento` AS `clase_movimiento`,`tt`.`descripcion` AS `descripcion_tipo_transaccion`,`t`.`fecha` AS `fecha`,`t`.`id_tipo_transaccion` AS `id_tipo_transaccion`,`t`.`nro_tipo_transaccion` AS `nro_tipo_transaccion`,`t`.`id_lugar` AS `id_lugar`,`t`.`id_terminal` AS `id_terminal`,`t`.`tipo_movimiento` AS `tipo_movimiento`,`t`.`estado` AS `estado`,sum(`d`.`valor_total`) AS `valor_total`,`t`.`fecha_hora_registro` AS `fecha_hora_registro`,`t`.`usuario` AS `usuario`,`t`.`descripcion_transaccion` AS `descripcion_transaccion` from ((`transaccion` `t` join `detalle_transaccion` `d` on((`t`.`id` = `d`.`id_transaccion`))) join `tipo_transaccion` `tt` on((`t`.`id_tipo_transaccion` = `tt`.`id`))) group by `t`.`id`,`tt`.`clase_movimiento`,`tt`.`descripcion`,`t`.`fecha`,`t`.`id_tipo_transaccion`,`t`.`nro_tipo_transaccion`,`t`.`id_lugar`,`t`.`id_terminal`,`t`.`tipo_movimiento`,`t`.`estado`,`t`.`fecha_hora_registro`,`t`.`usuario` ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `arqueo`
--
ALTER TABLE `arqueo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `caja`
--
ALTER TABLE `caja`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cliente_proveedor`
--
ALTER TABLE `cliente_proveedor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id_transaccion`);

--
-- Indices de la tabla `configuraciones`
--
ALTER TABLE `configuraciones`
  ADD PRIMARY KEY (`id_configuracion`);

--
-- Indices de la tabla `credito`
--
ALTER TABLE `credito`
  ADD PRIMARY KEY (`id_transaccion`,`id_cliente_proveedor`);

--
-- Indices de la tabla `detalle_transaccion`
--
ALTER TABLE `detalle_transaccion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `dosificacion`
--
ALTER TABLE `dosificacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `entrega_transaccion`
--
ALTER TABLE `entrega_transaccion`
  ADD PRIMARY KEY (`id_entrega_transaccion`);

--
-- Indices de la tabla `equivalencia`
--
ALTER TABLE `equivalencia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `factura_compra`
--
ALTER TABLE `factura_compra`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `factura_venta`
--
ALTER TABLE `factura_venta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `familia`
--
ALTER TABLE `familia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `familia_producto`
--
ALTER TABLE `familia_producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `lugar`
--
ALTER TABLE `lugar`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `procedencia`
--
ALTER TABLE `procedencia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles_permisos`
--
ALTER TABLE `roles_permisos`
  ADD PRIMARY KEY (`id_rol_permiso`);

--
-- Indices de la tabla `rubro_producto`
--
ALTER TABLE `rubro_producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `terminal`
--
ALTER TABLE `terminal`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `transaccion`
--
ALTER TABLE `transaccion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `unidad_medida`
--
ALTER TABLE `unidad_medida`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `unidad_producto`
--
ALTER TABLE `unidad_producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`login`);

--
-- Indices de la tabla `vencimiento`
--
ALTER TABLE `vencimiento`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `arqueo`
--
ALTER TABLE `arqueo`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `caja`
--
ALTER TABLE `caja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `cliente_proveedor`
--
ALTER TABLE `cliente_proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id_transaccion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `configuraciones`
--
ALTER TABLE `configuraciones`
  MODIFY `id_configuracion` tinyint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalle_transaccion`
--
ALTER TABLE `detalle_transaccion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `dosificacion`
--
ALTER TABLE `dosificacion`
  MODIFY `id` tinyint(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `entrega_transaccion`
--
ALTER TABLE `entrega_transaccion`
  MODIFY `id_entrega_transaccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `equivalencia`
--
ALTER TABLE `equivalencia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura_compra`
--
ALTER TABLE `factura_compra`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura_venta`
--
ALTER TABLE `factura_venta`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `familia`
--
ALTER TABLE `familia`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `familia_producto`
--
ALTER TABLE `familia_producto`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `lugar`
--
ALTER TABLE `lugar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `procedencia`
--
ALTER TABLE `procedencia`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` smallint(6) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rubro_producto`
--
ALTER TABLE `rubro_producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `transaccion`
--
ALTER TABLE `transaccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `unidad_medida`
--
ALTER TABLE `unidad_medida`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `unidad_producto`
--
ALTER TABLE `unidad_producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `vencimiento`
--
ALTER TABLE `vencimiento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
