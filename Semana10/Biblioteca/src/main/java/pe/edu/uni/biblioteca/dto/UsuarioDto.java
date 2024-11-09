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
@Data @AllArgsConstructor @NoArgsConstructor
public class UsuarioDto {
	
	private String usuario;
	private int idEmpleado;
	private int idRol;
	private String nombRol;

}
