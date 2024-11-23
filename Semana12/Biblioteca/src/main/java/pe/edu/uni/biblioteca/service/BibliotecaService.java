package pe.edu.uni.biblioteca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.uni.biblioteca.db.AccesoDB;
import pe.edu.uni.biblioteca.dto.PrestamoDto;

public class BibliotecaService {

	public void registrarPrestamo(PrestamoDto dto) {
		Connection cn = null;
		try {
			// La conexion
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicia la Tx
			// Validaciones
			validarEjemplar(cn, dto.getIdEjemplar());
			validarEstudiante(cn, dto.getIdEstudiante());
			validarExistePrestamo(cn, dto.getIdEstudiante());
			// Proceso
			modificarEstadoEjemplar(cn, dto.getIdEjemplar());
			insertarPrestamo(cn, dto);
			// Confirmar Tx
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback(); // Cancela la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			try {
				cn.rollback(); // Cancela la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException("Error en el proceso, intentalo mas tarde.");
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
	}

	private void validarEjemplar(Connection cn, Long idEjemplar) throws SQLException {
		String sql = """
			SELECT COUNT(1) CONT FROM EJEMPLAR 
			WHERE IDEJEMPLAR=? AND IDSITUACION=1         
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idEjemplar);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();;
		if (cont != 1) {
			throw new SQLException("Ejemplar no existe o no esta disponible.");
		}
	}

	private void validarEstudiante(Connection cn, Long idEstudiante) throws SQLException {
		String sql = """
			SELECT COUNT(1) CONT FROM ESTUDIANTE 
         WHERE IDESTUDIANTE=?        
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idEstudiante);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();;
		if (cont != 1) {
			throw new SQLException("Estudiante no existe.");
		}
	}

	private void validarExistePrestamo(Connection cn, Long idEstudiante) throws SQLException {
		String sql = """
			SELECT COUNT(1) CONT FROM PRESTAMO 
			WHERE IDESTUDIANTE=? AND IDESTADO=1        
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idEstudiante);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();;
		if (cont >= 1) {
			throw new SQLException("Estudiante tiene prestamo activo.");
		}
	}

	private void modificarEstadoEjemplar(Connection cn, Long idEjemplar) throws SQLException {
		String sql = """
			UPDATE EJEMPLAR
			SET IDSITUACION = 2
			WHERE IDEJEMPLAR = ?        
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idEjemplar);
		int filas = pstm.executeUpdate();
		if (filas != 1) {
			throw new SQLException("Error en el proceso.");
		}
	}

	private void insertarPrestamo(Connection cn, PrestamoDto dto) throws SQLException {
		String sql = """
			INSERT INTO PRESTAMO(IDEJEMPLAR,IDESTUDIANTE,IDESTADO,FECHA_INICIO,FECHA_FIN)
         VALUES(?,?,1,GETDATE(),GETDATE()+5);      
      """;
		PreparedStatement pstm = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstm.setLong(1, dto.getIdEjemplar());
		pstm.setLong(2, dto.getIdEstudiante());
		pstm.executeUpdate();
		ResultSet generatedKeys = pstm.getGeneratedKeys();
		generatedKeys.next();
		int generatedId = generatedKeys.getInt(1);
		dto.setIdPrestamo((long) generatedId);
	}

	public void registrarDevolucion(Long idPrestamo, double importe) {
		Connection cn = null;
		try {
			// La conexion
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicia la Tx
			// Validaciones
			validarPrestamo(cn, idPrestamo);
			// Datos
			int dias = calcularDias(cn, idPrestamo);
			Long idEjemplar = obtenerEjemplar(cn, idPrestamo);
			// Proceso
			double valorMulta = dias * 5.0;
			if (importe != valorMulta) {
				throw new SQLException("El valor de la multa es incorrecto.");
			}
			actualizarSituacionEjemplar(cn, idEjemplar, 1L);
			Long idEstado = ((valorMulta == 0.0) ? 2L : 3L);
			actualizarEstadoPrestamo(cn, idPrestamo, idEstado);
			if (valorMulta > 0.0) {
				registrarMulta(cn, idPrestamo, dias, valorMulta);
			}
			// Confirmar Tx
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback(); // Cancela la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			try {
				cn.rollback(); // Cancela la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException("Error en el proceso, intentalo mas tarde.");
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
	}

	private void validarPrestamo(Connection cn, Long idPrestamo) throws SQLException {
		String sql = """
			select count(1) filas from PRESTAMO 
			where IDPRESTAMO=? and IDESTADO=1   
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idPrestamo);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int filas = rs.getInt("filas");
		rs.close();
		pstm.close();;
		if (filas != 1) {
			throw new SQLException("Prestamo no existe o ya fue devuelto.");
		}
	}

	private int calcularDias(Connection cn, Long idPrestamo) throws SQLException {
		String sql = """
			select DATEDIFF(DAY,FECHA_FIN, GETDATE()) dias 
			from PRESTAMO where IDPRESTAMO = ? 
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idPrestamo);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int dias = rs.getInt("dias");
		rs.close();
		pstm.close();;
		return dias;
	}

	private Long obtenerEjemplar(Connection cn, Long idPrestamo) throws SQLException {
		String sql = """
			select idEjemplar 
			from PRESTAMO where IDPRESTAMO = ? 
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idPrestamo);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		Long idEjemplar = rs.getLong("idEjemplar");
		rs.close();
		pstm.close();;
		return idEjemplar;
	}

	private void actualizarSituacionEjemplar(Connection cn, Long idEjemplar, Long idSituacion) throws SQLException {
		String sql = "update EJEMPLAR set IDSITUACION=? where IDEJEMPLAR=?";
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idSituacion);
		pstm.setLong(2, idEjemplar);
		int filas = pstm.executeUpdate();
		if (filas != 1) {
			throw new SQLException("Error en el proceso.");
		}
	}

	private void actualizarEstadoPrestamo(Connection cn, Long idPrestamo, Long idEstado) throws SQLException {
		String sql = """
			update PRESTAMO set IDESTADO=?, FECHA_DEVOLUCION=GETDATE() 
          where IDPRESTAMO=?         
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idEstado);
		pstm.setLong(2, idPrestamo);
		int filas = pstm.executeUpdate();
		if (filas != 1) {
			throw new SQLException("Error en el proceso.");
		}
	}

	private void registrarMulta(Connection cn, Long idPrestamo, int dias, double valorMulta) throws SQLException {
		String sql = """
			insert into multa(idprestamo,dias,importe,fecha)
          values(?,?,?,GETDATE())    
      """;
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setLong(1, idPrestamo);
		pstm.setInt(2, dias);
		pstm.setDouble(3, valorMulta);
		pstm.executeUpdate();
	}
}
