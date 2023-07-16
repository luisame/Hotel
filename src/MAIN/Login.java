package MAIN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author luisa
 */
public class Login extends Application {

    public static void main(String[] args) {
        // Lanza la aplicación
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML de la interfaz gráfica de Login
        Parent root = FXMLLoader.load(getClass().getResource("/Login/LoginFXML.fxml"));

        // Crea una escena con el contenido cargado desde el archivo FXML
        Scene scene = new Scene(root);

        // Establece la escena en el escenario principal
        primaryStage.setScene(scene);

        // Establece el título de la ventana principal
        primaryStage.setTitle("Login");

        // Muestra la ventana principal
        primaryStage.show();
    }
}
