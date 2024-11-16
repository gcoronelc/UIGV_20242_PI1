package pe.edu.uni.biblioteca.prueba;

import java.util.List;
import pe.edu.uni.biblioteca.dto.EjemplarDto;
import pe.edu.uni.biblioteca.dto.UsuarioDto;
import pe.edu.uni.biblioteca.service.ConsultaService;
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
public class PruebaEjemplares01 {
	
	public static void main(String[] args) {
		
		// Usuario no existe
		try {
			// Datos
			String titulo = "años";
			// Proceso
			ConsultaService service = new ConsultaService();
			List<EjemplarDto> lista = service.consultarEjemplares(titulo);
			// Reporte
			for (EjemplarDto bean : lista) {
				System.out.println(bean);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

}
