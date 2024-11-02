USE EUREKABANK;
GO

CREATE PROCEDURE usp_deposito( 
	@cuenta char(8), 
	@importe decimal(12,2),  
	@empleado char(4),
	@estado int output
)
AS
BEGIN

	DECLARE @moneda char(2);
	DECLARE @costoMov decimal(12,2);
	DECLARE @cont int;

	BEGIN TRY
	
		BEGIN TRANSACTION;
	
		-- Tabla Cuenta
		select @cont = int_cuencontmov, @moneda = chr_monecodigo 
		from cuenta where chr_cuencodigo = @cuenta;
	
		select @costoMov = dec_costimporte
		from costomovimiento
		where chr_monecodigo = @moneda;
	
		-- Actualiza la cuenta
		update cuenta
			set dec_cuensaldo = dec_cuensaldo + @importe - @costoMov,
				int_cuencontmov = int_cuencontmov + 2
			where chr_cuencodigo = @cuenta;

		-- Registrar el movimiento
		set @cont = @cont + 1;	
		insert into movimiento(chr_cuencodigo,int_movinumero,dtt_movifecha,
			chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia)
			values(@cuenta,@cont,getdate(),@empleado,'003',@importe,null);

		-- Registrar el costo del movimiento
		set @cont = @cont + 1;
		insert into movimiento(chr_cuencodigo,int_movinumero,dtt_movifecha,
			chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia)
			values(@cuenta,@cont,getdate(),@empleado,'010',@costoMov,null);
		
		-- Confirma Transacción
		COMMIT;

		-- Retorna el estado
		set @estado = 1;
	END TRY 
	BEGIN CATCH 
		ROLLBACK TRANSACTION; 
		set @estado = -1;
	END CATCH;
	
END;
GO

select * from cuenta where chr_cuencodigo = '00100002';
select * from Movimiento where chr_cuencodigo = '00100002';
go



DECLARE @estado int
exec usp_deposito '00100002', 500, '0001', @estado output;
print @estado;
go

