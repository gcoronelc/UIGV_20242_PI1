package pe.edu.uni.biblioteca.prueba;

import pe.edu.uni.biblioteca.dto.PrestamoDto;
import pe.edu.uni.biblioteca.service.BibliotecaService;

/**
 * Prueba si existe el ejemplar
 */
public class PruebaPrestamo01 {
	
	public static void main(String[] args) {
		
		try {
			// Datos
			PrestamoDto dto = new PrestamoDto();
			dto.setIdEjemplar(777L);
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
