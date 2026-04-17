package BaseDeDatos;

import java.sql.*;

public class PacienteDB {

    public static String obtenerNombrePorID(String id) {
        String sql = "SELECT Nombre, ApellidoPaterno, ApellidoMaterno FROM InformacionAlumno WHERE ID = ?";
        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Nombre") + " " +
                            rs.getString("ApellidoPaterno") + " " +
                            rs.getString("ApellidoMaterno");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("ID no numérico: " + id);
        } catch (SQLException e) {
            System.err.println("Error buscando paciente: " + e.getMessage());
        }
        return null;
    }
}
