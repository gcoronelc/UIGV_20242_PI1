package pe.edu.uni.educa2024.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class PagoDto {
	
	private int idCurso;
	private int idAlumno;
	private int idEmpleado;
	private int cuota;
	private double importe;
	private String fecha;

}
