-- Crear un curso

select * from curso;
go

alter procedure usp_crear_curso
(
	@nombre varchar(100),
	@vacantes int,
	@profesor varchar(100),
	@precio money,
	@estado int output,
	@id int output
)
as
begin
	insert into curso(cur_nombre,cur_vacantes,cur_matriculados,
	cur_profesor,cur_precio)
	values(@nombre,@vacantes,0,@profesor,@precio);
	set @estado = 1;
	set @id = SCOPE_IDENTITY();
end;
go

declare @estado int, @id int;
exec usp_crear_curso 'java 2',20,'Gustavo',-1000.0, 
	@estado output, @id output;
print concat('estado: ',@estado);
print concat('id: ',@id);
go

