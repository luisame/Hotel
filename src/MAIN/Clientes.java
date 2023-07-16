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
public class Clientes extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("ClientesFXML.fxml"));
        
        // Establece el estilo "Modena" para los componentes de JavaFX
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);

        // Crea la escena
        Scene scene = new Scene(root);

        // Establece la escena en el escenario principal
        primaryStage.setScene(scene);
        primaryStage.setTitle("Aplicación de Clientes");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lanza la aplicación
        launch(args);
    }
}

