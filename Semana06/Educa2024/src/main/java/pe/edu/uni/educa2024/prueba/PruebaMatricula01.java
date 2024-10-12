package pe.edu.uni.educa2024.prueba;

import pe.edu.uni.educa2024.dto.MatriculaDto;
import pe.edu.uni.educa2024.service.EducaService;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class PruebaMatricula01 {
	
	// Curso 500 no existe
	public static void main(String[] args) {
		try {
			// Datos
			MatriculaDto bean = new MatriculaDto();
			bean.setIdCurso(500);
			// Proceso
			EducaService service = new EducaService();
			bean = service.procMatricula(bean);
			// Reporte
			System.out.println("Matricula ok.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
