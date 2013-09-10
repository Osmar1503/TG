package factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public static Connection createConnection() {
		try {
			Class.forName("SQLITEDriver");			
			Connection con = DriverManager.getConnection("String de Conexao SQLITE");
			return con;
		} catch (Exception e) {
			System.out.println("Erro ao criar conexao.");
			e.printStackTrace();
		}
		return null;
	}	
	
}
