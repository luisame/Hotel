package MAIN;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author luisa
 */
public class conexionMariaDB {
    private static final String URL = "jdbc:mariadb://localhost:3306/hotel";
    private static final String USER = "root";
    private static final String PASSWORD = "?7Ash€r";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones
            throw new RuntimeException("Error al establecer la conexión con la base de datos.", e);
        }
    }
}

 





