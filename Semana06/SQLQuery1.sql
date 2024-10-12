
declare @idcurso int;
set @idcurso = 1;
select * from CURSO where cur_id=@idcurso;
select * from MATRICULA where cur_id=@idcurso;
go

declare @idcurso int, @idalumno int;
set @idcurso = 1;
set @idalumno = 5;
select * from CURSO where cur_id=@idcurso;
select * from MATRICULA where cur_id=@idcurso and alu_id=@idalumno;
select * from PAGO where cur_id=@idcurso and alu_id=@idalumno;
go










