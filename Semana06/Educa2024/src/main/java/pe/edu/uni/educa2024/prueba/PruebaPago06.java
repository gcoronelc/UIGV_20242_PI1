package pe.edu.uni.educa2024.prueba;

import pe.edu.uni.educa2024.dto.PagoDto;
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
public class PruebaPago06 {
	
	// Proceso Ok.
	public static void main(String[] args) {
		try {
			// Datos
			PagoDto bean = new PagoDto();
			bean.setIdEmpleado(1);
			bean.setIdCurso(1);
			bean.setIdAlumno(4);
			bean.setCuota(3);
			bean.setImporte(300.00);
			// Proceso
			EducaService service = new EducaService();
			bean = service.procPago(bean);
			System.out.println(bean);
			// Reporte
			System.out.println("Pago ok.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}