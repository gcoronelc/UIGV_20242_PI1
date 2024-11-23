package pe.edu.uni.biblioteca.prueba;

import pe.edu.uni.biblioteca.dto.PrestamoDto;
import pe.edu.uni.biblioteca.service.BibliotecaService;

/**
 * Prueba para registrar prestamo correcto.
 */
public class PruebaPrestamo04 {
	
	public static void main(String[] args) {
		
		try {
			// Datos
			PrestamoDto dto = new PrestamoDto();
			dto.setIdEstudiante(3L);
			dto.setIdEjemplar(1L);
			// Proceso
			BibliotecaService service = new BibliotecaService();
			service.registrarPrestamo(dto);
			System.out.println("Proceso ok.");
			System.out.println(dto);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
	}
	
}
