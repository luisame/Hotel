package CONTROLLER;
/*
 * Esta clase representa al manejo de clientes del sistema.
 */
import Varios.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author luisa
 */
public class Clientes {

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> codigoColumna;

    // Resto de las columnas...

    @FXML
    private TextField codigoInputAgregar;

    // Resto de los campos de texto...

    @FXML
    private ComboBox<String> buscarPor;

    @FXML
    private TextField busquedaInput;

    @FXML
    private Button botonBuscar;

    private ObservableList<Cliente> listaClientes;

    public void initialize() {
        // Configurar las columnas de la tabla
        codigoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        // Configurar el resto de las columnas...

        listaClientes = FXCollections.observableArrayList();
        tablaClientes.setItems(listaClientes);
    }

    @FXML
    public void agregarCliente(ActionEvent event) {
        // Obtener los valores de los campos de texto
        String codigo = codigoInputAgregar.getText();
        // Obtener el resto de los valores de los campos de texto...

        // Crear un nuevo objeto Cliente
        Cliente nuevoCliente = new Cliente(codigo, nombre, apellidos, nif, direccion, ciudad, correoElectronico, telefono1, telefono2, categoria);

        // Agregar el cliente a la tabla
        tablaClientes.getItems().add(nuevoCliente);

        // Limpiar los campos de texto
        codigoInputAgregar.clear();
        // Limpiar el resto de los campos de texto...
    }

    @FXML
    public void buscar(ActionEvent event) {
        // Obtener la búsqueda y el tipo de búsqueda seleccionado
        String busqueda = busquedaInput.getText();
        String tipoBusqueda = buscarPor.getValue();

        ObservableList<Cliente> resultados;

        switch (tipoBusqueda) {
            case "Cliente":
                // Buscar clientes por nombre o apellidos
                resultados = FXCollections.observableArrayList(buscarClientesPorNombreOApellidos(busqueda));
                break;
            case "Categoría":
                // Buscar clientes por categoría
                resultados = FXCollections.observableArrayList(buscarClientesPorCategoria(busqueda));
                break;
            default:
                // Obtener todos los clientes
                resultados = FXCollections.observableArrayList(obtenerTodosLosClientes());
        }

        // Actualizar la tabla con los resultados de la búsqueda
        tablaClientes.setItems(resultados);
    }

    // Método para buscar clientes por nombre o apellidos
    private List<Cliente> buscarClientesPorNombreOApellidos(String busqueda) {
        // Obtener todos los clientes de la tabla
        List<Cliente> todosLosClientes = obtenerTodosLosClientes();

        // Filtrar los clientes que coinciden con la búsqueda
        return todosLosClientes.stream()
                .filter(cliente -> cliente.getNombre().contains(busqueda) || cliente.getApellidos().contains(busqueda))
                .collect(Collectors.toList());
    }

    // Método para buscar clientes por categoría
    private List<Cliente> buscarClientesPorCategoria(String busqueda) {
        // Obtener todos los clientes de la tabla
        List<Cliente> todosLosClientes = obtenerTodosLosClientes();

        // Filtrar los clientes que coinciden con la categoría de búsqueda
        return todosLosClientes.stream()
                .filter(cliente -> cliente.getCategoria().equals(busqueda))
                .collect(Collectors.toList());
    }

    // Método para obtener todos los clientes de la tabla
    private List<Cliente> obtenerTodosLosClientes() {
        // Convertir la tabla a una lista de clientes
        return new ArrayList<>(tablaClientes.getItems());
    }
}
