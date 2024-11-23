package pe.edu.uni.biblioteca.prueba;

import pe.edu.uni.biblioteca.service.BibliotecaService;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class PruebaDevolucion01 {
	
	public static void main(String[] args) {
		try {
			// Dato
			Long idPrestamo = 10L;
			Double importe = 0.0;
			// Proceso
			BibliotecaService service = new BibliotecaService();
			service.registrarDevolucion(idPrestamo, importe);
			// Reporte
			System.out.println("Proceso ok.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
