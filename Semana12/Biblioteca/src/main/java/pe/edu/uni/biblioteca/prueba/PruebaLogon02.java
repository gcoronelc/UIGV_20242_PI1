package pe.edu.uni.biblioteca.prueba;

import pe.edu.uni.biblioteca.dto.UsuarioDto;
import pe.edu.uni.biblioteca.service.LogonService;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class PruebaLogon02 {
	
	public static void main(String[] args) {
		
		// Usuario no se encuentra activo
		try {
			// Datos
			String usuario = "laguirre";
			String clave = "secreto";
			// Proceso
			LogonService service = new LogonService();
			UsuarioDto dto = service.validarUsuario(usuario, clave);
			// Reporte
			System.out.println(dto);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

}
