CREATE TABLE IF NOT EXISTS `credito` (
  `idTransaccion` int(11) NOT NULL,
  `idClienteProveedor` int(11) NOT NULL,
  `detalle` varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idTransaccion`,`idClienteProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;


CREATE TABLE `clienteProveedor` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`tipo` CHAR(1) NOT NULL DEFAULT '0',
	`cedulaIdentidad` VARCHAR(10) NOT NULL DEFAULT '0',
	`nombreCompleto` VARCHAR(70) NOT NULL DEFAULT '0',
	`razonSocial` VARCHAR(50) NOT NULL DEFAULT '0',
	`nit` TINYINT NOT NULL DEFAULT '0',
	`direccion` VARCHAR(50) NOT NULL DEFAULT '0',
	`telefonos` VARCHAR(50) NOT NULL DEFAULT '0',
	`otrosDatos` LONGTEXT NOT NULL DEFAULT '0',
	`estado` CHAR(1) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_spanish_ci'
ENGINE=InnoDB;

CREATE VIEW vPagoTransaccion
AS

SELECT vt.id as idTransaccion, vt.fecha,vt.idTipoTransaccion, vt.nroTipoTransaccion,SUM(ifnull(c.importe,0)) as ImporteCaja,
         vt.valorTotal, (vt.valorTotal - SUM(ifnull(c.importe,0))) AS diferencia
FROM caja c
    RIGHT JOIN vtransaccion vt ON c.idtransaccion = vt.id and vt.Estado<>'N'    
WHERE vt.Estado<>'N'  AND vt.idtipotransaccion IN (1, 2, 3, 4)
GROUP BY vt.valortotal, vt.fecha, vt.id, vt.idtipotransaccion, vt.nrotipotransaccion
ORDER BY vt.id


CREATE VIEW vPendientePago
AS

SELECT v.idTransaccion, v.fecha, v.idTipoTransaccion, v.nroTipoTransaccion, 
	v.ImporteCaja, v.valorTotal, v.diferencia, 
    c.Detalle, cp.nombreCompleto, cp.razonSocial, cp.telefonos
FROM vpagotransaccion v 
	join credito c on v.idTransaccion = c.idTransaccion
    join clienteProveedor cp on cp.id = c.idClienteProveedor
where diferencia <> 0

create view vCredito
as
select t.id as idTransaccion, t.fecha, t.nroTipoTransaccion, t.valorTotal as valorTotalTransaccion, t.fechaHoraRegistro, t.usuario,
	d.descripcion, d.simbolo, d.cantidad, d.valorunitario, d.valortotal,
	c.detalle, cp.cedulaIdentidad, cp.nit, cp.nombreCompleto, cp.razonSocial
from vtransaccion t 
	join vdetalletransaccion d on t.id = d.idtransaccion
	join credito c on t.id = c.idTransaccion
	join clienteproveedor cp on c.idClienteProveedor = cp.id

create view vPagoCredito
as
select p.idTransaccion, p.fecha, p.nroTipoTransaccion, p.ImporteCaja, p.valorTotal, p.diferencia,
	c.detalle, cp.cedulaIdentidad, cp.nit, cp.nombreCompleto, cp.razonSocial
from vpagotransaccion p
	join credito c on p.idTransaccion = c.idTransaccion
	join clienteproveedor cp on c.idClienteProveedor = cp.id



