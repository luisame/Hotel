/*
 * clase  habitación getters y setters.
 */
package CONTROLLER;

/**
 *
 * @author luisa
 */
public class Habitacion {
    private int id;
    private String tipo;
    private String descripcion;
    private int capacidad;
    private String estado;

    /**
     * Constructor de la clase Habitacion.
     *
     * @param id          ID de la habitación
     * @param tipo        Tipo de habitación
     * @param descripcion Descripción de la habitación
     * @param capacidad   Capacidad de la habitación
     * @param estado      Estado de la habitación
     */
    public Habitacion(int id, String tipo, String descripcion, int capacidad, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Getter para el ID de la habitación
    public int getId() {
        return id;
    }

    // Getters y setters para los demás atributos de la habitación

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
