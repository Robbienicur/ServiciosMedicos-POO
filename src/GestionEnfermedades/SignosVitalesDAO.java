package GestionEnfermedades;

import BaseDeDatos.ConexionSQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignosVitalesDAO {

    public static boolean guardarSignosVitales(int idPaciente, String presionArterial,
            int ritmoCardiaco, int ritmoRespiratorio, double temperatura, int oximetria) {
        String sql = "INSERT INTO SignosVitales (idPaciente, presionArterial, ritmoCardiaco, "
                + "ritmoRespiratorio, temperatura, oximetria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ps.setString(2, presionArterial);
            ps.setInt(3, ritmoCardiaco);
            ps.setInt(4, ritmoRespiratorio);
            ps.setDouble(5, temperatura);
            ps.setInt(6, oximetria);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String[]> obtenerUltimosSignos(int idPaciente, int limite) {
        List<String[]> registros = new ArrayList<>();
        String sql = "SELECT fecha, presionArterial, ritmoCardiaco, ritmoRespiratorio, "
                + "temperatura, oximetria FROM SignosVitales WHERE idPaciente = ? "
                + "ORDER BY fecha DESC LIMIT ?";

        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ps.setInt(2, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    registros.add(new String[]{
                            rs.getString("fecha"),
                            rs.getString("presionArterial"),
                            String.valueOf(rs.getInt("ritmoCardiaco")),
                            String.valueOf(rs.getInt("ritmoRespiratorio")),
                            String.valueOf(rs.getDouble("temperatura")),
                            String.valueOf(rs.getInt("oximetria"))
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public static String[] obtenerUltimoRegistro(int idPaciente) {
        String sql = "SELECT fecha, presionArterial, ritmoCardiaco, ritmoRespiratorio, "
                + "temperatura, oximetria FROM SignosVitales WHERE idPaciente = ? "
                + "ORDER BY fecha DESC LIMIT 1";

        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                            rs.getString("fecha"),
                            rs.getString("presionArterial"),
                            String.valueOf(rs.getInt("ritmoCardiaco")),
                            String.valueOf(rs.getInt("ritmoRespiratorio")),
                            String.valueOf(rs.getDouble("temperatura")),
                            String.valueOf(rs.getInt("oximetria"))
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
