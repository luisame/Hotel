package CONTROLLER;

/**Esta clase  que maneja  las reservas en el sistem*/
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author luisa
 */
public class Reservas {
    @FXML
    private Label precioNocheLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private DatePicker fechaEntradaPicker;

    @FXML
    private DatePicker fechaSalidaPicker;

    private Connection connection;

    @FXML
    private void initialize() {
        // Configurar listeners para actualizar los precios y el total al seleccionar las fechas
        fechaEntradaPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            calcularPrecioTotal();
        });

        fechaSalidaPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            calcularPrecioTotal();
        });

        // Establecer conexión a la base de datos
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/Hotelr", "root", "?7Ash€r");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reservar() {
        // Comprobar disponibilidad de habitaciones
        boolean habitacionesDisponibles = comprobarDisponibilidadHabitaciones();
        if (!habitacionesDisponibles) {
            // Mostrar mensaje de habitaciones no disponibles
            mostrarMensaje("No hay habitaciones disponibles para las fechas seleccionadas.");
            return;
        }

        // Validar fechas seleccionadas
        LocalDate fechaEntrada = fechaEntradaPicker.getValue();
        LocalDate fechaSalida = fechaSalidaPicker.getValue();
        if (fechaEntrada == null || fechaSalida == null || fechaEntrada.isAfter(fechaSalida)) {
            // Mostrar mensaje de fechas inválidas
            mostrarMensaje("Las fechas seleccionadas son inválidas.");
            return;
        }

        // Obtener capacidad deseada del cliente
        int capacidadDeseada = obtenerCapacidadDeseada();

        // Consultar habitaciones disponibles con la capacidad requerida y las fechas seleccionadas
        List<Habitacion> habitacionesDisponibles = consultarHabitacionesDisponibles(capacidadDeseada, fechaEntrada, fechaSalida);
        if (habitacionesDisponibles.isEmpty()) {
            // Mostrar mensaje de habitaciones no disponibles
            mostrarMensaje("No hay habitaciones disponibles para la capacidad y/o fechas seleccionadas.");
            return;
        }

        // Mostrar habitaciones disponibles en el calendario
        mostrarHabitacionesDisponiblesEnCalendario(habitacionesDisponibles, fechaEntrada, fechaSalida);

        // Calcular precio por noche y el total
        double precioNoche = obtenerPrecioPorNoche();
        double total = calcularTotal(fechaEntrada, fechaSalida, precioNoche);

        // Guardar reserva en la base de datos
        Reserva reserva = guardarReserva(fechaEntrada, fechaSalida, total);

        // Actualizar estado de la habitación
        if (reserva != null) {
            actualizarEstadoHabitacion(reserva.getHabitacion());
        }

        // Mostrar confirmación de reserva
        if (reserva != null) {
            mostrarConfirmacionReserva(reserva);
        }
    }

    private boolean comprobarDisponibilidadHabitaciones() {
        try {
            // Construir la consulta SQL para verificar la disponibilidad de habitaciones
            String query = "SELECT COUNT(*) FROM habitaciones WHERE estado = 'Disponible'";
            PreparedStatement statement = connection.prepareStatement(query);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener el resultado
            if (resultSet.next()) {
                int numHabitacionesDisponibles = resultSet.getInt(1);
                return numHabitacionesDisponibles > 0;
            }

            // Cerrar el ResultSet y el PreparedStatement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private int obtenerCapacidadDeseada() {
        int capacidadDeseada = 0;

        try {
            // Construir la consulta SQL para obtener la capacidad deseada
            String query = "SELECT CAPACIDAD FROM habitaciones WHERE estado = 'Disponible' LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener la capacidad deseada del primer resultado
            if (resultSet.next()) {
                capacidadDeseada = resultSet.getInt("CAPACIDAD");
            }

            // Cerrar el ResultSet y el PreparedStatement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return capacidadDeseada;
    }

    private List<Habitacion> consultarHabitacionesDisponibles(int capacidad, LocalDate fechaEntrada, LocalDate fechaSalida) {
        List<Habitacion> habitacionesDisponiblesList = new ArrayList<>();

        try {
            // Construir la consulta SQL para obtener las habitaciones disponibles
            String query = "SELECT * FROM habitaciones WHERE capacidad >= ? AND estado = 'Disponible'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, capacidad);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Recorrer los resultados y construir objetos Habitacion
            while (resultSet.next()) {
                int idHabitacion = resultSet.getInt("id_habitacion");
                String tipoHabitacion = resultSet.getString("tipo_habitacion");
                // Obtener otros datos de la habitación

                // Crear objeto Habitacion y añadirlo a la lista
                Habitacion habitacion = new Habitacion(idHabitacion, tipoHabitacion);
                habitacionesDisponiblesList.add(habitacion);
            }

            // Cerrar el ResultSet y el PreparedStatement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return habitacionesDisponiblesList;
    }

    private void mostrarHabitacionesDisponiblesEnCalendario(List<Habitacion> habitacionesDisponibles, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // Obtener todas las fechas entre la fecha de entrada y la fecha de salida
        List<LocalDate> fechas = new ArrayList<>();
        LocalDate fecha = fechaEntrada;
        while (!fecha.isAfter(fechaSalida)) {
            fechas.add(fecha);
            fecha = fecha.plusDays(1);
        }

        // Actualizar visualmente el calendario para resaltar las fechas y habitaciones disponibles
        for (LocalDate fechaActual : fechas) {
            // Obtener la habitación disponible para la fecha actual
            Habitacion habitacionDisponible = obtenerHabitacionDisponibleParaFecha(habitacionesDisponibles, fechaActual);

            // Actualizar la etiqueta del calendario con la información de la habitación
            actualizarEtiquetaCalendario(fechaActual, habitacionDisponible);
        }
    }

    private Habitacion obtenerHabitacionDisponibleParaFecha(List<Habitacion> habitacionesDisponibles, LocalDate fecha) {
        Habitacion habitacionDisponible = null;

        // Iterar sobre las habitaciones disponibles
        for (Habitacion habitacion : habitacionesDisponibles) {
            // Obtener el estado de la habitación para la fecha actual
            String estadoHabitacion = obtenerEstadoHabitacionParaFecha(habitacion, fecha);

            // Verificar si la habitación está disponible en la fecha actual
            if (estadoHabitacion.equals("Disponible")) {
                // Asignar la habitación como habitación disponible
                habitacionDisponible = habitacion;
                break; // Terminar el bucle una vez que se encuentra una habitación disponible
            }
        }

        return habitacionDisponible;
    }

    private String obtenerEstadoHabitacionParaFecha(Habitacion habitacion, LocalDate fecha) {
        String estadoHabitacion = ""; // Estado de la habitación para la fecha actual

        try {
            // Construir la consulta SQL para obtener el estado de la habitación en la fecha actual
            String query = "SELECT estado FROM reservas WHERE id_habitacion = ? AND fecha_entrada <= ? AND fecha_salida >= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, habitacion.getId());
            statement.setDate(2, Date.valueOf(fecha));
            statement.setDate(3, Date.valueOf(fecha));

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Verificar si hay resultados y obtener el estado de la habitación
            if (resultSet.next()) {
                estadoHabitacion = resultSet.getString("estado");
            }

            // Cerrar el ResultSet y el PreparedStatement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadoHabitacion;
    }

    private void actualizarEtiquetaCalendario(LocalDate fecha, Habitacion habitacionDisponible) {
        // Implementar lógica para actualizar la etiqueta del calendario con la información de la habitación disponible
        // Quiero  utilizar los métodos correspondientes de tu interfaz gráfica para actualizar visualmente el calendario
        // Quiero asignar un color a la etiqueta de la fecha según la disponibilidad de habitaciones (amarillo R rojo o y verde L los valores de estado  de las abitaciones)

        // Obtener la etiqueta correspondiente a la fecha en el calendario
        Label etiquetaFecha = obtenerEtiquetaFechaDelCalendario(fecha);

        // Actualizar la etiqueta con la información de la habitación disponible
        if (habitacionDisponible != null) {
            // Habitación disponible
            etiquetaFecha.setText("Disponible");
            etiquetaFecha.setStyle("-fx-background-color: #00FF00"); // Color verde
        } else {
            // Habitación no disponible
            etiquetaFecha.setText("No disponible");
            etiquetaFecha.setStyle("-fx-background-color: #FF0000"); // Color rojo
        }
    }

    private Label obtenerEtiquetaFechaDelCalendario(LocalDate fecha) {
        // Falta la lógica para obtener la etiqueta correspondiente a la fecha en el calendario
        // Aquí debes utilizar los métodos y componentes de tu interfaz gráfica para obtener la etiqueta correcta
        // y retornarla. Asegúrate de que el tipo de retorno sea el correcto.
        // ...
        return null;
    }

    private double obtenerPrecioPorNoche() {
        // Falta la lógica para obtener el precio por noche
        // Además calcular el precio por noche de la habitación según la temporada y el tipo de habitación
        // Realizar una consulta en la tabla precioshabitaciones para obtener el precio correspondiente
        // Devolver el precio por noche
        
        return 0.0;
    }

    private double calcularTotal(LocalDate fechaEntrada, LocalDate fechaSalida, double precioNoche) {
        // Implementar lógica para calcular el total
        // Calcular el número de noches de la estancia
        long numNoches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);

        // Calcular el total multiplicando el número de noches por el precio por noche
        double total = numNoches * precioNoche;

        return total;
    }

    private Reserva guardarReserva(LocalDate fechaEntrada, LocalDate fechaSalida, double total) {
        Reserva reserva = null;

        try {
            // Construir la consulta SQL para insertar la reserva en la base de datos
            String query = "INSERT INTO reservas (fecha_entrada, fecha_salida, total) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(fechaEntrada));
            statement.setDate(2, Date.valueOf(fechaSalida));
            statement.setDouble(3, total);

            // Ejecutar la consulta
            int rowsAffected = statement.executeUpdate();

            // Verificar si se insertó correctamente y obtener el ID generado
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int reservaId = generatedKeys.getInt(1);
                    reserva = new Reserva(reservaId, fechaEntrada, fechaSalida, total);
                }
            }

            // Cerrar el PreparedStatement
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserva;
    }

    private void actualizarEstadoHabitacion(Habitacion habitacion) {
        try {
            // Actualizar el estado de la habitación
            String query = "UPDATE habitaciones SET estado = 'Reservada' WHERE id_habitacion = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, habitacion.getId());

            // Ejecutar la consulta
            int rowsAffected = statement.executeUpdate();

            // Verificar si se actualizó correctamente
            if (rowsAffected > 0) {
                System.out.println("El estado de la habitación se actualizó correctamente.");
            } else {
                System.out.println("No se pudo actualizar el estado de la habitación.");
            }

            // Cerrar 
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void mostrarConfirmacionReserva(Reserva reserva) {
        // Crear un mensaje con los detalles de la reserva
        String mensaje = "Reserva realizada con éxito\n\n";
        mensaje += "Número de reserva: " + reserva.getNumeroReserva() + "\n";
        mensaje += "Fecha de entrada: " + reserva.getFechaEntrada() + "\n";
        mensaje += "Fecha de salida: " + reserva.getFechaSalida() + "\n";
        mensaje += "Precio total: " + reserva.getTotal() + "\n";

        // Mostrar el mensaje de confirmación en una ventana emergente
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmación de Reserva");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void calcularPrecioTotal() {
        // Obtener las fechas seleccionadas
        LocalDate fechaEntrada = fechaEntradaPicker.getValue();
        LocalDate fechaSalida = fechaSalidaPicker.getValue();

        // Calcular el número de días de la estancia
        long numDias = fechaEntrada != null && fechaSalida != null ? fechaEntrada.until(fechaSalida).getDays() + 1 : 0;

        // Obtener el precio por noche de la habitación
        double precioNoche = obtenerPrecioPorNoche();

        // Calcular el total
        double total = numDias * precioNoche;

        // Actualizar las etiquetas de precio y total
        precioNocheLabel.setText(String.format("%.2f", precioNoche));
        totalLabel.setText(String.format("%.2f", total));
    }

    private String obtenerTemporada() {
        String temporada = "";

        try {
            LocalDate fechaEntrada = fechaEntradaPicker.getValue();
            LocalDate fechaSalida = fechaSalidaPicker.getValue();

            // Construir la consulta SQL para obtener la temporada correspondiente
            String query = "SELECT temporada FROM temporadas WHERE fecha_inicio <= ? AND fecha_fin >= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(fechaEntrada));
            statement.setDate(2, Date.valueOf(fechaSalida));

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Verificar si se obtuvo algún resultado y obtener la temporada
            if (resultSet.next()) {
                temporada = resultSet.getString("temporada");
            }

            // Cerrar el ResultSet y el PreparedStatement
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temporada;
    }
}
