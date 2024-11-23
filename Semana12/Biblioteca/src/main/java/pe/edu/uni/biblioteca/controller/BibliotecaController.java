package pe.edu.uni.biblioteca.controller;

import pe.edu.uni.biblioteca.dto.PrestamoDto;
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
public class BibliotecaController {
	
	private BibliotecaService bibliotecaService;

	public BibliotecaController() {
		bibliotecaService = new BibliotecaService();
	}
		
	
	public void registrarPrestamo(PrestamoDto dto) {
		bibliotecaService.registrarPrestamo(dto);
	}

}
