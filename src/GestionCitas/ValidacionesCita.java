package GestionCitas;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import BaseDeDatos.ConexionSQLite;

public class ValidacionesCita {

    public static boolean esNombreValido(String nombre) {
        return nombre != null && nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    public static boolean esIdValido(String id) {
        return id != null && id.matches("^[0-9]+$") && Integer.parseInt(id) >= 100000;
    }

    public static boolean esFechaValida(int dia, int mes, int año) {
        try {
            LocalDate fechaCita = LocalDate.of(año, mes, dia);
            return fechaCita.isAfter(LocalDate.now());
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static boolean esFechaHoraValida(int dia, int mes, int año, int hora, int minuto) {
        try {
            LocalDateTime fechaHoraCita = LocalDateTime.of(año, mes, dia, hora, minuto);
            return fechaHoraCita.isAfter(LocalDateTime.now());
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static boolean estaCitaOcupada(String fecha, String hora, String servicio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CitasMedicas WHERE fecha=? AND hora=? AND servicio=?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setString(3, servicio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public static boolean pacienteYaTieneCitaParaServicio(int idPaciente, String servicio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CitasMedicas WHERE idPaciente=? AND servicio=?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ps.setString(2, servicio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public static boolean existeConflictoConOtraCita(int idCita, String fecha, String hora, String servicio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CitasMedicas WHERE fecha=? AND hora=? AND servicio=? AND idCita<>?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setString(3, servicio);
            ps.setInt(4, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
