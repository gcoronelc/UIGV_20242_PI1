package pe.edu.uni.educa2024.prueba;

import pe.edu.uni.educa2024.dto.MatriculaDto;
import pe.edu.uni.educa2024.service.EducaService;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.comI
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class Prueba06 {
	
	public static void main(String[] args) {
		try {
			// Datos
			MatriculaDto bean = new MatriculaDto();
			bean.setIdCurso(1);
			bean.setIdAlumno(7);
			bean.setIdEmpleado(1);
			bean.setTipo("COCACOLA");
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
