package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String passaword = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		conectar();
	}

	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, passaword);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERRO ao conctar o banco de dados");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
