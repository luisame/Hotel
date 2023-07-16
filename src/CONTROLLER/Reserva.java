/*
 * Esta clase son los getter y setter de la clase Reserva.
 */
package CONTROLLER;

import java.time.LocalDate;
/**
 *
 * @author luisa
 */
public class Reserva {
    
    private int idReserva;
    private int idCliente;
    private int idHabitacion;
    private int idUsuario;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estadoReserva;
    private double precioPersona;
    private String temporada;

    /**
     * Constructor de la clase Reserva.
     *
     * @param idReserva     ID de la reserva
     * @param idCliente     ID del cliente asociado a la reserva
     * @param idHabitacion  ID de la habitación reservada
     * @param idUsuario     ID del usuario que realizó la reserva
     * @param fechaEntrada  Fecha de entrada
     * @param fechaSalida   Fecha de salida
     * @param estadoReserva Estado de la reserva
     * @param precioPersona Precio por persona
     * @param temporada     Temporada de la reserva
     */
    public Reserva(int idReserva, int idCliente, int idHabitacion, int idUsuario, LocalDate fechaEntrada, LocalDate fechaSalida, String estadoReserva, double precioPersona, String temporada) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idHabitacion = idHabitacion;
        this.idUsuario = idUsuario;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estadoReserva = estadoReserva;
        this.precioPersona = precioPersona;
        this.temporada = temporada;
    }

    // Getters y setters para los atributos de la reserva
    
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public double getPrecioPersona() {
        return precioPersona;
    }

    public void setPrecioPersona(double precioPersona) {
        this.precioPersona = precioPersona;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }
}
