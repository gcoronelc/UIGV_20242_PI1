package pe.edu.uni.biblioteca.controller;

import java.util.List;
import pe.edu.uni.biblioteca.dto.EjemplarDto;
import pe.edu.uni.biblioteca.dto.EstudianteDto;
import pe.edu.uni.biblioteca.service.ConsultaService;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class ConsultaController {
	
	private ConsultaService consultaService;

	public ConsultaController() {
		consultaService = new ConsultaService();
	}
	
	public EstudianteDto consultarEstudiante(String codigo){
		return consultaService.consultarEstudiante(codigo);
	}
	
	public List<EjemplarDto> consultarEjemplares(String titulo){
		return consultaService.consultarEjemplares(titulo);
	}

}
