package Chaves.ProjetoLicitacoes.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Nao foi possivel encontrar db.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao carregar db.properties", ex);
        }
    }

    public static Connection getConnection() {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco.", e);
        }
    }
}