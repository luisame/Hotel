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
public class Reservas extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML de la interfaz gráfica de Reservas
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Reservas.fxml"));

        // Crea una escena con el contenido cargado desde el archivo FXML
        Scene scene = new Scene(root, 800, 600);

        // Establece el título de la ventana principal
        primaryStage.setTitle("Sistema de Reservas");

        // Establece la escena en el escenario principal
        primaryStage.setScene(scene);

        // Muestra la ventana principal
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lanza la aplicación
        launch(args);
    }
}
