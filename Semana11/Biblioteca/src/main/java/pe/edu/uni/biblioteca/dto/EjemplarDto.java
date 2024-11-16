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
public class EjemplarDto {

	private Long idejemplar;
	private String codigo;
	private String titulo;
	private String editorial;
	private Integer edicion;
	private Integer anio;

}
