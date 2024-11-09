package pe.edu.uni.biblioteca.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.edu.uni.biblioteca.db.AccesoDB;
import pe.edu.uni.biblioteca.dto.UsuarioDto;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class LogonService {
	
	
	public UsuarioDto validarUsuario(String usuario, String clave){
		UsuarioDto dto = new UsuarioDto();
		Connection cn = null;
		PreparedStatement pstm;
		ResultSet rs;
		String sql = """
               select u.IDEMPLEADO, u.IDROL, u.USUARIO, r.NOMBRE
               from usuario u 
               join rol r on u.IDROL = r.IDROL
               where u.USUARIO = ? and u.CLAVE=? and u.ACTIVO=1
               """;
		try {
			// Conexion con la BD
			cn = AccesoDB.getConnection();
			// Preparando el objeto
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, usuario);
			pstm.setString(2, clave);
			// Ejecutar el objeto
			rs = pstm.executeQuery();
			// Verificar resultado
			if(!rs.next()){
				throw new SQLException("Datos incorrectos.");
			}
			// Obtener datos del usuario
			dto.setIdEmpleado(rs.getInt("IDEMPLEADO"));
			dto.setIdRol(rs.getInt("IDROL"));
			dto.setNombRol(rs.getString("NOMBRE"));
			dto.setUsuario(rs.getString("USUARIO"));
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
		return dto;
	}

}
