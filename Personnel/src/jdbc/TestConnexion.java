package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnexion {

	public static void main(String[] args) {
				
		try {
			Connection con = DriverManager.getConnection(ConnexionMySQL.getUrl(), ConnexionMySQL.getUser(), ConnexionMySQL.getPassword());
			System.out.println("Connexion reussi");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connexion à la base de données impossible");
		}

	}

}
