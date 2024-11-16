package pe.edu.uni.biblioteca.dto;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDto {

	private Long idestudiante;
	private String codigo;
	private String apellido;
	private String nombre;

}
