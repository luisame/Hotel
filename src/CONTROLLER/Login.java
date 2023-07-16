package CONTROLLER;
/**
 * Clase que maneja  la ventana de Login para entrar.
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.DriverManager;

public class Login {

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;
    /**
     * Inicializa el controlador después de que se haya cargado el archivo FXML.
     * 
     * @param url la ubicación utilizada para resolver rutas relativas para el objeto raíz o nulo si no se puede determinar.
     * @param rb  el recurso utilizado para localizar el objeto raíz o nulo si no se puede determinar.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar los campos con los valores del archivo FXML
        userField.setText("");
        passwordField.setText("");
    }
    @FXML
    private void handleLoginButtonAction() throws IOException {
        String username = userField.getText();
        String password = passwordField.getText();

        try {
            // Conectar con la base de datos
            String url = "jdbc:mariadb://localhost:3306/hotelr";
            String user = "root";
            String dbPassword = "?7Ash€r";
            Connection conn = DriverManager.getConnection(url, user, dbPassword);

            // Verificar el usuario y contraseña en la base de datos
            String query = "SELECT * FROM acceso WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si el usuario existe, mostrar un mensaje de bienvenida
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText("Acceso permitido");
                alert.setContentText("Bienvenido " + username);
                alert.showAndWait();
                // Ejecutar el programa InicioMain.java
                try {
                    String javaHome = System.getProperty("java.home");
                    String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
                    String classpath = System.getProperty("java.class.path");
                    String className = "MenuInicio.InicioMain";

                    ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
                    builder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Si el usuario no existe, mostrar un mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login");
                alert.setHeaderText("Acceso denegado");
                alert.setContentText("Usuario o contraseña incorrectos");
                alert.showAndWait();
            }

            // Cerrar la conexión con la base de datos
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            // Mostrar un mensaje de error si ocurre un problema con la base de datos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error de conexion");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
    
}
