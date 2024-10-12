package pe.edu.uni.educa2024.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.edu.uni.educa2024.db.AccesoDB;
import pe.edu.uni.educa2024.dto.MatriculaDto;
import pe.edu.uni.educa2024.dto.PagoDto;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class EducaService {

	public MatriculaDto procMatricula(MatriculaDto bean) {
		Connection cn = null;
		PreparedStatement pstm;
		String sql;
		int cont;
		try {
			// Inicio de Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicio de Tx
			// Validar el curso
			double precio = validarCurso(cn, bean.getIdCurso());
			// Validar alumno
			validarAlumno(cn, bean.getIdAlumno());
			// Validar empleado
			validarEmpleado(cn, bean.getIdEmpleado());
			// Validar si ya existe la matricula
			validarMatricula(cn, bean.getIdCurso(), bean.getIdAlumno());
			// Validar tipo
			String tipo = bean.getTipo().toUpperCase().trim();
			int index = "REGULAR,MEDIABECA,BECA".indexOf(tipo);
			if (index == -1) {
				throw new SQLException("Tipo de matricula incorrecto.");
			}
			// Validar cuotas
			if (bean.getCuotas() < 1 || bean.getCuotas() > 3) {
				throw new SQLException("Número de cuotas incorrecto.");
			}
			if (!tipo.equals("REGULAR") && bean.getCuotas() != 1) {
				throw new SQLException("Número de cuotas incorrecto.");
			}
			// Determinar precio
			precio = switch (tipo) {
				case "REGULAR" ->
					precio;
				case "MEDIABECA" ->
					precio / 2;
				case "BECA" ->
					precio * 0.10;
				default ->
					precio;
			};
			// Registrar matricula
			sql = "insert into matricula(cur_id,alu_id,emp_id,mat_tipo,";
			sql += "mat_fecha,mat_precio,mat_cuotas) ";
			sql += "values(?,?,?,?,getdate(),?,?)";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, bean.getIdCurso());
			pstm.setInt(2, bean.getIdAlumno());
			pstm.setInt(3, bean.getIdEmpleado());
			pstm.setString(4, tipo);
			pstm.setDouble(5, precio);
			pstm.setInt(6, bean.getCuotas());
			pstm.executeUpdate();
			pstm.close();
			// Actualizar el curso
			sql = """
				update curso 
				set cur_matriculados = cur_matriculados + 1
				where cur_id =?
         """;
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, bean.getIdCurso());
			cont = pstm.executeUpdate();
			pstm.close();
			if (cont == 0) {
				throw new SQLException("No se pudo actualizar el curso.");
			}
			// Confirmar la Tx
			cn.commit();
			bean.setPrecio(precio);
			bean.setFecha(obtenerFecha(cn, bean));
		} catch (SQLException e) {
			try {
				cn.rollback(); // Cancelar la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			try {
				cn.rollback(); // Cancelar la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException("Error en el proceso, intentelo mas tarde!!");
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	public PagoDto procPago(PagoDto bean) {
		Connection cn = null;
		PreparedStatement pstm;
		ResultSet rs;
		String sql;
		double mat_precio;
		int mat_cuotas;
		double deuda;
		int sgteCuota;
		String mat_tipo;
		double valorCuota;
		try {
			// Inicio de Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false); // Inicio de Tx
			// Validar empleado
			validarEmpleado(cn, bean.getIdEmpleado());
			// Validar matricula
			sql = "select mat_tipo, mat_precio, mat_cuotas from matricula where cur_id=? and alu_id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, bean.getIdCurso());
			pstm.setInt(2, bean.getIdAlumno());
			rs = pstm.executeQuery();
			if (!rs.next()) {
				throw new SQLException("Matricula NO existe.");
			}
			mat_precio = rs.getDouble("mat_precio");
			mat_cuotas = rs.getInt("mat_cuotas");
			mat_tipo = rs.getString("mat_tipo");
			rs.close();
			pstm.close();
			// Verificar si tiene deuda
			deuda = varificarExisteDeuda(cn, bean, mat_precio);
			// Validar número de cuota
			sgteCuota = contarCuotas(cn, bean);
			if (bean.getCuota() != sgteCuota) {
				throw new SQLException("Número de cuota incorrecto.");
			}
			// Validar importe de cuota
			valorCuota = obtenerValorCuota(mat_precio, mat_tipo, sgteCuota);
			if(valorCuota != bean.getImporte()){
				throw new SQLException("Valor de la cuota es incorrecto.");
			}
			// Registrar pago
			sql = "insert into PAGO(cur_id,alu_id,pag_cuota,emp_id,pag_fecha,pag_importe) ";
			sql += "values(?,?,?,?,GETDATE(),?)";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, bean.getIdCurso());
			pstm.setInt(2, bean.getIdAlumno());
			pstm.setInt(3, bean.getCuota());
			pstm.setInt(4, bean.getIdEmpleado());
			pstm.setDouble(5, bean.getImporte());
			pstm.executeUpdate();
			pstm.close();
			// Confirmar la Tx
			cn.commit();
			bean.setFecha(obtenerFecha(cn,bean));
		} catch (SQLException e) {
			try {
				cn.rollback(); // Cancelar la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			try {
				cn.rollback(); // Cancelar la Tx
			} catch (Exception e1) {
			}
			throw new RuntimeException("Error en el proceso, intentelo mas tarde!!");
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
		return bean;
	}

	private double validarCurso(Connection cn, int idCurso) throws SQLException {
		String sql = "select cur_precio from  curso where cur_id=?";
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idCurso);
		ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Curso no existe.");
		}
		double precio = rs.getDouble("cur_precio");
		rs.close();
		pstm.close();
		return precio;
	}

	private void validarAlumno(Connection cn, int idAlumno) throws SQLException {
		String sql = "select count(1) cont from alumno where alu_id=?";
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idAlumno);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if (cont != 1) {
			throw new SQLException("Alumno no existe.");
		}
	}

	private void validarEmpleado(Connection cn, int idEmpleado) throws SQLException {
		String sql = "select count(1) cont from empleado where emp_id=?";
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idEmpleado);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if (cont != 1) {
			throw new SQLException("Empleado no existe.");
		}
	}

	private void validarMatricula(Connection cn, int idCurso, int idAlumno) throws SQLException {
		String sql = "select count(1) cont from matricula where cur_id=? and alu_id=?";
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, idCurso);
		pstm.setInt(2, idAlumno);
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int cont = rs.getInt("cont");
		rs.close();
		pstm.close();
		if (cont > 0) {
			throw new SQLException("Matricula ya esta registrada.");
		}
	}

	private double varificarExisteDeuda(Connection cn, PagoDto bean, double mat_precio) throws SQLException {
		String sql = "select ISNULL(sum(pag_importe),0.0) importe from pago where cur_id=? and alu_id=?";
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, bean.getIdCurso());
		pstm.setInt(2, bean.getIdAlumno());
		ResultSet rs = pstm.executeQuery();
		rs.next();
		double importe = rs.getInt("importe");
		rs.close();
		pstm.close();
		if (importe >= mat_precio) {
			throw new SQLException("No existe deuda por pagar.");
		}
		return (mat_precio - importe);
	}

	private int contarCuotas(Connection cn, PagoDto bean) throws SQLException {
		String sql = "select count(1) sgte from pago where cur_id=? and alu_id=?";
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setInt(1, bean.getIdCurso());
		pstm.setInt(2, bean.getIdAlumno());
		ResultSet rs = pstm.executeQuery();
		rs.next();
		int sgte = rs.getInt("sgte");
		rs.close();
		return (sgte + 1);
	}

	private double obtenerValorCuota(double precio, String tipo, int cuota) {
		if (tipo.equals("BECA")) {
			return precio * 0.10;
		}
		if (tipo.equals("MEDIABECA")) {
			return precio * 0.50;
		}
		double valorCuota = switch (cuota) {
			case 1 ->
				precio * 0.40;
			case 2, 3 ->
				precio * 0.30;
			default ->
				precio;
		};
		return valorCuota;
	}

	private String obtenerFecha(Connection cn, PagoDto bean) {
		return "Falta";
	}

	private String obtenerFecha(Connection cn, MatriculaDto bean) {
		return "Falta";
	}

}
