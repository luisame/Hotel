
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
public class HabitacionesMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML de la interfaz gráfica de Habitaciones
        Parent root = FXMLLoader.load(getClass().getResource("/Habitaciones/HabitacionesFXML.fxml"));
        
        // Establece el título de la ventana principal
        primaryStage.setTitle("Sistema de Habitaciones");
        
        // Crea una escena con el contenido cargado desde el archivo FXML
        primaryStage.setScene(new Scene(root));
        
        // Muestra la ventana principal
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lanza la aplicación
        launch(args);
    }
}
