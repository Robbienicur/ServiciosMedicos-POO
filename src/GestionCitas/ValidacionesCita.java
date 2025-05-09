package GestionCitas;

import java.time.*;

public class ValidacionesCita {

    public static boolean esNombreValido(String nombre) {
        return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    public static boolean esIdValido(String id) {
        // Posteriormente agregar la condición para que cheque el ID ej. ID>100000
        return id.matches("^[0-9]+$");
    }

    public static boolean esFechaValida(int dia, int mes, int año) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaCita = LocalDate.of(año, mes, dia);
        return fechaCita.isAfter(fechaActual);
    }
}
