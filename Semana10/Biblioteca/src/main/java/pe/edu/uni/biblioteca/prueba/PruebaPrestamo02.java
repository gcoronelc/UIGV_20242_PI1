package pe.edu.uni.biblioteca.prueba;

import pe.edu.uni.biblioteca.dto.PrestamoDto;
import pe.edu.uni.biblioteca.service.BiblioService;

/**
 * Prueba si existe el estudiante
 */
public class PruebaPrestamo02 {
	
	public static void main(String[] args) {
		
		try {
			// Datos
			PrestamoDto dto = new PrestamoDto();
			dto.setIdEjemplar(1L);
			dto.setIdEstudiante(777L);
			// Proceso
			BiblioService service = new BiblioService();
			service.registrarPrestamo(dto);
			System.out.println("Proceso ok.");
			System.out.println(dto);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
	}
	
}
