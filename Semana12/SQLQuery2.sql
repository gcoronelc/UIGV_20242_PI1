
select * from estudiante;

select idestudiante, apellido, nombre from ESTUDIANTE where codigo=?;
go

select * from SITUACION;
select * from LIBRO;
select * from EJEMPLAR;
select * from PRESTAMO;
go




sp_help estudiante


private Long idejemplar;
private String codigo;
private String titulo;
private String editorial;
private Integer edicion;
private Integer anio;
go

select E.IDEJEMPLAR, E.CODIGO, L.TITULO,
E.EDITORIAL, E.EDICION, E.ANIO
from LIBRO L 
join EJEMPLAR E on L.IDLIBRO = E.IDLIBRO
where E.IDSITUACION = 1 and L.TITULO like ?;
go

