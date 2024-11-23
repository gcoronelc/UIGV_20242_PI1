package pe.edu.uni.biblioteca.prueba;

import java.sql.Connection;
import pe.edu.uni.biblioteca.db.AccesoDB;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class Prueba01 {
	
	public static void main(String[] args) {
		
		try {
			Connection cn = AccesoDB.getConnection();
			System.out.println("Conexión Ok.");
			cn.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

}