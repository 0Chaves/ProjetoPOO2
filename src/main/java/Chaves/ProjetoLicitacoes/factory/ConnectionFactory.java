package Chaves.ProjetoLicitacoes.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//Tenta estabelecer conexão com o banco de dados. Retorna a conexão caso bem sucedida.
	public static Connection getConnection() {
		String login = "postgres";
		String senha = "ifrs";
		String urlcon = "jdbc:postgresql://localhost:5432/projeto";
		try {
			return DriverManager.getConnection(urlcon, login, senha);
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
