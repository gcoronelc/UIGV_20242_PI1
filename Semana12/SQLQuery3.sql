select * from estudiante;
select * from prestamo;
select * from multa;
select * from ESTADO;
select * from SITUACION;
select * from EJEMPLAR;


select count(1) filas from PRESTAMO 
where IDPRESTAMO=? and IDESTADO=1

select DATEDIFF(DAY,FECHA_FIN, GETDATE()) dias 
from PRESTAMO where IDPRESTAMO = ?;

select idEjemplar  from PRESTAMO;

update EJEMPLAR set IDSITUACION=? wwhere IDEJEMPLAR=?


update PRESTAMO set IDESTADO=?, FECHA_DEVOLUCION=GETDATE() 
where IDPRESTAMO=?;


insert into multa(idprestamo,dias,importe,fecha)
values(?,?,?,GETDATE())
