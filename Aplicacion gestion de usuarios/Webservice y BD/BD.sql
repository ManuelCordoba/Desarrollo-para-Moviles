-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-10-2019 a las 01:19:18
-- Versión del servidor: 10.4.6-MariaDB
-- Versión de PHP: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `encuesta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hist_ses`
--

CREATE TABLE `hist_ses` (
  `id_hist` int(10) NOT NULL,
  `id_usu` int(10) NOT NULL,
  `in_f_sesion` varchar(15) NOT NULL,
  `in_h_sesion` varchar(15) NOT NULL,
  `out_f_sesion` varchar(15) DEFAULT NULL,
  `out_h_sesion` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `hist_ses`
--

INSERT INTO `hist_ses` (`id_hist`, `id_usu`, `in_f_sesion`, `in_h_sesion`, `out_f_sesion`, `out_h_sesion`) VALUES
(1, 2, '25/09/2019', '05:20', '25/09/2019', '05:20'),
(2, 3, '25/09/2019', '16:45', '25/09/2019', '16:45'),
(3, 2, '25/09/2019', '16:45', NULL, ''),
(4, 3, '25/09/2019', '16:46', NULL, ''),
(5, 2, '26/09/2019', '22:41', NULL, ''),
(6, 2, '28/09/2019', '23:25', NULL, ''),
(7, 2, '28/09/2019', '23:46', '28/09/2019', '23:46'),
(8, 2, '28/09/2019', '23:48', NULL, ''),
(9, 9, '29/09/2019', '00:04', '29/09/2019', '00:04'),
(10, 2, '29/09/2019', '00:04', NULL, ''),
(11, 2, '29/09/2019', '00:08', NULL, ''),
(12, 9, '29/09/2019', '00:09', '29/09/2019', '00:09'),
(13, 2, '29/09/2019', '00:09', NULL, ''),
(14, 2, '29/09/2019', '00:10', NULL, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permiso`
--

CREATE TABLE `permiso` (
  `id_permiso` int(11) NOT NULL,
  `nom_permiso` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `permiso`
--

INSERT INTO `permiso` (`id_permiso`, `nom_permiso`) VALUES
(1, 'Hacer encuesta'),
(2, 'Revisar encuesta'),
(3, 'Administrar'),
(4, 'Editar perfil');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permiso_de_rol`
--

CREATE TABLE `permiso_de_rol` (
  `id_rol` int(11) NOT NULL,
  `id_permiso` int(11) NOT NULL,
  `id_per_rol` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `permiso_de_rol`
--

INSERT INTO `permiso_de_rol` (`id_rol`, `id_permiso`, `id_per_rol`) VALUES
(0, 3, 3),
(0, 4, 4),
(1, 4, 5),
(1, 1, 11),
(1, 3, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id_rol` int(10) NOT NULL,
  `nombre_rol` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id_rol`, `nombre_rol`) VALUES
(0, 'Administrador'),
(1, 'Usuario'),
(2, 'Suplente'),
(3, 'contable');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usu` int(20) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `nombre_usu` varchar(45) NOT NULL,
  `apellido_usu` varchar(45) NOT NULL,
  `email_usu` varchar(45) NOT NULL,
  `id_rol` int(10) NOT NULL,
  `estado` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usu`, `username`, `password`, `nombre_usu`, `apellido_usu`, `email_usu`, `id_rol`, `estado`) VALUES
(2, 'admin', 'bI5JiZ4TY3kPdYnuaScAqQ==', 'manuel', 'cordoba', 'manuelogo19@gmail.com', 0, 1),
(3, 'user', '3mopJSmftdeARCda6NEVSg==', 'Andres', 'Castellano', 'andres', 1, 1),
(8, 'pipe', '3mopJSmftdeARCda6NEVSg==', 'Felipe', 'Torres', 'felipe@', 1, 1),
(9, 'aaa', '5PJX1/1/m4UuqS/6s2DJmQ==', 'dasdsa', 'dsas', 'fasdas', 1, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `hist_ses`
--
ALTER TABLE `hist_ses`
  ADD PRIMARY KEY (`id_hist`);

--
-- Indices de la tabla `permiso`
--
ALTER TABLE `permiso`
  ADD PRIMARY KEY (`id_permiso`);

--
-- Indices de la tabla `permiso_de_rol`
--
ALTER TABLE `permiso_de_rol`
  ADD PRIMARY KEY (`id_per_rol`),
  ADD KEY `id_rol` (`id_rol`),
  ADD KEY `id_permiso` (`id_permiso`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usu`),
  ADD KEY `id_rol` (`id_rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `hist_ses`
--
ALTER TABLE `hist_ses`
  MODIFY `id_hist` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `permiso_de_rol`
--
ALTER TABLE `permiso_de_rol`
  MODIFY `id_per_rol` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usu` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `permiso_de_rol`
--
ALTER TABLE `permiso_de_rol`
  ADD CONSTRAINT `permiso_de_rol_ibfk_1` FOREIGN KEY (`id_permiso`) REFERENCES `permiso` (`id_permiso`) ON UPDATE CASCADE,
  ADD CONSTRAINT `permiso_de_rol_ibfk_2` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
