package pe.edu.uni.educa2024.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Eric Gustavo Coronel Castillo
 * @blog www.desarrollasoftware.com
 * @email gcoronelc@gmail.com
 * @youtube www.youtube.com/DesarrollaSoftware
 * @facebook www.facebook.com/groups/desarrollasoftware/
 * @cursos gcoronelc.github.io
 */
public class AccesoDB {

	private AccesoDB() {
	}

	public static Connection getConnection() throws SQLException {
		Connection cn = null;
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String urlDB = "jdbc:sqlserver://localhost:1433;databaseName=EDUCA2;encrypt=true;TrustServerCertificate=True;";
		String user = "sa";
		String pass = "sql";
		try {
			// Paso 1: Cargar el driver a memoria 
			//Class.forName(driver).newInstance();
			Class.forName(driver).getDeclaredConstructor().newInstance();
			// Paso 2: Obtener el objeto Connection 
			cn = DriverManager.getConnection(urlDB, user, pass);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw new SQLException("No se encontró el driver de la base de datos.");
		} catch (Exception e) {
			throw new SQLException("No se puede establecer la conexión con la BD.");
		}
		return cn;
	}

}
