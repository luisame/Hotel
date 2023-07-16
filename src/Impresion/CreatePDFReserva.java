package Impresion;

import java.io.FileOutputStream;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.math.BigDecimal;
/*Bajar la librería itext de github*/
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
/**
 *
 * @author luisa
 */
public class CreatePDFReserva {
    public static void main(String[] args) {
        try {
            // Definir objeto reserva:  Reserva reserva = obtenerReserva();
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("reserva.pdf"));
            document.open();
            document.add(new Paragraph("Detalles de la Reserva:"));
            document.add(new Paragraph("-------------------------"));
            document.add(new Paragraph("ID Reserva: " + reserva.getIdReserva()));
            document.add(new Paragraph("ID Cliente: " + reserva.getIdCliente()));
            document.add(new Paragraph("ID Habitacion: " + reserva.getIdHabitacion()));
            document.add(new Paragraph("Fecha de Entrada: " + reserva.getFechaEntrada()));
            document.add(new Paragraph("Fecha de Salida: " + reserva.getFechaSalida()));
            document.add(new Paragraph("Estado de Reserva: " + reserva.getEstadoReserva()));
            document.add(new Paragraph("Precio por Persona: " + reserva.getPrecioPersona()));
            document.add(new Paragraph("Temporada: " + reserva.getTemporada()));

            LocalDate fechaEntrada = reserva.getFechaEntrada();
            LocalDate fechaSalida = reserva.getFechaSalida();

            long numeroDias = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
            BigDecimal precioPersona = reserva.getPrecioPersona();
            
            // Asumiendo que tienes un método getNumeroPersonas que devuelve un int.
            int numeroPersonas = reserva.getNumeroPersonas();

            BigDecimal precioTotal = precioPersona.multiply(new BigDecimal(numeroDias)).multiply(new BigDecimal(numeroPersonas));
/*hasta que no baje itex y lo agregue a las librerías no se solucionará*/
            document.add(new Paragraph("Precio Total: " + precioTotal));
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
