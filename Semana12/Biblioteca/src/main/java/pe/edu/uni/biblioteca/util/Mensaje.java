package pe.edu.uni.biblioteca.util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware
 * @cursos gcoronelc.github.io
 */
public class Mensaje {

	private Mensaje() {
	}

	public static void info(Component padre, String mensaje) {
		JOptionPane.showMessageDialog(padre, mensaje, "BIBLIOTECA - INFORMACIÓN",
				  JOptionPane.INFORMATION_MESSAGE);
	}

	public static void error(Component padre, String mensaje) {
		JOptionPane.showMessageDialog(padre, mensaje, "BIBLIOTECA - ERROR",
				  JOptionPane.ERROR_MESSAGE);
	}

}
