package GestionCitas;

import BaseDeDatos.ConexionSQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class RecordatorioCitasService {

    private static Timer timer;

    private RecordatorioCitasService() {
    }

    public static void iniciar() {
        timer = new Timer("RecordatorioCitas", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                verificarCitas();
            }
        }, 5000, 1800000);
    }

    private static void verificarCitas() {
        String fechaManana = LocalDate.now().plusDays(1).toString();

        String sqlCitas = "SELECT idCita, idPaciente, fecha, hora, servicio FROM CitasMedicas WHERE fecha = ?";
        String sqlExiste = "SELECT COUNT(*) FROM Notificaciones WHERE idPaciente = ? AND fecha = ? AND hora = ? AND servicio = ?";
        String sqlInsertar = "INSERT INTO Notificaciones (idPaciente, mensaje, estado, fecha, hora, servicio) VALUES (?, ?, 'pendiente', ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement stmtCitas = conn.prepareStatement(sqlCitas)) {

            stmtCitas.setString(1, fechaManana);

            try (ResultSet rs = stmtCitas.executeQuery()) {
                while (rs.next()) {
                    String idPaciente = rs.getString("idPaciente");
                    String fecha = rs.getString("fecha");
                    String hora = rs.getString("hora");
                    String servicio = rs.getString("servicio");

                    try (PreparedStatement stmtExiste = conn.prepareStatement(sqlExiste)) {
                        stmtExiste.setString(1, idPaciente);
                        stmtExiste.setString(2, fecha);
                        stmtExiste.setString(3, hora);
                        stmtExiste.setString(4, servicio);

                        try (ResultSet rsExiste = stmtExiste.executeQuery()) {
                            if (rsExiste.next() && rsExiste.getInt(1) > 0) {
                                continue;
                            }
                        }
                    }

                    String mensaje = "Recordatorio: Tiene cita de " + servicio + " mañana " + fecha + " a las " + hora;

                    try (PreparedStatement stmtInsertar = conn.prepareStatement(sqlInsertar)) {
                        stmtInsertar.setString(1, idPaciente);
                        stmtInsertar.setString(2, mensaje);
                        stmtInsertar.setString(3, fecha);
                        stmtInsertar.setString(4, hora);
                        stmtInsertar.setString(5, servicio);
                        stmtInsertar.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en verificación de recordatorios de citas: " + e.getMessage());
        }
    }

    public static void detener() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
