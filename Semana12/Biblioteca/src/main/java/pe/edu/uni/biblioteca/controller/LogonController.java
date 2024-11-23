package pe.edu.uni.biblioteca.controller;

import pe.edu.uni.biblioteca.service.LogonService;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class LogonController {

	public void validar(String usuario, String clave) {
		LogonService service = new LogonService();
		service.validarUsuario(usuario, clave);
	}

}
