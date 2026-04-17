package BaseDeDatos;

import java.sql.*;

public class AlumnoDB {

    public static String obtenerNombreCompleto(int id) {
        String sql = "SELECT Nombre, ApellidoPaterno, ApellidoMaterno FROM InformacionAlumno WHERE ID = ?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Nombre") + " " +
                            rs.getString("ApellidoPaterno") + " " +
                            rs.getString("ApellidoMaterno");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando alumno: " + e.getMessage());
        }
        return null;
    }

    public static String[] obtenerDatosBasicos(int id) {
        String sql = "SELECT Nombre, ApellidoPaterno, ApellidoMaterno, Correo FROM InformacionAlumno WHERE ID = ?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                            rs.getString("Nombre"),
                            rs.getString("ApellidoPaterno"),
                            rs.getString("ApellidoMaterno"),
                            rs.getString("Correo")
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando datos de alumno: " + e.getMessage());
        }
        return null;
    }

    public static boolean existeAlumno(int id) {
        String sql = "SELECT 1 FROM InformacionAlumno WHERE ID = ?";
        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error verificando alumno: " + e.getMessage());
        }
        return false;
    }
}
