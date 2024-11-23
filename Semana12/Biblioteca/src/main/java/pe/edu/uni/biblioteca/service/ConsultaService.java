package pe.edu.uni.biblioteca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.uni.biblioteca.db.AccesoDB;
import pe.edu.uni.biblioteca.dto.EjemplarDto;
import pe.edu.uni.biblioteca.dto.EstudianteDto;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class ConsultaService {

	public EstudianteDto consultarEstudiante(String codigo){
		EstudianteDto bean = null;
		Connection cn = null;
		PreparedStatement pstm;
		ResultSet rs;
		String sql = """
			select idestudiante, apellido, nombre from ESTUDIANTE where codigo=?
		""";
		try {
			// Conexion con la BD
			cn = AccesoDB.getConnection();
			// Preparando el objeto
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, codigo);
			// Ejecutar el objeto
			rs = pstm.executeQuery();
			// Verificar resultado
			if(!rs.next()){
				throw new SQLException("Estudiante no existe.");
			}
			// Obtener datos del usuario
			bean = new EstudianteDto();
			bean.setIdestudiante(rs.getLong("idestudiante"));
			bean.setApellido(rs.getString("apellido"));
			bean.setNombre(rs.getString("nombre"));
			bean.setCodigo(codigo.toUpperCase());
			rs.close();
			pstm.close();		
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Error en el proceso.");
		} finally{
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
		return bean;
	}
	
	public List<EjemplarDto> consultarEjemplares(String titulo){
		List<EjemplarDto> lista = new ArrayList<>();
		Connection cn = null;
		PreparedStatement pstm;
		ResultSet rs;
		String sql = """
			select E.IDEJEMPLAR, E.CODIGO, L.TITULO,
         E.EDITORIAL, E.EDICION, E.ANIO
         from LIBRO L 
         join EJEMPLAR E on L.IDLIBRO = E.IDLIBRO
         where E.IDSITUACION = 1 and L.TITULO like ?
		""";
		try {
			// Conexion con la BD
			cn = AccesoDB.getConnection();
			// Preparando el objeto
			pstm = cn.prepareStatement(sql);
			titulo = "%" + titulo.trim() + "%";
			pstm.setString(1, titulo);
			// Ejecutar el objeto
			rs = pstm.executeQuery();
			// Verificar resultado
			while(rs.next()){
				EjemplarDto bean = new EjemplarDto();
				bean.setIdejemplar(rs.getLong("IDEJEMPLAR"));
				bean.setCodigo(rs.getString("CODIGO"));
				bean.setTitulo(rs.getString("TITULO"));
				bean.setEditorial(rs.getString("EDITORIAL"));
				bean.setEdicion(rs.getInt("EDICION"));
				bean.setAnio(rs.getInt("ANIO"));
				lista.add(bean);
			}
			rs.close();
			pstm.close();		
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Error en el proceso.");
		} finally{
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
		return lista;
	}
}
