package pe.edu.uni.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoDto {
	
	private Long idPrestamo;
	private Long idEstudiante;
	private Long idEjemplar;
	private Long idEstado;
	private String fechaInicio;
	private String fechaFin;
	private String fechaDevolucion;
	
	
}
