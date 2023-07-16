package CONTROLLER;
/*
 * Clase que maneja El menú de Inicio
 */

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 *
 * @author luisa
 */
public class Inicio {

    @FXML
    private ImageView habitacionesImageView;

    @FXML
    private ImageView administracionImageView;

    @FXML
    private ImageView reservasImageView;

    @FXML
    private ImageView clientesImageView;
    
    @FXML
    private ImageView exitImageView;


    private Map<String, Image> imageMap;

    public void initialize(URL url, ResourceBundle rb) {
        // Carga las imágenes en el mapa
        imageMap = new HashMap<>();
        imageMap.put("administracion", new Image(getClass().getResourceAsStream("../Imagenes/administracion.png")));
        imageMap.put("reservas", new Image(getClass().getResourceAsStream("../Imagenes/reservas.png")));
        imageMap.put("clientes", new Image(getClass().getResourceAsStream("../Imagenes/clientes.png")));
        imageMap.put("habitaciones", new Image(getClass().getResourceAsStream("../Imagenes/habitaciones.png")));
        imageMap.put("exit", new Image(getClass().getResourceAsStream("../Imagenes/exit.png")));
        // Mostrar las imágenes en los ImageViews correspondientes
        administracionImageView.setImage(imageMap.get("administracion"));
        reservasImageView.setImage(imageMap.get("reservas"));
        clientesImageView.setImage(imageMap.get("clientes"));
        habitacionesImageView.setImage(imageMap.get("habitaciones"));
        exitImageView.setImage(imageMap.get("exit"));

        // Configurar eventos de clic para las imágenes
        administracionImageView.setOnMouseClicked(this::handleAdministracionClick);
        reservasImageView.setOnMouseClicked(this::handleReservasClick);
        clientesImageView.setOnMouseClicked(this::handleClientesClick);
       // habitacionesImageView.setOnMouseClicked(this::handleHabitacionesClick);
    }

    @FXML
    private Button btnClientes;

    @FXML
    private void handleBtnClientes(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Clientes.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Clientes");
            stage.show();
        } catch (IOException e) {
        }
    }
@FXML
    private Button BtnHabitaciones;

    public void handleBtnHabitaciones(ActionEvent event) {
        
       
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Habitaciones/HabitacionesFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Habitaciones");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @FXML
    private void exitProgram(MouseEvent event) {
        // Obtener la ventana actual
        Stage currentStage = (Stage) exitImageView.getScene().getWindow();

        try {
            // Crear una nueva instancia de Login
            MAIN.Login loginMain = new MAIN.Login();

            // Cerrar la ventana actual
            currentStage.close();

            // Abrir la ventana de inicio de sesión
            loginMain.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAdministracionClick(MouseEvent event) {
        // Lógica para manejar el clic en la imagen de Administración
        System.out.println("Haz clic en Administración");
    }

 @FXML
private void handleReservasClick(MouseEvent event) {
    // Lógica para manejar el clic en la imagen de Reservas
    System.out.println("Haz clic en Reservas");
}



    private void handleClientesClick(MouseEvent event) {
        // Lógica para manejar el clic en la imagen de Clientes
        System.out.println("Haz clic en Clientes");
    }
 
}